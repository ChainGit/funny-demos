#include "TalkClientService.h"

char qq[6] = { 0 };
char fqq[6] = { 0 };
int sock;
char talk_buf[15][1024] = { 0 };
int talk_buf_num = 0;
int isChanged = 1;

int status = STUS_START;

void mainProc()
{
	system("cls");
	while (1)
	{
		switch (status)
		{
		case STUS_CONNECTED:
			statusConnected();
			break;

		case STUS_WAIT_LOGIN_RES:
			printMsg("等待服务器响应..\n");
			Sleep(1000);
			break;

		case STUS_LOGINED:
			statusLogined();
			break;

		case STUS_WAIT_QUERY_FQQ_RES:
			printMsg("等待服务器响应..\n");
			Sleep(1000);
			break;

		case STUS_TALK:
			statusTalk();
			break;
		}
	}
}

void statusTalk()
{
	clock_t t0, dt;
	int t_flag;
	int x;

	t0 = clock();
	t_flag = 1;

	//printf("Me: ");
	fflush(stdin);
	

	char buf[1024] = { 0 };
	char in_buf[1024] = { 0 };

	while (!_kbhit()){
		dt = clock() - t0;
		if (dt >= CLOCKS_PER_SEC/2){
			t_flag = 0;
			show_msg();
			return;
		}
	}

	scanf("%s", buf);
	//gets(buf);

	if (strcmp(buf, "Exit") == 0)
	{
		//退出聊天，返回主界面
		statusLogined();
	}
	else
	{
		struct TalkMsg msg;

		msg.id = TALK_MSG;
		strcpy(msg.qq, qq);
		strcpy(msg.fqq, fqq);
		strcpy(msg.info, buf);
		status = STUS_TALK;
		tcpSend(sock, (const char *)&msg, sizeof(struct TalkMsg));
		
		sprintf(in_buf, "%s: %s",msg.qq, buf);
		proc_msg(in_buf);
		//show_msg();
	}

	return;
}

void statusLogined()
{
	system("cls");
	setbuf(stdin, NULL);   //清空键盘缓冲区
	printf("【%s】你好,找好友聊天(T)|退出(E)\n\n> ",qq);
	int c = -1;
	c=getchar();
	printf("\n");
	if (c == 'T' || c == 't')
	{
		show_online();
		Sleep(1000);
		while (1)
		{
			memset(talk_buf, 0, sizeof(talk_buf));
			talk_buf_num = 0;
			printf(">输入聊天好友QQ号(返回E)：");

			char tmp[1024];
			scanf("%s", tmp);
			if (strlen(tmp)>5)
			{
				printMsg("您输入的qq号长度大于5个，请重新输入\n");
			}
			else if (strcmp(tmp, "E") == 0||strcmp(tmp, "e") == 0){
				statusLogined();
			}
			else if (strcmp(tmp, qq) == 0)
			{
				printMsg("您输入的qq号是自己的QQ号，请重新输入\n");
			}
			else
			{//
				struct FriendQqMsg msg;
				msg.id = QUERY_FQQ;
				strcpy(msg.qq, tmp);
				strcpy(fqq, tmp);
				status = STUS_WAIT_QUERY_FQQ_RES;
				tcpSend(sock, (const char *)&msg, sizeof(msg));
				system("cls");
				break;
			}
		}
	}
	else if (c == 'E' || c == 'e')
	{
		HWND hwnd = FindWindow(L"ConsoleWindowClass", NULL);
		MessageBox(hwnd, L"软件即将关闭!", L"客户端", 0);
		printMsg("客户端已经退出！\n");
		exit(0);
	}
	else
	{
		statusLogined();
	}
}

void connectServer()
{
	//printMsg("开始连接服务器......\n");
	initSock(IS_CLIENT);  //客户端
	sock = tcpConnect(IP, SER_PORT);
	if (sock > -1) {
		//system("cls");
		printf("\t\t\t\t  连接服务器成功\n");
	}
	else{
		return;
	}

	//创建一个线程用于接收服务器发送的信息
	DWORD threadId;
	CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)myRecvThread, NULL, 0, &threadId);
}

