#pragma once

#include <iostream>
#include <cstdlib>
#include <string>

#include "rapidjson\document.h"
#include "rapidjson\stringbuffer.h"
#include "rapidjson\writer.h"
#include "Data.h"

class ParseJson
{
public:

	ParseJson();

	~ParseJson();

	bool TransMsgToRData(std::string& msg, tRData& rdata);

	bool TransSDataToConnectMsg(tSDataConnect& sdataConnect, std::string &msg);

	bool TransSDataToConfirmMsg(tSDataConfirm& sdataConfirm, std::string &msg);

private:
	ParseJson(const ParseJson&);
	void operator=(const ParseJson&);

	void UTF8ToUnicode(const std::string utfstr, std::wstring& ustr);
	
	void UnicodeToUTF8(const std::wstring& wstr, std::string& utfstr);

};

