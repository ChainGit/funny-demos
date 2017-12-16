#ifndef _MYMOUSE_H_
#define _MYMOUSE_H_
#include "MyInclude.h"

extern HANDLE handle_in;
extern HANDLE handle_out;
extern CONSOLE_SCREEN_BUFFER_INFO csbi;        //定义窗口缓冲区信息结构体

void DisplayMousePosition(COORD pos);   //显示鼠标所在位置
void gotoxy(int x, int y);  //将光标移到坐标为(x,y)的位置
void hideCursor();
void showCursor();
int mouse_click();
extern void check_click(COORD pos);

#endif