#pragma once
#include <iostream>
#include <cstdlib>
#include <Windows.h>

class EncodingConvert
{
public:
	EncodingConvert();
	~EncodingConvert();

	void UnicodeToUTF8(const std::wstring& wstr, std::string& str);

	void UTF8ToUnicode(const std::string utfstr, std::wstring& ustr);

private:
	EncodingConvert(const EncodingConvert&); 
	void operator=(const EncodingConvert&);
};