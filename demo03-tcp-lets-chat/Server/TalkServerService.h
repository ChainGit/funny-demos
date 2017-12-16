#ifndef _TALKSERVERSERVICE_H_
#define _TALKSERVERSERVICE_H_
#include "MyInclude.h"

struct ClientInfo
{
	char qq[6];               //客户端ID号
	int sock;                //客户端已经建立连接的套接字
	unsigned char isFree;    //空闲标志
	unsigned int threadId;   //为处理该客户端建立的接收线程ID
};

void startServer();
void myAcceptThread();
void myRecvThread(ClientInfo *pinfo);
void procLoginMsg(struct ClientInfo * pcinfo, struct LoginMsg *msg);
void procFriendQqMsg(struct ClientInfo * pcinfo, struct FriendQqMsg *msg);
void procTalkMsg(struct TalkMsg *msg);
int findIdelCinfo();
void repose_online(ClientInfo *pcinfo);

#endif


