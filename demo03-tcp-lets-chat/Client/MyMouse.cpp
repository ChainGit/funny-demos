#include "MyMouse.h"

HANDLE handle_in;
HANDLE handle_out;
CONSOLE_SCREEN_BUFFER_INFO csbi;        //���崰�ڻ�������Ϣ�ṹ��

void DisplayMousePosition(COORD pos)
{
	COORD dis = { 0, 24 };        //�ڵ�24����ʾ���λ��
	WORD att = FOREGROUND_GREEN | FOREGROUND_INTENSITY; //�ı�����
	GetConsoleScreenBufferInfo(handle_out, &csbi);  //��ô��ڻ�������Ϣ
	printf("X = %3d, Y = %3d", (int)pos.X, (int)pos.Y);
	FillConsoleOutputAttribute(handle_out, att, 16, dis, NULL);  //����ı�����
	return;
}

void gotoxy(int x, int y)
{
	COORD pos = { x, y };
	SetConsoleCursorPosition(handle_out, pos);
}

void hideCursor()//���ؿ���̨�Ĺ�� 
{
	CONSOLE_CURSOR_INFO cursor_info = { 1, 0 };
	SetConsoleCursorInfo(GetStdHandle(STD_OUTPUT_HANDLE), &cursor_info);
}

void showCursor()//��ʾ����̨�Ĺ�� 
{
	CONSOLE_CURSOR_INFO cursor_info = { 0, 1 };
	SetConsoleCursorInfo(GetStdHandle(STD_OUTPUT_HANDLE), &cursor_info);
}

int mouse_click(){
	handle_in = GetStdHandle(STD_INPUT_HANDLE);      //��ñ�׼�����豸���
	handle_out = GetStdHandle(STD_OUTPUT_HANDLE);    //��ñ�׼����豸���
	INPUT_RECORD mouserec;      //���������¼��ṹ��
	DWORD res;      //���ڴ洢��ȡ��¼
	COORD pos;      //���ڴ洢��굱ǰλ��
	COORD size = { 80, 25 };  //���ڻ�������С
	GetConsoleScreenBufferInfo(handle_out, &csbi);  //��ô��ڻ�������Ϣ
	SetConsoleScreenBufferSize(handle_out, size);   //���ô��ڻ�������С
	for (;;)
	{
		ReadConsoleInput(handle_in, &mouserec, 1, &res);      //��ȡ�����¼�
		pos = mouserec.Event.MouseEvent.dwMousePosition;    //��õ�ǰ���λ��
		gotoxy(0, 24);  //�ڵ�25����ʾ���λ��
		DisplayMousePosition(pos);      //��ʾ���λ��
		if (mouserec.EventType == MOUSE_EVENT)    //�����ǰΪ����¼�
		{
			gotoxy(pos.X, pos.Y);
			//����������
			if (mouserec.Event.MouseEvent.dwButtonState == FROM_LEFT_1ST_BUTTON_PRESSED)
			{
				check_click(pos);
				continue;
			}
			//��������Ҽ�
			if (mouserec.Event.MouseEvent.dwButtonState == RIGHTMOST_BUTTON_PRESSED)
			{
				/*break;*/
				continue;
			}
			//˫���˳�
			if (mouserec.Event.MouseEvent.dwEventFlags == DOUBLE_CLICK)
			{
				/*break;*/
				continue;
			}
		}
	}
	CloseHandle(handle_out);
	CloseHandle(handle_in);
	return 0;
}