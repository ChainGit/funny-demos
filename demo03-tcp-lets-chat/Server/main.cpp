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

/*  ϵͳ�˵�  */
void displayMenu()
{
	printf("\t\t************************************************\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*           ����ģ��QQ�������˳���           *\n");
	printf("\t\t*                    Ǯ��ȫ                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                1) ���� (s)                   *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t*                2) �˳�(e)                    *\n");
	printf("\t\t*                                              *\n");
	printf("\t\t************************************************\n");
	printf("\n");
}

void procMenu()
{
	char ch=0;     //�˵�ѡ��
	setbuf(stdin, NULL);
	printf("\t\t����ѡ����:");
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
	printf("\t\tϵͳ�Ѿ��˳�!\n");
	exit(0);
}

void defProc()
{
	printf("\t\t�����ַ�����!\n");
}
