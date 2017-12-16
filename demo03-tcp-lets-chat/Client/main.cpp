#include "main.h"

int main(int agrc, char *argv[])
{
	displayMenu();
	while (1){
		Sleep(10000);
	}
}

void displayMenu()
{
	connectServer();
	hideCursor();
	printf("\t\t************************************************\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*           课设模仿QQ聊天客户端程序           *\n");
	printf("\t\t*                    钱帮全                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                   1) 登录                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                   2) 退出                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t************************************************\n");
	printf("\n");
	mouse_click();
}

void exitProc()
{
	HWND hwnd = FindWindow(L"ConsoleWindowClass", NULL);
	MessageBox(hwnd, L"软件即将关闭!", L"客户端", 0);
	printMsg("\t\t客户端程序已经终止!\n");
	exit(0);
}

void check_click(COORD pos){
	HWND hwnd = FindWindow(L"ConsoleWindowClass", NULL);/*  控制台窗口句柄 */
	int x = (int)pos.X;
	int y = (int)pos.Y;

	if (x > 30 && x < 42 && y == 8){
		showCursor();
		//system("cls");
		statusConnected();
	}
	else if (x > 30 && x < 42 && y == 10){
		exitProc();
	}
}