#ifndef _MYMOUSE_H_
#define _MYMOUSE_H_
#include "MyInclude.h"

extern HANDLE handle_in;
extern HANDLE handle_out;
extern CONSOLE_SCREEN_BUFFER_INFO csbi;        //���崰�ڻ�������Ϣ�ṹ��

void DisplayMousePosition(COORD pos);   //��ʾ�������λ��
void gotoxy(int x, int y);  //������Ƶ�����Ϊ(x,y)��λ��
void hideCursor();
void showCursor();
int mouse_click();
extern void check_click(COORD pos);

#endif