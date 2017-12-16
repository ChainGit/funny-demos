#ifndef _TALKSERVERSERVICE_H_
#define _TALKSERVERSERVICE_H_
#include "MyInclude.h"

struct ClientInfo
{
	char qq[6];               //�ͻ���ID��
	int sock;                //�ͻ����Ѿ��������ӵ��׽���
	unsigned char isFree;    //���б�־
	unsigned int threadId;   //Ϊ����ÿͻ��˽����Ľ����߳�ID
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


