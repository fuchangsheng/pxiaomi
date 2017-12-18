#pragma once
#include <Winsock2.h>
#include <stdio.h>
#include <cstdlib>
#include <iostream>
#include <Windows.h>
#include <tchar.h>
#include <locale.h>
#include <string>
#include "AutoInput.h"
#include "ParseJson.h"
#include "Data.h"
#include "EncodingConvert.h"
#pragma comment(lib,"ws2_32.lib")

class Client
{
public:
	Client();

	~Client();

	SOCKET getSocket();

	bool SocketConnect();

	bool SocketExe(SOCKET clisock,unsigned int threadNum);

private:	
	SOCKET clisock;
	static CRITICAL_SECTION g_cs;
	static const int DATA_BUFFER = 1024;
	static const int PORT = 8989;
	static char sbuf[DATA_BUFFER];
	static char rbuf[DATA_BUFFER];

	static const char* const businessId;
	static const char* const processName;

	static bool transferred;
	static const char* serverAddress;

	bool SetSbufWithSDataConnect(const char* businessId);

	bool SetSbufWithSDataConfirm(std::wstring billId, int operation);

	//static void SendProc(SOCKET sockClient);
	void SendProc(SOCKET sockClient);

	static void RecvProc(SOCKET sockClient);

	char* ByteOrderConvert(char* nstr);

	Client(const Client&);
	void operator=(const Client&);
};


