#include "AutoInput.h"


	AutoInput::AutoInput()
	{

	}


	AutoInput::~AutoInput()
	{

	}

	bool AutoInput::NeverRun(const tRData& rdata)
	{
		return (RunTax() && Login() && HomePage() && FillInInvoice(rdata));
	}

	bool AutoInput::AlreadyRunning(const tRData& rdata)
	{
		return HomePage()&&FillInInvoice(rdata);
	}

	bool AutoInput::RunTax()
	{
		int ErrorCode;

		if (ErrorCode = WinExec("C:\\Program Files (x86)\\开票软件\\MainExecute.exe", SW_SHOWMAXIMIZED) <= 31)
		{
			switch (ErrorCode)
			{
			case 0:
				printf("Memory or resource are depleted.\n");
				break;
			case ERROR_BAD_FORMAT:
				printf("Invalid file.\n");
				break;
			case ERROR_FILE_NOT_FOUND:
				printf("Can not find specified file.\n");
				break;
			case ERROR_PATH_NOT_FOUND:
				printf("Can not find specified path.\n");
				break;
			default:
				printf("The reason of error is unknown.Return value :%d\n", ErrorCode);
				break;
			}
			printf("Open file failed :%d\n", GetLastError());
			return false;
		}
		return true;
	}
	
	bool AutoInput::CloseFillInWindow()
	{
		int LastError;
		//	Sleep(1000);
		LastError = Sim_Alt_F4();
		
		if (LastError != 0 && LastError != 18)
		{
			printf("SendInput Alt+F4 failed: %d.\n", GetLastError());
			return false;
		}
		HWND hwnd = NULL;
		for (int i = 0; NULL == hwnd; ++i)
		{
			hwnd = FindWindowEx(NULL, NULL, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"), _T("SysMessageBox"));
			if (30 == i)
			{
				printf("FindWindows SysMessageBox failed: %d\n", GetLastError());
				return false;
			}
			Sleep(100);
		}
		if (!SetForegroundWindow(hwnd))
		{
			printf("SetForegroundWindow failed: %d\n", GetLastError());
			return false;
		}
		Sleep(1000);
		sendSimInput.KeySimulationByChar('\n');
		return true;
	}

	bool AutoInput::Login()
	{
		int ErrorCode;
		HWND hwnd = NULL, hwndChild = NULL;
		for (int i = 0; NULL == hwnd; ++i)
		{
			hwnd = FindWindowEx(NULL, NULL, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"), _T("LoginForm"));
			if (50 == i)
			{
				printf("FindWindows LoginForm failed: %d\n", GetLastError());
				return false;
			}
			Sleep(100);
		}

		if (!SetForegroundWindow(hwnd))
		{
			printf("SetForegroundWindow failed: %d\n", GetLastError());
			return false;
		}
		
		if (ProcessPrivilege() == false)
		{
			printf("ProcessPrivilege failed.\n");
			return false;
		}

		EnumChildWindows(hwnd, EnumChildProc1, 0);
		if ((ErrorCode = GetLastError()) && (ErrorCode != 18))
		{
			printf("EnumChildWindows EnumChildProc1 failed.Error code is %d.\n", ErrorCode);
			return false;
		}

		//模拟鼠标改为tab+enter进入
		/*
		unsigned int x, y;
		x = 680;
		y = 460;
		sendSimInput.MouseSimulation(x, y);
		*/

		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
	
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
	
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\n')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'ENTER' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		return true;
	}
		//首页
	bool AutoInput::HomePage()
	{
		int ErrorCode;
		HWND hwnd = NULL;
		HWND hwndChild = NULL;
		for (int i = 0; NULL == hwnd; ++i)
		{
			hwnd = FindWindowEx(NULL, NULL, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"), _T("增值税发票税控开票软件（金税盘版） V2.1.20.160425.01"));
			if (50 == i)
			{
				printf("FindWindows \"增值税发票税控开票软件（金税盘版） V2.1.20.160425.01\" failed: %d\n", GetLastError());
				return false;
			}
			Sleep(100);
		}

		hwndChild = NULL;
		for (int i = 0; NULL == hwndChild; ++i)
		{
			hwndChild = sendSimInput.FindWithClassName(hwnd, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"));
			if (30 == i)
			{
				printf("FindWithClassName \"WindowsForms10.Window.8.app.0.3598b65_r14_ad1\" failed: %d\n", GetLastError());
				return false;
			}
			Sleep(100);
		}

		hwndChild = NULL;
		for (int i = 0; NULL == hwndChild; ++i)
		{
			hwndChild = sendSimInput.FindWithClassName(hwnd, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"));
			if (30 == i)
			{
				printf("FindWithClassName \"WindowsForms10.Window.8.app.0.3598b65_r14_ad1\" failed: %d\n", GetLastError());
				return false;
			}
			Sleep(100);
		}

	
		RECT rect;

		if (!GetWindowRect(hwndChild, &rect))
		{
			printf("GetWindowRect failed :%d", GetLastError());
			return false;
		}

		unsigned int x, y;
		x = rect.left + 154;
		y = rect.top + 10;
		Sleep(100);
		if ((ErrorCode = sendSimInput.MouseSimulation(x, y)) && (ErrorCode != 18))
		{
			printf("MouseSimulation at (%d,%d) failed. Error Code is %d.\n", x, y, ErrorCode);
			return false;
		}
		/*y = rect.top + 30;
		if ((ErrorCode = sendSimInput.MouseSimulation(x, y)) && (ErrorCode != 18))
		{
		printf("MouseSimulation at (%d,%d) failed. Error Code is %d.\n", x, y, ErrorCode);
		return false;
		}
		x = rect.left + 390;
		if ((ErrorCode = sendSimInput.MouseSimulation(x, y)) && (ErrorCode != 18))
		{
		printf("MouseSimulation at (%d,%d) failed. Error Code is %d.\n", x, y, ErrorCode);
		return false;
		}*/
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar(39)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'RIGHT' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\n')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'ENTER' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\n')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'ENTER' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		return true;
	}
		

	bool AutoInput::FillInInvoice(const tRData& rdata)
	{
		int ErrorCode;
		HWND hwnd = NULL;
		for (int i = 0; NULL == hwnd; ++i)
		{
			hwnd = hwnd = sendSimInput.PrepareToFillInReceipt();
			if (30 == i)
			{
				printf("FindWindows \"增值税发票税控开票软件（金税盘版） V2.1.20.160425.01\" failed: %d\n", GetLastError());
				return false;
			}
			Sleep(100);
		}
		

		HWND hInputWnd = NULL, cHwnd = NULL;

		//名称
		if (rdata.title == _T(""))
		{
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByStr(rdata.title)) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr rdata.title: %ls failed: %d\n", rdata.title, ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}

		//纳税人识别号
		//字母会被自动转换为大写
		if (rdata.taxNo == _T(""))
		{
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByStr(rdata.taxNo)) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr rdata.taxNo: %ls failed: %d\n", rdata.taxNo, ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar(40)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'DOWN' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\n')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'ENTER' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}

		//地址、电话
		std::wstring address = rdata.address;
		address.append(_T(" "));
		address.append(rdata.mobile);
		if (address == _T(""))
		{
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByStr(address.c_str())) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr address.c_str(): %ls failed: %d\n", address.c_str(), ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}

		//开户行及账号
		std::wstring accountNo = rdata.accountNo, bankDeposit = rdata.bankDeposit;
		if (rdata.bankDeposit != _T(""))
		{
			bankDeposit.append(_T(" "));
			bankDeposit.append(rdata.accountNo);
			if ((ErrorCode = sendSimInput.KeySimulationByStr(bankDeposit.c_str())) && (ErrorCode != 18))
			{
				printf("sendSimInput.KeySimulationByStr bankDeposit.c_str(): %ls failed: %d\n", bankDeposit.c_str(), ErrorCode);
				return false;
			}
		}
		else if (rdata.accountNo != _T(""))
		{
			if ((ErrorCode = sendSimInput.KeySimulationByStr(accountNo.c_str())) && (ErrorCode != 18))
			{
				printf("sendSimInput.KeySimulationByStr address.c_str(): %ls failed: %d\n", address.c_str(), ErrorCode);
				return false;
			}
		}
		else
		{
			//不填数据，模拟一次右键
			if ((ErrorCode = sendSimInput.KeySimulationByChar(39)) && (ErrorCode != 18))
			{
				printf("KeySimulationByChar 'RIGHT' failed. Error Code is %d.\n", ErrorCode);
				return false;
			}
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}


		//销售方地址电话
		if ((ErrorCode = sendSimInput.KeySimulationByChar(39)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'RIGHT' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}

		//销售方开户行及账号
		if ((ErrorCode = sendSimInput.KeySimulationByChar(39)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'RIGHT' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar('\t')) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'TAB' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}

		
		if ((ErrorCode = sendSimInput.KeySimulationByChar(8)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'BACKSPACE' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar(37)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'LEFT' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		
		if ((ErrorCode = sendSimInput.KeySimulationByChar(8)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'BACKSPACE' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}
		if ((ErrorCode = sendSimInput.KeySimulationByChar(37)) && (ErrorCode != 18))
		{
			printf("KeySimulationByChar 'LEFT' failed. Error Code is %d.\n", ErrorCode);
			return false;
		}

		//金额
		if ((ErrorCode = sendSimInput.KeySimulationByStr(rdata.amount)) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr rdata.amount failed: %d\n",  ErrorCode);
			return false;
		}

		Sim_Shift_Tab();
		//单价
		/*	if ((ErrorCode = sendSimInput.KeySimulationByChar('1')) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr \"单价\" failed: %d\n",  ErrorCode);
			return false;
		}*/
		Sim_Shift_Tab();
		//数量
		if ((ErrorCode = sendSimInput.KeySimulationByChar('1')) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr \"数量\" failed: %d\n", ErrorCode);
			return false;
		}
		Sim_Shift_Tab();
		//单位
		if ((ErrorCode = sendSimInput.KeySimulationByStr(_T("间"))) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr \"单位\" failed: %d\n", ErrorCode);
			return false;
		}
		Sim_Shift_Tab();

		//规格
		if ((ErrorCode = sendSimInput.KeySimulationByStr(_T("0"))) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr %ls failed: %d\n", _T("E"), ErrorCode);
			return false;
		}
		Sim_Shift_Tab();

		//商品名
		if ((ErrorCode = sendSimInput.KeySimulationByStr(rdata.content)) && (ErrorCode != 18))
		{
			printf("sendSimInput.KeySimulationByStr rdata.content failed: %d\n", ErrorCode);
			return false;
		}
			

		//发票大输入框
		/*if (!(hInputWnd = GetWindow(GetWindow(GetWindow(hwnd, GW_CHILD), GW_CHILD), GW_CHILD)))
		{
		printf("can not get input box hand\n");
		}*/

		//收款人
		//hwndChild = GetChildHwnd(hInputWnd, 4);

		//printf("hwnd :%x\n", hwndChild);
		//cHwnd = GetWindow(hwndChild, GW_CHILD);
		//printf("hwnd :%x\n", cHwnd);

		//SetWindowText(hwndChild, _T("收款人"));
		/*printf("SetWindowText:%d\n", GetLastError());
		int id;
		id=GetWindowLong(cHwnd, GWL_ID);
		printf("GetWindowLong:%d\n", GetLastError());
		printf("id=%x\n", id);
		printf("SetDlgItemText :%d\n",SetDlgItemText(hwndChild,id,_T("张三")));
		printf("SetDlgItemText failed:%d\n", GetLastError());*/

		/*	if (!GetWindowRect(hwndChild, &rect))
		{
		printf("GetWindowRect failed :%d\n", GetLastError());
		}*/

		//x = 420;
		//y = 740;
		//autoInput.MouseSimulation(x, y);
		//autoInput.KeySimulationByStr("E");
		return true;
	}

	BOOL CALLBACK AutoInput::EnumChildProc1(HWND hwndChild, LPARAM lParam)
	{
		if (hwndChild == NULL)
		{
			return false;
		}
		wchar_t *cmp = _T("登录");
		wchar_t buffer[1024];
		GetWindowText(hwndChild, buffer, sizeof(buffer) / sizeof(wchar_t));
		char szAString[1024];
		WideCharToMultiByte(CP_ACP, WC_COMPOSITECHECK, buffer, -1, szAString, sizeof(szAString), NULL, NULL);
		if (wcscmp(buffer, cmp) == 0)
		{
			return false;
		}

		EnumChildWindows(hwndChild, EnumChildProc1, lParam);
		return true;
	}


	BOOL AutoInput::ProcessPrivilege()
	{
		BOOL bEnable = false;
		BOOL bResult = true;
		HANDLE hToken = INVALID_HANDLE_VALUE;
		TOKEN_PRIVILEGES TokenPrivileges;
		if (OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY | TOKEN_ADJUST_PRIVILEGES, &hToken) == 0)
		{
			printf("OpenProcessToken Error: %d\n", GetLastError());
			bResult = false;
		}
		TokenPrivileges.PrivilegeCount = 1;
		TokenPrivileges.Privileges[0].Attributes = bEnable ? SE_PRIVILEGE_ENABLED : 0;
		LookupPrivilegeValue(NULL, SE_DEBUG_NAME, &TokenPrivileges.Privileges[0].Luid);
		AdjustTokenPrivileges(hToken, FALSE, &TokenPrivileges, sizeof(TOKEN_PRIVILEGES), NULL, NULL);
		if (GetLastError() != ERROR_SUCCESS)
		{
			bResult = false;
		}
		CloseHandle(hToken);

		return bResult;
	}

	HWND AutoInput::GetChildHwnd(HWND parentHwnd, UINT ordinal)
	{
		HWND cHwnd;
		cHwnd = GetWindow(parentHwnd, GW_CHILD);
		ordinal--;
		while (ordinal--)
		{
			cHwnd = GetWindow(cHwnd, GW_HWNDNEXT);
		}
		return cHwnd;
	}

	bool AutoInput::IsInFillInPage()
	{
		HWND hwnd = NULL;
		hwnd = FindWindowEx(NULL, NULL, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"), _T("开具增值税专用发票"));
		if (NULL == hwnd)
		{
			printf("FindWindows \"开具增值税专用发票\" failed: %d.\n", GetLastError());
			return false;
		}
		if (!SetForegroundWindow(hwnd))
		{
			printf("SetForegroundWindow failed: %d\n", GetLastError());
			return false;
		}
		return true;
	}

	DWORD AutoInput::ProcessToPID(const char *InputProcessName)
	{
		if (InputProcessName == NULL)
		{
			return false;
		}
		DWORD aProcesses[1024], cbNeeded, cProcesses;
		unsigned int i;
		HANDLE hProcess = NULL;
		HMODULE hMod = NULL;
		char szProcessName[MAX_PATH] = "UnknownProcess";

		// 计算目前有多少进程, aProcesses[]用来存放有效的进程PIDs 
		if (!EnumProcesses(aProcesses, sizeof(aProcesses), &cbNeeded))
		{
			return 0;
		}
		cProcesses = cbNeeded / sizeof(DWORD);
		//遍历所有的进程 
		for (i = 0; i < cProcesses; i++)
		{
			hProcess = OpenProcess(PROCESS_QUERY_INFORMATION |
				PROCESS_VM_READ,
				FALSE, aProcesses[i]);
			// 取得特定PID的进程名 并比较
			if (hProcess)
			{
				if (EnumProcessModules(hProcess, &hMod, sizeof(hMod), &cbNeeded))
				{
					GetModuleBaseNameA(hProcess, hMod,
						szProcessName, sizeof(szProcessName));
					if (!_stricmp(szProcessName, InputProcessName))
					{
						CloseHandle(hProcess);
						return aProcesses[i];
					}
				}
			}
		}
		CloseHandle(hProcess);
		return 0;
	}


	int AutoInput::Sim_Shift_Tab()
	{
		INPUT input[4];
		//memset(input, 0, sizeof(input));
		input[0].type = input[1].type = input[2].type = input[3].type = INPUT_KEYBOARD;
		input[0].ki.wScan = input[1].ki.wScan = input[2].ki.wScan = input[3].ki.wScan = 0;
		input[0].ki.wVk = input[2].ki.wVk = VK_SHIFT;
		input[1].ki.wVk = input[3].ki.wVk = VK_TAB;
		input[2].ki.dwFlags = input[3].ki.dwFlags = KEYEVENTF_KEYUP;
		input[0].ki.dwFlags = input[1].ki.dwFlags = 0;
		SendInput(4, input, sizeof(INPUT));
		return GetLastError();
	}

	int AutoInput::Sim_Alt_F4()
	{
		INPUT input[4];
		//memset(input, 0, sizeof(input));
		input[0].type = input[1].type = input[2].type = input[3].type = INPUT_KEYBOARD;
		input[0].ki.wScan = input[1].ki.wScan = input[2].ki.wScan = input[3].ki.wScan = 0;
		input[0].ki.wVk = input[2].ki.wVk = VK_MENU;
		input[1].ki.wVk = input[3].ki.wVk = VK_F4;
		input[2].ki.dwFlags = input[3].ki.dwFlags = KEYEVENTF_KEYUP;
		input[0].ki.dwFlags = input[1].ki.dwFlags = 0;
		SendInput(4, input, sizeof(INPUT));
		return GetLastError();
	}








