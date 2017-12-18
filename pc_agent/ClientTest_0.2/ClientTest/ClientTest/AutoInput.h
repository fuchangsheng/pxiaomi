#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>
#include <tchar.h>
#include <iostream>
#include "SendSimInput.h"
#include "Data.h"
#include "psapi.h"

class AutoInput
{
public:
	AutoInput();
	~AutoInput();

	//No tax software if running
	bool NeverRun(const tRData& rdata);

	//Tax software is running.
	bool AlreadyRunning(const tRData& rdata);

	//whether in the fill-in page
	bool IsInFillInPage();

	//Close window
	bool CloseFillInWindow();
	
	//Accroding to the process name to seek process id
	DWORD ProcessToPID(const char *InputProcessName);
private:
	SendSimInput sendSimInput;

	//Run tax sofeware.
	bool RunTax();

	//Auto login,enter home page
	bool Login();

	//Home page of tax software,enter fill-in page
	bool HomePage();

	//Fill-in page,auto fill in tax messages
	bool FillInInvoice(const tRData& rdata);

	//Shift+Tab,switch to last control
	int Sim_Shift_Tab();

	//Alt+F4,close window
	int Sim_Alt_F4();

	//Seek "µÇÂ¼" button to determine whether to enter login page 
	static BOOL CALLBACK EnumChildProc1(HWND hwndChild, LPARAM lParam);

	//Access to operating right
	BOOL ProcessPrivilege();

	HWND GetChildHwnd(HWND parentHwnd, UINT ordinal);
	AutoInput(const AutoInput&);
	void operator=(const AutoInput&);
};


