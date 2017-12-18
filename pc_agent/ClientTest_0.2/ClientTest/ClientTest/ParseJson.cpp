#include "ParseJson.h"
#include "EncodingConvert.h"
ParseJson::ParseJson(){}

ParseJson::~ParseJson(){}

void ParseJson::UTF8ToUnicode(const std::string utfstr, std::wstring& ustr)
{
	///*rapidjson::StringStream source(utfstr.c_str());
	//rapidjson::GenericStringBuffer<rapidjson::UTF16LE<> > target;
	//bool hasError = false;
	//while (source.Peek() != '\0')
	//{
	//	if (!rapidjson::Transcoder<rapidjson::UTF8<>, rapidjson::UTF16LE<> >::Transcode(source, target))
	//	{
	//		hasError = true;
	//		break;
	//	}
	//}
	//if (!hasError)
	//{
	//	ustr = target.GetString();
	//}*/
	//const char* s = utfstr.c_str(); // UTF-8 string
	//rapidjson::StringStream source(s);
	//rapidjson::GenericStringBuffer<rapidjson::UTF16<> > target;
	//bool hasError = false;
	//while (source.Peek() != '\0')
	//	if (!rapidjson::Transcoder<rapidjson::UTF8<>, rapidjson::UTF16<> >::Transcode(source, target)) {
	//		hasError = true;
	//		break;
	//	}

		int uLen = MultiByteToWideChar(CP_UTF8, 0, utfstr.c_str(), -1, NULL, 0);
		wchar_t* pUnicode = new wchar_t[uLen + 1];
		memset(pUnicode, 0, sizeof(wchar_t)*(uLen + 1));
		MultiByteToWideChar(CP_UTF8, 0, utfstr.c_str(), -1, pUnicode, uLen);
		ustr = pUnicode;
}

void ParseJson::UnicodeToUTF8(const std::wstring& wstr, std::string& utfstr)
{
	/*typedef rapidjson::GenericStringStream<rapidjson::UTF16LE<> > StringStreamUnicode;
	StringStreamUnicode source(wstr.c_str());
	rapidjson::GenericStringBuffer<rapidjson::UTF8<> > target;

	bool hasError = false;
	while (source.Peek() != _T('\0'))
	{
		if (!rapidjson::Transcoder<rapidjson::UTF16LE<>, rapidjson::UTF8<> >::Transcode(source, target))
		{
			hasError = true;
			break;
		}
	}
	if (!hasError)
	{
		utfstr = target.GetString();
	}*/

		int iTextLen;
		char s[1024];
		iTextLen = WideCharToMultiByte(CP_UTF8, 0, wstr.c_str(), -1, NULL, 0, NULL, NULL);
		WideCharToMultiByte(CP_UTF8, 0, wstr.c_str(), -1, s, iTextLen, NULL, NULL);
		utfstr = s;

}

bool ParseJson::TransSDataToConfirmMsg(tSDataConfirm& sdataConfirm, std::string &msg)
{
	rapidjson::Document doc;
	doc.SetObject();
	rapidjson::Value v;
	v.SetString(sdataConfirm.billId,strlen(sdataConfirm.billId));
	rapidjson::Document::AllocatorType& allocator = doc.GetAllocator();
	doc.AddMember("operation", sdataConfirm.operation, allocator);
	doc.AddMember("billId", v, allocator);

	rapidjson::StringBuffer buffer;
	rapidjson::Writer<rapidjson::StringBuffer> writer(buffer);
	doc.Accept(writer);
	msg = buffer.GetString();
	return true;
}

bool ParseJson::TransSDataToConnectMsg(tSDataConnect& sdataConnect, std::string &msg)
{
	rapidjson::Document doc;
	doc.SetObject();
	rapidjson::Value v;
	v.SetString(sdataConnect.businessId, strlen(sdataConnect.businessId));
	rapidjson::Document::AllocatorType& allocator = doc.GetAllocator();
	doc.AddMember("operation", sdataConnect.operation, allocator);
	doc.AddMember("businessId", v, allocator);

	rapidjson::StringBuffer buffer;
	rapidjson::Writer<rapidjson::StringBuffer> writer(buffer);
	doc.Accept(writer);
	msg = buffer.GetString();
	return true;
}


