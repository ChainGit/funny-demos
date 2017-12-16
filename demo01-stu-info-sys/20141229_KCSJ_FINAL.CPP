#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

//【预处理】
#define T1      "-----------------------学生信息管理系统----------------------\n\n"
#define T2      "-------------------------[by Chain]-------------------------\n\n"
#define CHOOSE  ">请输入你想进行的操作序号(0-%d):"
#define EXIT    "\n>您确定要退出系统么?退出前请注意保存噢。\n (先进行保存并退出请按S,确认直接退出请按Y，取消并返回菜单请按N):"
#define TABHEAD "|--------------------------------------------------------------|\n|   编号  学号    姓名    年龄  |该生成绩相关| 语文  数学  英语|\n"
#define TABLINE "|--------------------------------------------------------------|\n"

//【主要处理函数】
void s1_rec();     //主菜单_学生信息 录入 修改 删除
void s2_rec();     //主菜单_学生信息 浏览 查询 排序
void save_rec();   //主菜单_学生信息保存
void exit_rec();   //主菜单_退出系统

void input_rec();  //子菜单_学生信息 录入
void add_rec();    //子菜单_学生信息 增加
void change_rec(); //子菜单_学生信息 修改
void delete_rec();//子菜单_学生信息 删除

void surf_rec(); //子菜单_学生信息 浏览
void search_rec(); //子菜单_学生信息 查询
void arrange_rec(); //子菜单_学生信息 排序

//【子函数】
void add_s();//学生信息 录入 单个
void surf_s();//学生信息 浏览 单个

//【其他函数】
void sys_load();//启动系统选项
void print_main_menu();//主菜单显示
void example_rec();//系统数组示范
void file_load();//文件载入            
void file_save();//文件保存
void sys_rec();//初始化使用                      

//【数据结构定义】
//学号，姓名，年龄，成绩
//成绩可包括课程的基本信息：课程编号、课程名称、考试成绩、平时成绩、综合成绩、学分、重修否等
typedef struct _score
{int math,english,chinese;}SCORE;
typedef struct _stu
{
	int num;
	char name[10];
	int age;
    SCORE score;
}STU;
STU stu[40];

//【相关全局变量】
char sel=1;
int total=0;
int id=0;
int tabhead=1;
int i=0;
FILE *fp;

//*************************************************系统界面********************************************************************************
//【main函数】
void main()
{
	sys_load();
}

void sys_load()
{
	while(1)
	{
	     system("cls");sel=1;
	     printf(T1);
	     printf("\n\n                    1.正常启动系统\n\n                    2.初始化启动系统(第一次使用)\n\n\n                    0.退出系统\n\n");
	     printf(T2);

         printf(CHOOSE,1);
         sel=getchar();
		 switch(sel)
		 {
	       case '1':
			   {
				   	file_load();
					example_rec();
					printf("\n\n>OK,");
					system("pause");
					print_main_menu();
					break;
			   }
		   case '2':sys_rec();break;
		   case '0':exit(0);break;
		 }
	}
    return;
}


void print_main_menu()
{
	while(1)
	{
	   system("cls");sel=1;
	   printf(T1);
	   printf("1.学生信息 录入 增加 修改 删除 \n");
	   printf("2.学生信息 浏览 查询 排序 \n");
       printf("3.学生信息 保存\n");
       printf("0.退出系统 \n\n");
   	   printf(T2);

	   printf(CHOOSE,3);
	   sel=getchar();
       switch(sel)
	   {
	       case '1':s1_rec();break;
		   case '2':s2_rec();break;
		   case '3':file_save();break;
		   case '0':exit_rec();break;
		   default: continue;
	   }
	}
	return;
}

void s1_rec()
{
	while(1)
	{
	   system("cls");sel=1;
       printf(T1);
	   printf("*学生信息 录入 增加 修改 删除: \n\n");
       printf("  1.学生信息 录入  \n");
	   printf("  2.学生信息 增加  \n");
       printf("  3.学生信息 修改 \n");
       printf("  4.学生信息 删除 \n\n");
	   printf("  0.返回主菜单\n\n");
	   printf(T2);

       printf(CHOOSE,4);
	   sel=getchar();
       switch(sel)
	   {
	       case '1':input_rec();break;
		   case '2':add_rec();break;
           case '3':change_rec();break;
           case '4':delete_rec();break;
		   case '0':print_main_menu();break;
		   default: continue;
	   }
	}
	return;
}

