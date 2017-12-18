
//#pragma execution_character_set("utf-8")

#define _WINSOCK_DEPRECATED_NO_WARNINGS 

#include "Client.h"

char Client::sbuf[DATA_BUFFER] = {0};
char Client::rbuf[DATA_BUFFER] = {0};

const char* const Client::businessId = "01f5d62c2c6099dd9e30";
const char* const Client::processName = "Aisino.Framework.Startup.exe";

bool Client::transferred = false;

const char* Client::serverAddress = "172.16.1.132";
//const char* Client::serverAddress = "172.16.1.23";
CRITICAL_SECTION Client::g_cs;

Client::Client()
{
	WORD wVersionRequested;
	WSADATA wsaData;
	wVersionRequested = MAKEWORD(2, 2);
	if (WSAStartup(wVersionRequested, &wsaData) != 0)
	{
		printf("WSAStartup failed.\n");
		system("pause");
		exit(-1);
	}
	if (wsaData.wVersion != wVersionRequested)
	{
		WSACleanup();
		printf("wsaData.wVersion!=wVersionRequested.\n");
		system("pause");
		exit(-1);
	}
};

Client::~Client()
{
	closesocket(clisock);
	WSACleanup();
};

SOCKET Client::getSocket()
{
	return clisock;
}

bool Client::SocketExe(SOCKET clisock, unsigned int threadNum)
{
	InitializeCriticalSection(&g_cs);
	while (1)
	{
		HANDLE hThread1 = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)RecvProc, (LPVOID)clisock, 0, 0);

		if (hThread1 != NULL)
		{
			CloseHandle(hThread1);
		}
		Sleep(3000);
		/*	HANDLE hThread2 = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)SendProc, (LPVOID)clisock, 0, 0);

		if (hThread2 != NULL)
		{
		CloseHandle(hThread2);
		}*/
		//Sleep(3000);
	}
}

char* Client::ByteOrderConvert(char* nstr)
{
	if (nstr == NULL)
	{
		return NULL;
	}
	char* s=nstr;
	while (*s&&*(s + 1))
	{
		char t = *s;
		*s = *(s + 1);
		*(++s) = t;
		++s;
	}
	return nstr;
}

bool Client::SetSbufWithSDataConnect(const char* businessId)
{
	if(businessId == NULL)
	{
		return false;
	}
	EncodingConvert encodingConvert;
	tSDataConnect sdataConnect;
	ParseJson parseJson;
	sdataConnect.operation = 0;
	//string utfstr;
	//encodingConvert.UnicodeToUTF8(businessId.c_str(), utfstr);
	sdataConnect.businessId = _strdup(businessId);
	std::string ss;
	if (!parseJson.TransSDataToConnectMsg(sdataConnect, ss))
	{
		printf("TransSDataConnectMsg failed.\n");
		return false;
	}
	strcpy_s(sbuf, ss.c_str());
	return true;
}

bool Client::SetSbufWithSDataConfirm(std::wstring billId,int operation)
{
	if (billId.c_str() == _T(""))
	{
		return false;
	}
	EncodingConvert encodingConvert;
	tSDataConfirm sdataConfirm;
	ParseJson parseJson;
	sdataConfirm.operation = operation;
	std::string utfstr;
	encodingConvert.UnicodeToUTF8(billId.c_str(), utfstr);
	sdataConfirm.billId = _strdup(utfstr.c_str());
	std::string ss;
	if (!parseJson.TransSDataToConfirmMsg(sdataConfirm, ss))
	{
		printf("TransSDataConfirmMsg failed.\n");
		return false;
	}
	strcpy_s(sbuf, ss.c_str());
	return true;
}

void Client::SendProc(SOCKET sockClient)
{
	int slen;
	if (sbuf[0])
	{
		//strcpy_s(sbuf,ByteOrderConvert(sbuf));
		int len;
		len = strnlen_s(sbuf, DATA_BUFFER);
		slen = send(sockClient, sbuf, len, 0);
		if (slen == SOCKET_ERROR)
		{
			printf("send() failed: %d\n.", WSAGetLastError());
			return;
		}
		else if (slen == 0)
		{
			return;
		}
		else
		{
			printf("send() %d bytes.\n", slen);
			memset(sbuf, 0, DATA_BUFFER);
		}
	}
}

void Client::RecvProc(SOCKET sockClient)
{
	EnterCriticalSection(&g_cs);
	int rlen;
	Client client;
	tRData rdata;
	bool sendConfirmFlag = false;
	memset(rbuf, 0, DATA_BUFFER);
	while (1)
	{
		if (transferred == false)
		{
			client.SetSbufWithSDataConnect(businessId);
			client.SendProc(sockClient);
			transferred = true;
		}
		rlen = recv(sockClient, rbuf, sizeof(rbuf), 0);
		if (rlen == 0)
		{
			break;
		}
		else if (rlen == SOCKET_ERROR)
		{
			printf("recv() failed:%d.\n", WSAGetLastError());
			break;
		}
		else
		{
			setlocale(LC_CTYPE, "");
			AutoInput autoInput;
			ParseJson parseJson;
			//char* s = ByteOrderConvert(rbuf);
			std::wstring w;
			std::string rmsg = rbuf;
			if (!parseJson.TransMsgToRData(rmsg, rdata))
			{
				printf("TransMsgToRData %s Error.\n", rmsg.c_str());
				sendConfirmFlag = false;
				break;
			}
			int LastError = GetLastError();
			if (0 == autoInput.ProcessToPID(processName))
			{
				SetLastError(0);
				if (autoInput.NeverRun(rdata) == true)
				{
					//runtax succeed,send 1 status
					sendConfirmFlag = true;
					break;
				}
				else
				{
					//runtax failed,send 2 status
					sendConfirmFlag = false;
					break;
				}
			}
			else
			{
				SetLastError(0);
				if (autoInput.IsInFillInPage() == true)
				{
					if (autoInput.CloseFillInWindow() && autoInput.AlreadyRunning(rdata))
					{
						sendConfirmFlag = true;
						break;
					}
				}
				else
				{
					sendConfirmFlag = false;
					break;
				}
			}
		}
	}
	if (sendConfirmFlag == false)
	{
		client.SetSbufWithSDataConfirm(rdata.billId, 2);
		client.SendProc(sockClient);
	}
	else
	{
		client.SetSbufWithSDataConfirm(rdata.billId, 1);
		client.SendProc(sockClient);
	}
	LeaveCriticalSection(&g_cs);
}


bool Client::SocketConnect()
{
	sockaddr_in ser_addr;
	clisock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (clisock == INVALID_SOCKET)
	{
		printf("socket() failed:%d.\n", WSAGetLastError());
		return false;
	}
	ser_addr.sin_family = AF_INET;
	ser_addr.sin_port = htons(PORT);
	ser_addr.sin_addr.s_addr = inet_addr(serverAddress);

	if (connect(clisock, (sockaddr*)&ser_addr, sizeof(ser_addr)) == INVALID_SOCKET)
	{
		printf("connect() failed:%d\n", WSAGetLastError());
		return false;
	}
	return true;
}


 









