#ifndef _MYMSG_H_
#define _MYMSG_H_
#include "MyInclude.h"

enum
{
	LOGIN_MSG,
	LOGIN_MSG_RES,
	QUERY_FQQ,
	QUERY_FQQ_RES,
	TALK_MSG,
	SHOW_ONLINE,
	SHOW_ONLINE_RES
};

struct LoginMsg
{
	unsigned char id;
	char qq[6];
};

struct LoginMsgResponse
{
	unsigned char id;
	unsigned char isOK;
	char reason[100];
};

struct FriendQqMsg
{
	unsigned char id;
	char qq[6];
};

struct FriendQqMsgResponse
{
	unsigned char id;
	unsigned char isOK;
};

struct TalkMsg
{
	unsigned char id;
	char qq[6];
	char fqq[6];
	char info[200];
};

struct OnlineF{
	unsigned char id;
	char onf_num;
	char onf[10][6];
};

#endif