void s2_rec()
{
	while(1)
	{
	   system("cls");sel=1;
       printf(T1);
       printf("*学生信息 浏览 查询 排序: \n\n");
       printf("  1.学生信息 浏览\n");
       printf("  2.学生信息 查询\n");
       printf("  3.学生信息 排序\n\n");
	   printf("  0.返回主菜单\n\n");
	   printf(T2);

       printf(CHOOSE,3);
       sel=getchar();
       switch(sel)
	   {
	       case '1':surf_rec();break;
		   case '2':search_rec();break;
           case '3':arrange_rec();break;
		   case '0':print_main_menu();break;
		   default: continue;
	   }
	}
	return;
}

//*===============================================主要实现函数====================================================================*
void input_rec()
{
	if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息，确定要初始化并重新输入吗?\n   (确定请按Y，取消并返回请按N，进行增加工作请按A):",total);
ask1:   sel=getchar();
		if(sel=='Y') goto inputfun;
		else if(sel=='N') print_main_menu();
		else if(sel=='A') add_rec();
		else goto ask1;
	}

inputfun:	
	sel=1;id=1;
	printf("\n>开始进行信息录入:\n");total=0;
	while(sel!='N')
	{
	   printf(" \n请输入编号为%2d的同学的信息：\n\n",total+1);
	   total++;
	   add_s();
	   sel=1;
	   printf("\n>请问是否继续进行输入下一个(Y/N):"); 
	   while(1)
	   {
	      sel=1;
	      sel=getchar();
		  if(sel=='Y'||sel=='N') break;
       }
	}
    printf("\n>输入结束，您总共输入了%d个数据.\n\n",total);
	system("pause");
    print_main_menu();
	return;	
}


void add_rec()
{
	if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息\n确认增加请按Y，取消并返回请按N):",total);
ask2:   sel=getchar();
		if(sel=='Y') goto addfun;
		else if(sel=='N') print_main_menu();
		else goto ask2;
	}

addfun:
	sel=1;id=1;
	printf("\n>开始进行信息增加工作:\n\n");
	while(sel!='N')
	{
       printf("当前已有%d个学生的信息\n",total);
       printf(" \n请输入要增加的编号为%2d的同学的信息：\n\n",total+1);
	   add_s();
       total++; 
	   printf("\n>请问是否继续进行增加下一个(Y/N):"); 
	   while(1)
	   {
	      sel=1;
	      sel=getchar();
		  if(sel=='Y'||sel=='N') break;
       }
	}

	printf("\n>输入结束，系统数据库目前共有%d个数据.\n\n",total);
	system("pause");
    print_main_menu();
	return;	
}

void add_s()
{
	    printf("   请输入学号：");scanf("%d",&stu[total].num);
		printf("   请输入姓名：");scanf("%s",&stu[total].name);
		printf("   请输入年龄：");scanf("%d",&stu[total].age);
		printf("   >下面请输入该生成绩相关：\n");
		printf("        请按顺序依次输入 语文 数学 英语 成绩(空格隔开):");scanf("%d%d%d",&stu[total].score.chinese,&stu[total].score.math,&stu[total].score.english);
}

void change_rec()
{
    if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息\n确认开始修改请按Y，取消并返回请按N:",total);
ask3:   sel=getchar();
		if(sel=='Y') goto changefun;
		else if(sel=='N') print_main_menu();
		else goto ask3;
	}
	else 
		if(total==0)
		{
		     printf("\n您还未添加任何数据!\n\n");
             system("pause");
             print_main_menu();
		}
	