bool ParseJson::TransMsgToRData(std::string& msgUtf, tRData &rdata)
{
	typedef rapidjson::GenericDocument<rapidjson::UTF16<> > wDocument;
	EncodingConvert encodingConvert;
	std::wstring wstr;
	encodingConvert.UTF8ToUnicode(msgUtf,wstr);
	wDocument doc;
	
	doc.Parse(wstr.c_str());
	if (doc.HasParseError())
	{
		rapidjson::ParseErrorCode code = doc.GetParseError();
		std::cout << "Parse error: " << code << std::endl;
		return false;
	}
	
	if (!doc.HasMember(_T("billId")))
	{
		std::cout << "Key \"billId\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("billId")].IsString())
	{
		std::cout << "Value of \"billId\" is not string type." << std::endl;
	}
	rdata.billId = _wcsdup(doc[TEXT("billId")].GetString());

	if (!doc.HasMember(_T("type")))
	{
		std::cout << "Key \"type\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("type")].IsInt())
	{
		std::cout << "Value of \"type\" is not int type." << std::endl;
		return false;
	}
	rdata.type = doc[_T("type")].GetInt();

	if (!doc.HasMember(_T("title")))
	{
		std::cout << "Key \"title\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("title")].IsString())
	{
		std::cout << "Value of \"title\" is not string type." << std::endl;
		return false;
	}
	rdata.title = _wcsdup(doc[_T("title")].GetString());

	if (!doc.HasMember(_T("taxNo")))
	{
		std::cout << "Key \"taxNo\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("taxNo")].IsString())
	{
		std::cout << "Value of \"taxNo\" is not string type." << std::endl;
		return false;
	}
	rdata.taxNo = _wcsdup(doc[_T("taxNo")].GetString());

	if (!doc.HasMember(_T("bankDeposit")))
	{
		std::cout << "Key \"bankDeposit\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("bankDeposit")].IsString())
	{
		std::cout << "Value of \"bankDeposit\" is not string type." << std::endl;
		return false;
	}
	rdata.bankDeposit = _wcsdup(doc[_T("bankDeposit")].GetString());

	if (!doc.HasMember(_T("accountNo")))
	{
		std::cout << "Key \"accountNo\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("accountNo")].IsString())
	{
		std::cout << "Value of \"accountNo\" is not string type." << std::endl;
		return false;
	}
	rdata.accountNo = _wcsdup(doc[_T("accountNo")].GetString());

	if (!doc.HasMember(_T("address")))
	{
		std::cout << "Key \"address\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("address")].IsString())
	{
		std::cout << "Value of \"address\" is not string type." << std::endl;
		return false;
	}
	rdata.address = _wcsdup(doc[_T("address")].GetString());

	if (!doc.HasMember(_T("mobile")))
	{
		std::cout << "Key \"mobile\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("mobile")].IsString())
	{
		std::cout << "Value of \"mobile\" is not string type." << std::endl;
		return false;
	}
	rdata.mobile = _wcsdup(doc[_T("mobile")].GetString());
	

	if (!doc.HasMember(_T("businessName")))
	{
		std::cout << "Key \"businessName\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("businessName")].IsString())
	{
		std::cout << "Value of \"businessName\" is not string type." << std::endl;
		return false;
	}
	rdata.businessName = _wcsdup(doc[_T("businessName")].GetString());

	if (!doc.HasMember(_T("amount")))
	{
		std::cout << "Key \"amount\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("amount")].IsString())
	{
		std::cout << "Value of \"amount\" is not string type." << std::endl;
		return false;
	}
	rdata.amount = _wcsdup(doc[_T("amount")].GetString());

	if (!doc.HasMember(_T("content")))
	{
		std::cout << "Key \"content\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("content")].IsString())
	{
		std::cout << "Value of \"content\" is not string type." << std::endl;
		return false;
	}
	rdata.content = _wcsdup(doc[_T("content")].GetString());

	if (!doc.HasMember(_T("rate")))
	{
		std::cout << "Key \"rate\" is absence." << std::endl;
		return false;
	}
	if (!doc[_T("rate")].IsFloat())
	{
		std::cout << "Value of \"rate\" is not int type." << std::endl;
		return false;
	}
	rdata.rate = doc[_T("rate")].GetFloat();

	return true;
}


