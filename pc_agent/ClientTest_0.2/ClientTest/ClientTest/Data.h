#pragma once
#include <iostream>
#include <tchar.h>

//Receive Data
typedef struct RData
{
	wchar_t* billId;//��������־������Ҫ������ֻ��ֱ�Ӵ���SData.billId
	int type;//��Ʊ���ͣ��ж�ר����
	wchar_t* title;//��Ʊ̧ͷ
	wchar_t* taxNo;//˰��
	wchar_t* bankDeposit;//���򷽿�������
	wchar_t* accountNo;//���򷽿����˺�
	wchar_t* address;//���򷽵�ַ
	wchar_t* mobile;//�����ֻ���
	wchar_t* businessName;//�̻�����
	wchar_t* amount;//���
	wchar_t* content;//��Ʒ����
	float rate;//˰��
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