changefun:
	tabhead=1;sel=1;id=1;
	printf("\n>开始进行信息修改工作:\n\n");sel=1;
    printf("请输入您要修改的学生编号：");
	id=0;
	scanf("%d",&id);
    printf("\n该学生信息:\n");
	surf_s();  
	    printf("\n请重新输入该学生信息:\n");
        printf("   请输入学号：");scanf("%d",&stu[id].num);
		printf("   请输入姓名：");scanf("%s",&stu[id].name);
		printf("   请输入年龄：");scanf("%d",&stu[id].age);
		printf("   >下面请输入该生成绩相关：\n");
		printf("        请按顺序依次输入 语文 数学 英语 成绩(空格隔开):");
		scanf("%d%d%d",&stu[id].score.chinese,&stu[id].score.math,&stu[id].score.english);

    printf(">>>信息修改完成，如下:\n");
    surf_s();
    system("pause");
	return;
}


void delete_rec()
{
	if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息\n确认开始删除请按Y，取消并返回请按N:",total);
ask4:   sel=getchar();
		if(sel=='Y') goto deletefun;
		else if(sel=='N') print_main_menu();
		else goto ask4;
	}
	else 
		if(total==0)
		{
		     printf("\n您还未添加任何数据!\n\n");
             system("pause");
             print_main_menu();
		}


deletefun:
	tabhead=1;sel=1;id=1;
	printf("\n>开始进行信息删除工作:\n\n");sel=1;
	printf("请输入您要删除的学生编号：");
	id=0;
	scanf("%d",&id);
    printf("\n该学生信息:\n");
	surf_s();  
    
	printf("请确认信息无误，按Y删除，N返回主菜单:");
	sel=1;
	scanf("%s",&sel);
	if(sel=='N') print_main_menu();
	if(sel=='Y') 
	{
		for(int i=id;i<total;i++)
		{
           stu[i].num=stu[i+1].num;
		   strcpy(stu[i].name,stu[i+1].name);
		   stu[i].age=stu[i+1].age;
		   stu[i].score.chinese=stu[i+1].score.chinese;
		   stu[i].score.math=stu[i+1].score.math;
		   stu[i].score.english=stu[i+1].score.english;
		   
		}
		total--;
		printf("\n>>>删除编号为%d的工作已完成.\n",id);
        printf("\n当前数据库中剩有%d个学生信息.\n",total);
	}
    printf("按任意键返回菜单...\n\n");
    system("pause");
    print_main_menu();
	return;
}


void surf_rec()
{
	if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息\n开始浏览操作请按Y，取消并返回请按N:",total);
ask5:   sel=getchar();
		if(sel=='Y') goto surffun;
		else if(sel=='N') print_main_menu();
		else goto ask5;
	}
	else 
		if(total==0)
		{
		     printf("\n您还未添加任何数据!\n\n");
             system("pause");
             print_main_menu();
		}


surffun:
    tabhead=1;sel=1;id=1;
    printf("\n>开始进行信息浏览工作:\n\n");sel=1;
	printf("所有的学生信息情况如下:\n");
	for(id=1;id<=total;id++)
	{
     surf_s();
	 tabhead=0;
	}
    printf("\n所有的学生信息已完全输出，共%d条数据...\n\n",total);
    printf("按任意键返回菜单...\n\n");
    system("pause");
    print_main_menu();
	return;
}

void surf_s()
{ 
    if(tabhead==1) printf(TABHEAD);
	printf(TABLINE);
    printf("|   %-5d %-5d   %-5s    %-2d                  %-3d   %-3d   %-3d |\n",
		id,stu[id].num,stu[id].name,stu[id].age,
		stu[id].score.chinese,stu[id].score.math,stu[id].score.english);
    printf(TABLINE);
	return;
}

void search_rec()
{
	if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息\n开始查询操作请按Y，取消并返回请按N:",total);
ask6:   sel=getchar();
		if(sel=='Y') goto searchfun;
		else if(sel=='N') print_main_menu();
		else goto ask6;
	}
	else 
		if(total==0)
		{
		     printf("\n您还未添加任何数据!\n\n");
             system("pause");
             print_main_menu();
		}


searchfun:
	sel=1;id=1;
	printf("\n>开始进行信息查询工作:\n\n");sel=1;
    printf("请输入您想查询的信息\n  (1.学号 2.姓名 3.年龄 \nc.语文成绩 m.数学成绩 e.英语成绩):");
