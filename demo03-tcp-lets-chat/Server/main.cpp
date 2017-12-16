#include "main.h"

int main(int agrc, char *argv[])
{
	displayMenu();
	procMenu();
	while (1){
		Sleep(10000);
	}
	tcpClose(ls);
}

/*  系统菜单  */
void displayMenu()
{
	printf("\t\t************************************************\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*           课设模仿QQ聊天服务端程序           *\n");
	printf("\t\t*                    钱帮全                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                1) 启动 (s)                   *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                2) 退出(e)                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t************************************************\n");
	printf("\n");
}

void procMenu()
{
	char ch=0;     //菜单选择
	setbuf(stdin, NULL);
	printf("\t\t您的选择是:");
	scanf("%c", &ch);
	switch (ch)
	{
	case 's':
	case 'S':
	case '1':
		startServer();
		break;

	case 'e':
	case 'E':
	case '2':
		exitProc();
		break;

	default:{
		defProc();
		break;
	}
	}
}

void exitProc()
{
	printf("\t\t系统已经退出!\n");
	exit(0);
}

void defProc()
{
	printf("\t\t输入字符错误!\n");
}
