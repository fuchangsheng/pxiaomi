#include "Client.h"
#include "resource.h"

int main()
{

	SECURITY_ATTRIBUTES sa;
	SECURITY_DESCRIPTOR sd;

	InitializeSecurityDescriptor(&sd, SECURITY_DESCRIPTOR_REVISION);
	SetSecurityDescriptorDacl(&sd, TRUE, NULL, FALSE);
	sa.nLength = sizeof(SECURITY_ATTRIBUTES);
	sa.bInheritHandle = TRUE;
	sa.lpSecurityDescriptor = &sd;

	HANDLE hOnlyOneMonitor = CreateMutex(&sa, FALSE, _T("client"));
	if ((hOnlyOneMonitor == NULL) || (GetLastError() == ERROR_ALREADY_EXISTS))
	{
		MessageBox(NULL, _T("There is already another process instance running."), _T("Extra Operation"), MB_OK|MB_ICONEXCLAMATION);
		exit(-1);
	}

	//LoadIcon(NULL,MAKEINTRESOURCE(IDI_ICON1));
	LoadImage(NULL, MAKEINTRESOURCE(IDI_ICON1), IMAGE_ICON, 0, 0, LR_DEFAULTCOLOR);
	Client client;
	if (client.SocketConnect() == false)
	{
		system("pause");
		return -1;
	}

	client.SocketExe(client.getSocket(),0);

	return 0;
}