input1:
    scanf("%s",&sel);
	int in1;
       switch(sel)
	   {
	       case '1':
			   {
				   printf("\n请输入查询的学号:");
                   scanf("%d",&id);
				   break;
			   }
		   case '2':
			   {
				   printf("\n请输入查询的姓名:");
                   char name1[10];
				   scanf("%s",name1);
				   for(int i=1;i<=total;i++)
				   if(strcmp(stu[i].name,name1)==0) {id=i;break;}
				   break;
			   }
           case '3':
			   {
				   printf("\n请输入查询的年龄:");
                   scanf("%d",&in1);
				   for(int i=1;i<=total;i++)
					   if(stu[i].age==in1) {id=i;break;}
				   break;
			   }
           case 'c':
			   {
				   printf("\n请输入查询的语文成绩:");
                   scanf("%d",&in1);
				   for(int i=1;i<=total;i++)
					   if(stu[i].score.chinese==in1) {id=i;break;}
				   break;
			   }
		   case 'm':
			   {
				   printf("\n请输入查询的数学成绩:");
                   scanf("%d",&in1);
				   for(int i=1;i<=total;i++)
					   if(stu[i].score.math==in1) {id=i;break;}
				   break;
			   }
           case 'e':
			   {
				   printf("\n请输入查询的英语成绩:");
                   scanf("%d",&in1);
				   for(int i=1;i<=total;i++)
					   if(stu[i].score.english==in1) {id=i;break;}
				   break;
			   }
		   default: goto input1;
	   }
	   	printf("该学生的信息如下：\n");
        surf_s();
        printf("\n按任意键返回到主菜单...\n");
		system("pause");
        print_main_menu();

	return;
}


void arrange_rec()
{
	if(total!=0)
	{
		printf("\n当前数据库中已有%d个学生信息\n开始排序操作请按Y，取消并返回请按N:",total);
ask7:   sel=getchar();
		if(sel=='Y') goto arrangefun;
		else if(sel=='N') print_main_menu();
		else goto ask7;
	}
	else 
		if(total==0)
		{
		     printf("\n您还未添加任何数据!\n\n");
             system("pause");
             print_main_menu();
		}


arrangefun:
    int t1,t2,t3;char name1[10];

    struct _score2
	{int math,english,chinese;};
    struct _stu2
	{
	   int num;
	   char name[10];
	   int age;
       struct _score2 score;
	}arr[40];

	sel=1;id=1;tabhead=1;int j=0;
	printf("\n>开始进行信息排序(降序)工作:\n\n");
	printf("请输入您想排序的信息\n  (c.语文成绩 m.数学成绩 e.英语成绩):");
	for(i=0;i<=total;i++)
	   {
		   arr[i].num=stu[i].num;
		   strcpy(arr[i].name,stu[i].name);
           arr[i].age=stu[i].age;
		   arr[i].score.chinese=stu[i].score.chinese;
           arr[i].score.english=stu[i].score.english;
		   arr[i].score.math=stu[i].score.math;
	   }
input2:
    scanf("%s",&sel);
       switch(sel)
	   {
	        case 'c':
			   {
				   printf("\n按语文成绩排序如下:\n");
                  for(j=0;j<total;j++)
		          for(i=0;i<total-j;i++)
			        if(arr[i].score.chinese<arr[i+1].score.chinese)
					{
				       strcpy(name1,arr[i].name);
					   strcpy(arr[i].name,arr[i+1].name);
				       strcpy(arr[i+1].name,name1);

					   t1=arr[i].score.chinese;
				       arr[i].score.chinese=arr[i+1].score.chinese;
				       arr[i+1].score.chinese=t1;

				       t2=arr[i].score.english;
				       arr[i].score.english=arr[i+1].score.english;
				       arr[i+1].score.english=t2;

				       t3=arr[i].score.math;
				       arr[i].score.math=arr[i+1].score.math;
				       arr[i+1].score.math=t3;

					}

			  break;
			   }
		   case 'm':
			   {
			   printf("\n按数学成绩排序如下:\n");
			   for(j=0;j<total;j++)
		          for(i=0;i<total-j;i++)
			         if(arr[i].score.math<arr[i+1].score.math)
					{
				       strcpy(name1,arr[i].name);
					   strcpy(arr[i].name,arr[i+1].name);
				       strcpy(arr[i+1].name,name1);

					   t1=arr[i].score.chinese;
				       arr[i].score.chinese=arr[i+1].score.chinese;
				       arr[i+1].score.chinese=t1;

				       t2=arr[i].score.english;
				       arr[i].score.english=arr[i+1].score.english;
				       arr[i+1].score.english=t2;

				       t3=arr[i].score.math;
				       arr[i].score.math=arr[i+1].score.math;
				       arr[i+1].score.math=t3;
					 }
			   break;
			   }
           case 'e':
			   {
			   printf("\n按英语成绩排序如下:\n");
                 for(j=0;j<total;j++)
		          for(i=0;i<total-j;i++)
			         if(arr[i].score.english<arr[i+1].score.english)
					{
				       strcpy(name1,arr[i].name);
					   strcpy(arr[i].name,arr[i+1].name);
				       strcpy(arr[i+1].name,name1);

					   t1=arr[i].score.chinese;
				       arr[i].score.chinese=arr[i+1].score.chinese;
				       arr[i+1].score.chinese=t1;

				       t2=arr[i].score.english;
				       arr[i].score.english=arr[i+1].score.english;
				       arr[i+1].score.english=t2;

				       t3=arr[i].score.math;
				       arr[i].score.math=arr[i+1].score.math;
				       arr[i+1].score.math=t3;
					 }
			   break;
			   }
		   default: goto input2;
	   }


		printf("|   编号    姓名    语文  数学  英语 |\n");
		for(i=0;i<=total-1;i++)
            printf("|   %-5d   %-5s    %-3d   %-3d   %-3d |\n",
	     	i+1,arr[i].name,arr[i].score.chinese,arr[i].score.math,arr[i].score.english);
		putchar(10);
		printf(">排序完成!\n");
		system("pause");

	return;
}