//创建接收线程专用于接收并处理服务器返回的收数据
void myRecvThread()
{
	char buf[1024] = { 0 };
	while (1)
	{
		memset(buf, 0, sizeof(buf));
		tcpRecv(sock, buf, sizeof(buf));
		unsigned char id = (unsigned char)buf[0];
		switch (id)
		{
		case LOGIN_MSG_RES:
			procLoginMsgResponse((struct LoginMsgResponse *)buf);
			break;

		case QUERY_FQQ_RES:
			procFriendQqMsgResponse((struct FriendQqMsgResponse *)buf);
			break;

		case TALK_MSG:
			procTalkMsg((struct TalkMsg *)buf);
			break;
		case SHOW_ONLINE_RES:
			proc_show_online((struct OnlineF *)buf);
			break;
		}
	}
	sprintf(buf, "结束myRecvThread线程:%d..\n");
	printMsg(buf);
	exit(0);
}

void statusConnected()
{
	while (1)
	{
		system("cls");
		printf("请输入登录信息(最多不超过5位):\n");
		printf("\n> 登陆的QQ号: ");
		setbuf(stdin, NULL);
		char tmp[10];
		scanf("%s", tmp);
		if (strlen(tmp)>5)
		{
			printf("您输入的qq号长度大于5个,请重新输入!\n");
		}
		else
		{
			strcpy(qq, tmp);
			break;
		}
	}

	struct LoginMsg msg;
	msg.id = LOGIN_MSG;
	status = STUS_WAIT_LOGIN_RES;
	strcpy(msg.qq, qq);
	tcpSend(sock, (const char *)&msg, sizeof(msg));
	mainProc(); //进入主处理程序
	return;
}

void procLoginMsgResponse(struct LoginMsgResponse *msg)
{
	if (msg->isOK == 1)
	{
		printMsg("登录成功!\n");
		status = STUS_LOGINED;//已登录状态
	}
	else
	{
		printMsg("登录失败,该QQ已经登陆!\n");
		HWND hwnd = FindWindow(L"ConsoleWindowClass", NULL);
		MessageBox(hwnd, L"登录失败,该QQ已经登陆!", L"客户端", 0);
		printMsgExit(msg->reason);
		//printf("\n");
	}
}

void procFriendQqMsgResponse(struct FriendQqMsgResponse *msg)
{
	if (msg->isOK == 1)
	{
		printMsg("好友目前在线，可以开始聊天..\n");
		status = STUS_TALK;
		Sleep(1000);
	}
	else
	{
		printMsg("好友不存在啊，再换个人试试!\n");
		status = STUS_LOGINED;
		Sleep(2000);
	}
}

void procTalkMsg(struct TalkMsg *msg)
{
	char buf[1024] = { 0 };

	if (strcmp(msg->qq, fqq) == 0)
	{//表示是自己发起的聊天，仅仅显示信息即可
		//printf("\n");
		sprintf(buf, "%s: %s", fqq, msg->info);
		//printf(buf);
	}
	else
	{//是陌生人发起的聊天
		//printf("\n");
		sprintf(buf, "%s: %s", msg->qq, msg->info);
		//printf(buf);
	}

	
	proc_msg(buf);
}

void proc_msg(char *buf){
	int i = 0;
	if (talk_buf_num < 15){
		strcpy(talk_buf[talk_buf_num], buf);
		talk_buf_num++;
	}
	else{
		memset(talk_buf[0], 0, 1024);
		for (i = 0; i < 14; i++){
			strcpy(talk_buf[i], talk_buf[i+1]);
			memset(talk_buf[i+1], 0, 1024);
		}
		strcpy(talk_buf[14], buf);
	}

	isChanged = 1;
}

void show_msg(){

	if (isChanged == 0) return;

	int i = 0;
	system("cls");
	gotoxy(0, 0);
	printf("[退出聊天输入Exit][聊天先回车]\n\n");

	for (i = 0; i < 15; i++){
		printf("%s\n",talk_buf[i]);
	}


	gotoxy(0, 22);
	printf("%s> ",qq);

	isChanged = 0;
	//setbuf(stdin, NULL);
}

void show_online(){
	struct OnlineF onf;
	onf.id = SHOW_ONLINE;
	tcpSend(sock, (const char *)&onf, sizeof(struct OnlineF));
	return;
}

void proc_show_online(struct OnlineF *buf){
	printf("\n当前在线好友为:\n");

	int i = 0;
	int mm = 0;
	mm = buf->onf_num;
	for (i = 0; i < mm; i++){
		printf("%d) %s\n",i+1,buf->onf[i]);
	}

	printf("\n");
}