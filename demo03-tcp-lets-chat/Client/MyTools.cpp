#include "MyTools.h"

void printMsg(const char * msg) {
	if (flag)
		printf("������> %s", msg);
	else
		printf("�ͻ���> %s", msg);
}

void printInt(int value) {
	if (flag)
		printf("������> %d", value);
	else
		printf("�ͻ���> %d", value);
}

void printMsgExit(const char * msg) {
	printMsg(msg);
	exit(0);
}

void printIntExit(int value) {
	printInt(value);
	exit(0);
}