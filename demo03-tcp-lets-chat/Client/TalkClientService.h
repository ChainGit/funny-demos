#ifndef _TALKCLIENTSERVICE_H_
#define _TALKCLIENTSERVICE_H_
#include "MyInclude.h"

extern char qq[6];
extern char fqq[6];
extern int sock;
extern char talk_buf[15][1024];
extern int talk_buf_num;
extern int isChanged;

extern int status;

void connectServer();
void procLoginMsgResponse(struct LoginMsgResponse *msg);
void procFriendQqMsgResponse(struct FriendQqMsgResponse *msg);
void procTalkMsg(struct TalkMsg *msg);
void statusConnected();
void statusLogined();
void statusTalk();
void myRecvThread();
void show_msg();
void proc_msg(char *buf);
void show_online();
void proc_show_online(struct OnlineF *buf);

/** ¿Í»§¶Ë×´Ì¬ **/
enum {
	STUS_START,
	STUS_CONNECTED,
	STUS_WAIT_LOGIN_RES,
	STUS_LOGINED,
	STUS_WAIT_QUERY_FQQ_RES,
	STUS_TALK
};

#endif
