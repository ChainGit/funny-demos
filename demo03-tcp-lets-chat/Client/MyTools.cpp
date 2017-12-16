#include "MyTools.h"

void printMsg(const char * msg) {
	if (flag)
		printf("服务器> %s", msg);
	else
		printf("客户端> %s", msg);
}

void printInt(int value) {
	if (flag)
		printf("服务器> %d", value);
	else
		printf("客户端> %d", value);
}

void printMsgExit(const char * msg) {
	printMsg(msg);
	exit(0);
}

void printIntExit(int value) {
	printInt(value);
	exit(0);
}