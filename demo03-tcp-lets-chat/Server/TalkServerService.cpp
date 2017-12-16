#include "TalkServerService.h"

struct ClientInfo cinfo[10];  //客户端信息记录区

/**   启动服务器 */
void startServer()
{
	//system("cls");

	DWORD threadId;

	if (initSock(IS_SERVER) < 0){ //创建服务器套接字	
		exit(0);
	}

	//创建一个线程专用于接受客户端的连接    
	CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)myAcceptThread, NULL, 0, &threadId);
	return;
}

void myAcceptThread()
{
	system("cls");

	DWORD threadId;
	printMsg("开始myAcceptThread线程..\n");

	while (1)
	{
		printMsg("等待客户端连接..\n");
		int cs = tcpAccept();
		//当有一个连接上以后创建一个线程专门接收该连接的数据
		int ind = findIdelCinfo();
		if (ind == -1) {//服务器溢出
			continue;
		}

		//得判断QQ是否冲突
		cinfo[ind].isFree = 1; //已经占用标志
		cinfo[ind].sock = cs;
		CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)myRecvThread, &cinfo[ind], 0, &threadId);
	}

	printMsg("退出Accept线程..\n");
	return;
}

//根据传入的套接字接收数据
void myRecvThread(ClientInfo *pcinfo)
{
	char buf[1024] = { 0 };

	sprintf_s(buf, "开始myRecvThread线程:%d..\n", pcinfo->threadId);
	printMsg(buf);

	while (1)
	{
		memset(buf, 0, sizeof(buf));
		int len = tcpRecv(pcinfo->sock, buf, sizeof(buf));
		if (len < 0) break;//判断客户端是否下线

		unsigned char id = (unsigned char)buf[0];
		switch (id)
		{
		case LOGIN_MSG:
			procLoginMsg(pcinfo, (struct LoginMsg *)buf);
			break;

		case QUERY_FQQ:
			procFriendQqMsg(pcinfo, (struct FriendQqMsg *)buf);
			break;

		case TALK_MSG:
			procTalkMsg((struct TalkMsg *)buf);
			break;
		case SHOW_ONLINE:
			repose_online(pcinfo);
			break;
		}
	}

	pcinfo->isFree = 0;
	sprintf_s(buf, "结束myRecvThread线程:%d..\n", pcinfo->threadId);
	printMsg(buf);
}

void procLoginMsg(struct ClientInfo * pcinfo, struct LoginMsg *msg)
{
	strcpy_s(pcinfo->qq, msg->qq);  //保存qq到服务器缓冲区

	//发送登录成功消息
	struct LoginMsgResponse res;
	res.id = LOGIN_MSG_RES;
	res.isOK = 1;

	int i = 0;
	int m = 0;
	//处理QQ号是否已经登陆
	for (i = 0; i<10; i++)
	{
		if (cinfo[i].isFree == 0) continue;

		if (strcmp(cinfo[i].qq, msg->qq) == 0)
		{
			//查找该QQ号出现次数
			m++;
		}
	}

	if (m>1){
		pcinfo->isFree = 0;
		res.isOK = 0;
	}

	tcpSend(pcinfo->sock, (const char *)&res, sizeof(res));
}

void procFriendQqMsg(struct ClientInfo * pcinfo, struct FriendQqMsg *msg)
{
	int i;

	struct FriendQqMsgResponse res;
	res.id = QUERY_FQQ_RES;
	res.isOK = 0;  //没有找到

	for (i = 0; i<sizeof(cinfo) / sizeof(struct ClientInfo); i++)
	{
		if (cinfo[i].isFree == 0) continue;
		if (strcmp(cinfo[i].qq, msg->qq) == 0)
		{
			res.isOK = 1; //好友在线
		}
	}

	//发送好友QQ查找结果消息
	tcpSend(pcinfo->sock, (const char *)&res, sizeof(res));
}

void procTalkMsg(struct TalkMsg *pmsg)
{
	int i;

	for (i = 0; i<sizeof(cinfo) / sizeof(struct ClientInfo); i++)
	{
		if (cinfo[i].isFree == 0) continue;

		if (strcmp(cinfo[i].qq, pmsg->fqq) == 0)
		{
			//将聊天消息转发给好友
			tcpSend(cinfo[i].sock, (const char *)pmsg, sizeof(struct TalkMsg));
			return;
		}
	}

	//好友可能下线了，下面的代码请你完善
	//服务器给发起聊天的客户端发送好友不在线的消息
	char *s_buf = "Friend is Offline!";
	strcpy(pmsg->info, s_buf);
	for (i = 0; i<sizeof(cinfo) / sizeof(struct ClientInfo); i++)
	{
		if (cinfo[i].isFree == 0) continue;

		if (strcmp(cinfo[i].qq, pmsg->qq) == 0)
		{
			tcpSend(cinfo[i].sock, (const char *)pmsg, sizeof(struct TalkMsg));
			return;
		}
	}

}

/** 在客户端信息记录区查找空闲的元素下标 **/
int findIdelCinfo()
{
	int i;
	for (i = 0; i<sizeof(cinfo) / sizeof(struct ClientInfo); i++)
	{
		if (cinfo[i].isFree == 0)
		{
			return i;
		}
	}

	return -1;
}

void repose_online(ClientInfo *pcinfo){
	struct OnlineF onf;
	onf.onf_num = 0;
	onf.id = SHOW_ONLINE_RES;
	int i = 0;
	for (i = 0; i < 10; i++){
		if (cinfo[i].isFree != 0){
			strcpy(onf.onf[i], cinfo[i].qq);
			onf.onf_num++;
		}
	}
	tcpSend(pcinfo->sock, (const char *)&onf, sizeof(struct OnlineF));
	return;
}