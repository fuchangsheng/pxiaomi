#include "SendSimInput.h"

SendSimInput::SendSimInput()
{

}

SendSimInput::~SendSimInput()
{

}

int SendSimInput::KeySimulationByChar(wchar_t ch)
{
	INPUT keyInput[3];
	memset(keyInput, 0, sizeof(keyInput));
	keyInput[0].type = INPUT_KEYBOARD;
	keyInput[0].ki.dwFlags = 0;
	keyInput[0].ki.wScan = 0;

	keyInput[1].type = INPUT_KEYBOARD;
	keyInput[1].ki.wScan = 0;
	keyInput[1].ki.dwFlags = KEYEVENTF_KEYUP;
	switch (ch)
	{
		//return
	case '\n':
		keyInput[0].ki.wVk = VK_RETURN;
		keyInput[1].ki.wVk = VK_RETURN;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//tab
	case '\t':
		keyInput[0].ki.wVk = VK_TAB;
		keyInput[1].ki.wVk = VK_TAB;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//space
	case '\0':
		keyInput[0].ki.wVk = VK_SPACE;
		keyInput[1].ki.wVk = VK_SPACE;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//backspace
	case 8:
		keyInput[0].ki.wVk = VK_BACK;
		keyInput[1].ki.wVk = VK_BACK;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//left
	case 37:
		keyInput[0].ki.wVk = VK_LEFT;
		keyInput[1].ki.wVk = VK_LEFT;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//up
	case 38:
		keyInput[0].ki.wVk = VK_UP;
		keyInput[1].ki.wVk = VK_UP;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//right
	case 39:
		keyInput[0].ki.wVk = VK_RIGHT;
		keyInput[1].ki.wVk = VK_RIGHT;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		//down
	case 40:
		keyInput[0].ki.wVk = VK_DOWN;
		keyInput[1].ki.wVk = VK_DOWN;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
		/*
		//alt
	case 18:
		keyInput[0].ki.wVk = VK_MENU;
		keyInput[1].ki.wVk = VK_MENU;
		SendInput(1, keyInput, sizeof(INPUT));
		break;
		*/
		//F4
	case 115:
		keyInput[0].ki.wVk = VK_F4;
		keyInput[1].ki.wVk = VK_F4;
		SendInput(2, keyInput, sizeof(INPUT));
		break;
	default:
		//upper case
		if (ch >= 0x41 && ch <= 0x5A)
		{
			keyInput[0].type = INPUT_KEYBOARD;
			keyInput[0].ki.dwFlags = 0;
			keyInput[0].ki.wScan = 0;
			keyInput[0].ki.wVk = VK_SHIFT;

			keyInput[1].type = INPUT_KEYBOARD;
			keyInput[1].ki.dwFlags = 0;
			keyInput[1].ki.wScan = 0;
			keyInput[1].ki.wVk = ch;

			keyInput[2].type = INPUT_KEYBOARD;
			keyInput[2].ki.dwFlags = KEYEVENTF_KEYUP;
			keyInput[2].ki.wScan = 0;
			keyInput[2].ki.wVk = VK_SHIFT;
			SendInput(3, keyInput, sizeof(INPUT));
		}
		else
		{
			memset(keyInput, 0, 2 * sizeof(INPUT));

			keyInput[0].type = INPUT_KEYBOARD;
			keyInput[0].ki.wVk = 0;
			keyInput[0].ki.wScan = ch;
			keyInput[0].ki.dwFlags = 0x4;//KEYEVENTF_UNICODE;

			keyInput[1].type = INPUT_KEYBOARD;
			keyInput[1].ki.wVk = 0;
			keyInput[1].ki.wScan = ch;
			keyInput[1].ki.dwFlags = KEYEVENTF_KEYUP | 0x4;//KEYEVENTF_UNICODE;

			SendInput(2, keyInput, sizeof(INPUT));
		}
		break;
	}
	return GetLastError();
}

int SendSimInput::KeySimulationByStr(const wchar_t* str)
{
	if (str == NULL)
	{
		SetLastError(13);
		return GetLastError();
	}
	int len = lstrlen(str);
	for (int index = 0; index < len; ++index)
	{
		KeySimulationByChar(str[index]);
	}	
	return GetLastError();
}

int SendSimInput::MouseSimulation(UINT x, UINT y)
{
	INPUT mouseInput;
	memset(&mouseInput, 0, sizeof(mouseInput));
	mouseInput.type = INPUT_MOUSE;
	mouseInput.mi.time = 0;
	mouseInput.mi.dx = static_cast<long>(65535 * x / (GetSystemMetrics(SM_CXSCREEN)));
	mouseInput.mi.dy = static_cast<long>(65535 * y / (GetSystemMetrics(SM_CYSCREEN)));
	mouseInput.mi.dwFlags = MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE | MOUSEEVENTF_LEFTDOWN | MOUSEEVENTF_LEFTUP;
	SendInput(1, &mouseInput, sizeof(INPUT));
	return GetLastError();
}

int SendSimInput::RunExe(LPCSTR fileName, UINT uCmdShow)
{
	return WinExec(fileName, uCmdShow);
}

HWND SendSimInput::PrepareToFillInReceipt()
{
	return FindWindowEx(NULL, NULL, _T("WindowsForms10.Window.8.app.0.3598b65_r14_ad1"), _T("开具增值税专用发票"));
}

HWND SendSimInput::FindWithClassName(HWND hwnd, const TCHAR* findClassName)
{ 
	HWND hChild = ::GetWindow(hwnd, GW_CHILD);

	TCHAR className[300];
	//for (; hChild != NULL; hChild = ::GetWindow(hChild, GW_HWNDNEXT))
	//{
	//	::GetClassName(hChild, ClassName, sizeof(ClassName) / sizeof(TCHAR));

	//	if (_tcscmp(ClassName, findClassName) == 0)
	//		return hChild;

	//	HWND FindWnd = FindWithClassName(hChild, findClassName);
	//	if (FindWnd)
	//		return FindWnd;
	//}

	//Get the handle to the ParentWnd's second child window. 
	if (hChild && (hChild = ::GetWindow(hChild, GW_HWNDNEXT)))
	{
		::GetClassName(hChild, className, sizeof(className) / sizeof(TCHAR));
		if (!_tcscmp(className, findClassName))
		{
			return hChild;
		}
	}
	return NULL;
}





