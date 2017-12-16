#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <windows.h>

//#define F1  ((f1/f2-int(f1/f2))<1e-6)&&((f1/f2-int(f1/f2))>1e-6)
//#define F2  ((f2/f1-int(f2/f1))<1e-6)&&((f2/f1-int(f2/f1))>1e-6)

#define G1 (a1==a2)&&(x1==x2)&&(b1==b2)&&(c1==c2)&&(y1==y2)&&(d1==d2)&&(z1==z2)
#define G2 (a1==b2)&&(x1==x2)&&(b1==a2)&&(b1==d2)&&(y1==y2)&&(d1==c2)&&(z1==z2)
#define G3 (a1==b2)&&(x1==x2)&&(b1==a2)&&(c1==c2)&&(y1==y2)&&(d1==d2)&&(z1==z2)
#define G4 (b1==d2)&&(y1==y2)&&(d1==c2)&&(a1==a2)&&(x1==x2)&&(b1==b2)&&(z1==z2)
#define G5 (x1==x2)&&(y1==y2)&&(z1==z2)

int a[4] = { 0 };
int x[4] = { 0 };
int m = 0, i = 0, t = 0;
char out[100][8] = { 0 };

void intial();
void main0();
void pass();
void output();
void swap(int &a, int &b);
void arrange(int begin, int end);
void court(int p1, int p2, int p3);

void main()
{
	while (1)
	{
		intial();

		system("cls");
		
		main0();

		fflush(stdin);
		system("pause");
	}
}

void intial()
{
	int ii, jj;
	m = 0; i = 0; t = 0;
	for (ii = 0; ii<100; ii++)
		for (jj = 0; jj<8; jj++)	out[ii][jj] = 0;
	for (ii = 0; ii<4; ii++)
	{
		a[ii] = 0; x[ii] = 0;
	}
}


void main0()
{
	printf("-------- 计算24点 --------\n\n");
	printf("请输入四个1~10的整数:\n\n");
	char inp;
	for (i = 0; i<4; i++)
	{
		printf("请输入第%d个整数:", i + 1);
		scanf("%d", &inp);
		if (inp<1 || inp>10)
		{
			printf("输入错误!\n\n");
			/*			for(int ww=i;ww<4;ww++)
			a[ww]=0;
			i--;
			*/
			return;
		}
		else a[i] = inp;
	}

	pass();

	printf("\n您输入的四个数为:");
	for (i = 0; i<4; i++) printf("%3d", x[i]);
	printf("\n\n");

	arrange(0, 4);

	output();


	printf("\n计算完毕,共有%d种算法.\n\n", t);

	return;
}

void pass()
{
	for (i = 0; i<4; i++) x[i] = a[i];
	return;
}

void swap(int &a, int &b)
{
	int temp = a;
	a = b;
	b = temp;
	return;
}

void arrange(int begin, int end)
{
	if (begin == end - 1)
	{
		int n1, n2, n3;

		for (n1 = 1; n1 <= 4; n1++)
		{
			for (n2 = 1; n2 <= 4; n2++)
			{
				for (n3 = 1; n3 <= 4; n3++)
				{
					court(n1, n2, n3);
				}
				n3 = 1;
			}
			n2 = 1;
		}
	}
	else
	{
		for (int k = begin; k<end; k++)
		{
			swap(x[k], x[begin]);
			arrange(begin + 1, end);
			swap(x[k], x[begin]);
		}
	}
}

