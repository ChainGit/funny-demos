#ifndef _MYINCLUDE_H_
#define _MYINCLUDE_H_

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define  _CRT_SECURE_NO_WARNINGS

#define IP "127.0.0.1"
//#define IP "192.168.43.114"
#define CLI_PORT 0
#define SER_PORT 5656

#define IS_CLIENT 0
#define IS_SERVER 1

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#include <windows.h>
#include <conio.h>
#include <time.h>

#include "MyMsg.h"
#include "MyTools.h"
#include "MyMouse.h"
#include "MyTcp.h"
#include "TalkClientService.h"

#pragma comment (lib, "ws2_32.lib")

#endif