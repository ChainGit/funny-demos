#include "MyTcp.h"

int ls;
int flag;

int initSock(int IsServer)
{
	WSADATA data;

	if (WSAStartup(1, &data) < 0){
		printMsgExit("call WSAStartup() failure!");
		WSACleanup();
		return -1;
	}
		

	ls = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (ls < 0){
		printMsgExit("�����׽���ʧ��!");
		closesocket(ls);
		WSACleanup();
		return -1;
	}
		

	flag = IsServer;
	if (flag == 1)
	{
		struct sockaddr_in servAddr;
		memset(&servAddr, 0, sizeof(servAddr));
		servAddr.sin_family = AF_INET;
		servAddr.sin_addr.s_addr = htonl(INADDR_ANY);
		servAddr.sin_port = CLI_PORT;

		if (bind(ls, (struct sockaddr *)&servAddr, sizeof(servAddr))<0) {
			printMsg("bind�׽���ʧ��!\n");
			closesocket(ls);
			WSACleanup();
			return -1;
		}

		if (listen(ls, 10)<0) {
			printMsg("listen�׽���ʧ��!\n");
			closesocket(ls);
			WSACleanup();
			return -1;
		}

	}

	return 0;
}


int tcpConnect(const char *serverIP, unsigned short port)
{
	struct sockaddr_in servAddr;

	memset(&servAddr, 0, sizeof(servAddr));
	servAddr.sin_family = AF_INET;
	servAddr.sin_addr.s_addr = inet_addr(serverIP);
	servAddr.sin_port = htons(port);

	if (connect(ls, (struct sockaddr *)&servAddr, sizeof(servAddr))<0)
	{
		closesocket(ls);
		WSACleanup();
		printMsgExit("���ӷ�����ʧ�ܣ�\n");
		return -1;
	}

	return ls;
}

int tcpAccept()
{
	struct sockaddr_in cliAddr;
	int cliAddrLen = sizeof(cliAddr);

	int s = accept(ls, (struct sockaddr *)&cliAddr, &cliAddrLen);
	if (s < 0)
	{
		closesocket(ls);
		WSACleanup();
		printMsgExit("������Accept�쳣��");
	}

	char tmp[40] = { 0 };

	sprintf_s(tmp, "�ͻ��˽���,IPΪ:%s IDΪ:%d\n", inet_ntoa(cliAddr.sin_addr), s);
	printMsg(tmp);

	return s;
}



int tcpSend(unsigned int sock, const char *sendBuf, int sendBufLen)
{
	int len = send(sock, sendBuf, sendBufLen, 0);
	return len;
}

int tcpRecv(unsigned int sock, char *recvBuf, int recvBufLen)
{
	int len = recv(sock, recvBuf, recvBufLen, 0);
	return len;
}

void tcpClose(unsigned int sock)
{
	closesocket(sock);
	WSACleanup();
}