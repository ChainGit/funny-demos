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
	printf("\t\t*           ����ģ��QQ����ͻ��˳���           *\n");
	printf("\t\t*                    Ǯ��ȫ                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                   1) ��¼                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                   2) �˳�                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t************************************************\n");
	printf("\n");
	mouse_click();
}

void exitProc()
{
	HWND hwnd = FindWindow(L"ConsoleWindowClass", NULL);
	MessageBox(hwnd, L"��������ر�!", L"�ͻ���", 0);
	printMsg("\t\t�ͻ��˳����Ѿ���ֹ!\n");
	exit(0);
}

void check_click(COORD pos){
	HWND hwnd = FindWindow(L"ConsoleWindowClass", NULL);/*  ����̨���ھ�� */
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