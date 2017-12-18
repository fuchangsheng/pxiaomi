#pragma once
#include <iostream>
#include <string>
#include <tchar.h>
#include <windows.h>

class SendSimInput
{
public:
	SendSimInput();

	~SendSimInput();

	//Simulate keyboard enter by char
	int KeySimulationByChar(wchar_t ch);

	//Simulate keyboard enter by string
	int KeySimulationByStr(const wchar_t* str);

	//Simulate mouse click through coordinate(x,y),
	//x,y:Relative to the upper left corner of the screen ,the horizontal dislpacement and the vertical displacement,in pixels
	int MouseSimulation(UINT x, UINT y);

	//according to the file path and file name(fileName),open file in uCmdShow mode
	int RunExe(LPCSTR fileName, UINT uCmdShow);

	//find specific window to judge whether the program is in the receipt interface 
	HWND PrepareToFillInReceipt();

	//find window by class name
	HWND FindWithClassName(HWND hwnd, const TCHAR* FindClassName);

private:
	SendSimInput(const SendSimInput&);
	void operator=(const SendSimInput&);
};




