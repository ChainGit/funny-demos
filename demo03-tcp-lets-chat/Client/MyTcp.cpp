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
		printMsgExit("创建套接字失败!");
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
			printMsg("bind套接字失败!\n");
			closesocket(ls);
			WSACleanup();
			return -1;
		}

		if (listen(ls, 10)<0) {
			printMsg("listen套接字失败!\n");
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
		printMsgExit("连接服务器失败！\n");
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
		printMsgExit("服务器Accept异常！");
	}

	char tmp[40] = { 0 };

	sprintf_s(tmp, "客户端进入,IP为:%s ID为:%d\n", inet_ntoa(cliAddr.sin_addr), s);
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