//-----------------------------------------其他处理函数----------------------------------------------------------------
void exit_rec()
{
    printf(EXIT);
	while(1)
	{
	    sel=1;
		sel=getchar();
        switch(sel)
		{
	       case 'Y':{printf("|-------------------------|\n|       Thank you!!       |\n|-------------------------|\n\n");exit(0);break;}
		   case 'N':print_main_menu();break;
           case 'S':file_save();break;
		   default: continue;
		}
	}
    fclose(fp);
	return;
}


void file_load()
{
	if((fp=fopen("data","rb"))==NULL) {printf("\n无法读出文件data\n");exit(0);}
	assert(fp);total=0;
    for(i=0;i<=40;i++)
		fread(&stu[i],sizeof(struct _stu),1,fp);
    for(i=0;i<=40;i++)
		if(stu[i+1].num==0) break;
	total=i;
    fclose(fp);
	return;
}

void file_save()
{
	if((fp=fopen("data","wb"))==NULL) {printf("\n无法读写文件data\n");exit(0);}
	assert(fp);
    for(i=0;i<=total;i++)
		fwrite(&stu[i],sizeof(struct _stu),1,fp);
	printf(">保存成功..按任意键返回主菜单...\n");
	fclose(fp);
	system("pause");
	print_main_menu();
	return;
}

void sys_rec()
{
		printf("\n\n>您确定要初始化启动系统么? 注意您所有数据将丢失!!\n  按Y确认N返回:");
ask8:   sel=getchar();
		if(sel=='Y') goto sysrecfun;
		else if(sel=='N')	sys_load();
		else goto ask8;


sysrecfun:
	sel=1;
	if((fp=fopen("data","wb+"))==NULL) {printf("\n无法读写文件data\n");exit(0);}
	printf("\n\n>初始化成功,");
	fclose(fp);
	system("pause");
	sys_load();
	return;
}


//----------------------------------系统数组示范---------------------------------------------
void example_rec()
{
	stu[0].num=0;
	strcpy(stu[0].name,"exa");
    stu[0].age=20;
    stu[0].score.chinese=0;
    stu[0].score.math=0;
    stu[0].score.english=0;
    return;
}

