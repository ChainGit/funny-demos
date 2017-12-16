#ifndef _MYTCP_H_
#define _MYTCP_H_
#include "MyInclude.h"

extern int ls;
extern int flag;

int initSock(int IsServer);
int tcpConnect(const char *serverIP, unsigned short port);
int tcpAccept();
int tcpSend(unsigned int sock, const char *sendBuf, int sendBufLen);
int tcpRecv(unsigned int sock, char *recvBuf, int recvBufLen);
void tcpClose(unsigned int sock);

#endif 
