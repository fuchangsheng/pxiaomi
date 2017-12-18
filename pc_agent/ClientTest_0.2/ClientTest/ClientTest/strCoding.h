#pragma once
#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;
class strCoding
{
public:
	strCoding(void);
	virtual ~strCoding(void);

	void UTF_8ToGB2312(string &pOut, char *pText, int pLen);
	void GB2312ToUTF_8(string& pOut, char *pText, int pLen);                    
	void UTF_8ToUnicode(WCHAR* pOut, char *pText);

private:
	void Gb2312ToUnicode(WCHAR* pOut, char *gbBuffer);

	void UnicodeToUTF_8(char* pOut, WCHAR* pText);
	void UnicodeToGB2312(char* pOut, WCHAR uData);
	char CharToInt(char ch);
	char StrToBin(char *str);

};