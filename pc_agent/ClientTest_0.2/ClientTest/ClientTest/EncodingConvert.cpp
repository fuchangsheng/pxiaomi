#include "EncodingConvert.h"

EncodingConvert::EncodingConvert()
{
	
}

EncodingConvert::~EncodingConvert()
{

}

void EncodingConvert::UnicodeToUTF8(const std::wstring& wstr, std::string& str)
{
	int iTextLen;
	char s[1024];
	iTextLen = WideCharToMultiByte(CP_UTF8, 0, wstr.c_str(), -1, NULL, 0, NULL, NULL);
	WideCharToMultiByte(CP_UTF8, 0, wstr.c_str(), -1, s, iTextLen, NULL, NULL);
	str = s;
}

void EncodingConvert::UTF8ToUnicode(const std::string utfstr, std::wstring& ustr)
{
	int uLen = MultiByteToWideChar(CP_UTF8, 0, utfstr.c_str(), -1, NULL, 0);
	wchar_t* pUnicode = new wchar_t[uLen + 1];
	memset(pUnicode, 0, sizeof(wchar_t)*(uLen + 1));
	MultiByteToWideChar(CP_UTF8, 0, utfstr.c_str(), -1, pUnicode, uLen);
	ustr = pUnicode;
}