void court(int p1, int p2, int p3)
{
	int pa1 = 0, pa2 = 0, pa3 = 0;
	char par1 = ' ', par2 = ' ', par3 = ' ';


	/*	float f1,f2;

	f1=x[0];f2=x[1];

	if(F1) goto over;
	if(F2) goto over;
	*/
	switch (p1)
	{
	case 1:{pa1 = x[0] + x[1]; par1 = '+'; break; }
	case 2:{pa1 = x[0] - x[1]; par1 = '-'; break; }
	case 3:{pa1 = x[0] * x[1]; par1 = '*'; break; }
	case 4:{
		if (x[0] % x[1] == 0)
		{
			pa1 = x[0] / x[1];
			par1 = '/';
		}
		else goto over;
		break;
	}
	default:break;
	}

	/*	f1=x[2];f2=x[3];

	if(F1) goto over;
	if(F2) goto over;
	*/
	switch (p2)
	{
	case 1:{pa2 = x[2] + x[3]; par2 = '+'; break; }
	case 2:{pa2 = x[2] - x[3]; par2 = '-'; break; }
	case 3:{pa2 = x[2] * x[3]; par2 = '*'; break; }
	case 4:{
		if (x[2] % x[3] == 0)
		{
			pa2 = x[2] / x[3];
			par2 = '/';
		}
		else goto over;
		break;
	}
	default:break;
	}

	/*	f1=pa1;f2=pa2;

	if(F1) goto over;
	if(F2) goto over;
	*/
	switch (p3)
	{
	case 1:{pa3 = pa1 + pa2; par3 = '+'; break; }
	case 2:{pa3 = pa1 - pa2; par3 = '-'; break; }
	case 3:{pa3 = pa1*pa2; par3 = '*'; break; }
	case 4:{
		if (pa2 != 0 && (pa1%pa2 == 0))
		{
			pa3 = pa1 / pa2;
			par3 = '/';
		}
		else goto over;
		break;
	}
	default:break;
	}

	//	if(pa3==24) {printf("(%d%c%d)%c(%d%c%d)=%d\n",x[0],par1,x[1],par3,x[2],par2,x[3],pa3);m++;}
	if (pa3 == 24)
	{
		out[m][0] = x[0]; out[m][1] = par1; out[m][2] = x[1]; out[m][3] = par3;
		out[m][4] = x[2]; out[m][5] = par2; out[m][6] = x[3]; out[m][7] = 1; m++;
	}
over:
	return;
}

void output()
{
	int p1, k2;
	char a1, x1, b1, c1, y1, d1, z1;
	char a2, x2, b2, c2, y2, d2, z2;

	for (p1 = 0; p1<m; p1++)
	{
		for (k2 = p1 + 1; k2<m; k2++)
		{
			if (out[k2][7] == 1)
			{
				a1 = out[p1][0]; x1 = out[p1][1]; b1 = out[p1][2]; c1 = out[p1][4]; y1 = out[p1][5]; d1 = out[p1][6]; z1 = out[p1][3];

				a2 = out[k2][0]; x2 = out[k2][1]; b2 = out[k2][2]; c2 = out[k2][4]; y2 = out[k2][5]; d2 = out[k2][6]; z2 = out[k2][3];
				if (G1) out[k2][7] = 0;
				if (G2) out[k2][7] = 0;
				if (G3) out[k2][7] = 0;
				if (G4) out[k2][7] = 0;
				if (G5) out[k2][7] = 0;



				a2 = out[k2][4]; x2 = out[k2][5]; b2 = out[k2][6]; c2 = out[k2][0]; y2 = out[k2][1]; d2 = out[k2][2]; z2 = out[k2][3];
				if (G1) out[k2][7] = 0;
				if (G2) out[k2][7] = 0;
				if (G3) out[k2][7] = 0;
				if (G4) out[k2][7] = 0;
				if (G5) out[k2][7] = 0;
			}
		}
	}

	int a0, b0, c0, d0;
	for (p1 = 0; p1<m; p1++)
		if (out[p1][7] == 1)
		{
			t++;
			a0 = out[p1][0];
			b0 = out[p1][2];
			c0 = out[p1][4];
			d0 = out[p1][6];

			printf("%-2d: (%d%c%d)%c(%d%c%d)=24\n", t, a0, out[p1][1], b0, out[p1][3], c0, out[p1][5], d0);

		}

	return;
}