#include "TalkServerService.h"

struct ClientInfo cinfo[10];  //�ͻ�����Ϣ��¼��

/**   ���������� */
void startServer()
{
	//system("cls");

	DWORD threadId;

	if (initSock(IS_SERVER) < 0){ //�����������׽���	
		exit(0);
	}

	//����һ���߳�ר���ڽ��ܿͻ��˵�����    
	CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)myAcceptThread, NULL, 0, &threadId);
	return;
}

void myAcceptThread()
{
	system("cls");

	DWORD threadId;
	printMsg("��ʼmyAcceptThread�߳�..\n");

	while (1)
	{
		printMsg("�ȴ��ͻ�������..\n");
		int cs = tcpAccept();
		//����һ���������Ժ󴴽�һ���߳�ר�Ž��ո����ӵ�����
		int ind = findIdelCinfo();
		if (ind == -1) {//���������
			continue;
		}

		//���ж�QQ�Ƿ��ͻ
		cinfo[ind].isFree = 1; //�Ѿ�ռ�ñ�־
		cinfo[ind].sock = cs;
		CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)myRecvThread, &cinfo[ind], 0, &threadId);
	}

	printMsg("�˳�Accept�߳�..\n");
	return;
}

//���ݴ�����׽��ֽ�������
void myRecvThread(ClientInfo *pcinfo)
{
	char buf[1024] = { 0 };

	sprintf_s(buf, "��ʼmyRecvThread�߳�:%d..\n", pcinfo->threadId);
	printMsg(buf);

	while (1)
	{
		memset(buf, 0, sizeof(buf));
		int len = tcpRecv(pcinfo->sock, buf, sizeof(buf));
		if (len < 0) break;//�жϿͻ����Ƿ�����

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
	sprintf_s(buf, "����myRecvThread�߳�:%d..\n", pcinfo->threadId);
	printMsg(buf);
}

void procLoginMsg(struct ClientInfo * pcinfo, struct LoginMsg *msg)
{
	strcpy_s(pcinfo->qq, msg->qq);  //����qq��������������

	//���͵�¼�ɹ���Ϣ
	struct LoginMsgResponse res;
	res.id = LOGIN_MSG_RES;
	res.isOK = 1;

	int i = 0;
	int m = 0;
	//����QQ���Ƿ��Ѿ���½
	for (i = 0; i<10; i++)
	{
		if (cinfo[i].isFree == 0) continue;

		if (strcmp(cinfo[i].qq, msg->qq) == 0)
		{
			//���Ҹ�QQ�ų��ִ���
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
	res.isOK = 0;  //û���ҵ�

	for (i = 0; i<sizeof(cinfo) / sizeof(struct ClientInfo); i++)
	{
		if (cinfo[i].isFree == 0) continue;
		if (strcmp(cinfo[i].qq, msg->qq) == 0)
		{
			res.isOK = 1; //��������
		}
	}

	//���ͺ���QQ���ҽ����Ϣ
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
			//��������Ϣת��������
			tcpSend(cinfo[i].sock, (const char *)pmsg, sizeof(struct TalkMsg));
			return;
		}
	}

	//���ѿ��������ˣ�����Ĵ�����������
	//����������������Ŀͻ��˷��ͺ��Ѳ����ߵ���Ϣ
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

/** �ڿͻ�����Ϣ��¼�����ҿ��е�Ԫ���±� **/
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