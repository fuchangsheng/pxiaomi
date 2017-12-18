#pragma once
#include <iostream>
#include <tchar.h>

//Receive Data
typedef struct RData
{
	wchar_t* billId;//服务器标志，不需要解析，只需直接传给SData.billId
	int type;//发票类型，判断专、普
	wchar_t* title;//发票抬头
	wchar_t* taxNo;//税号
	wchar_t* bankDeposit;//购买方开户银行
	wchar_t* accountNo;//购买方开户账号
	wchar_t* address;//购买方地址
	wchar_t* mobile;//购买方手机号
	wchar_t* businessName;//商户名称
	wchar_t* amount;//金额
	wchar_t* content;//商品内容
	float rate;//税率
}tRData;

//Send only when the connection is successful 
typedef struct SDataConnect
{
	int operation;
	char* businessId;
}tSDataConnect;

//Send to server,to indicate auto-input is successful or not 
typedef struct SDataConfirm
{
	int operation;
	char* billId;
}tSDataConfirm;




