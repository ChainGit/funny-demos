#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS

#include "pcap.h"
#include <stdio.h>
#pragma comment(lib,"wpcap.lib")
#pragma comment(lib,"wsock32.lib")

/* IPv4 头的定义 */
struct ip_header{
#if defined(WORDS_BIGENDIAN)
	u_int8_t ip_version : 4,/* 版本 */
	ip_header_length : 4;/* 首部长度 */
#else
	u_int8_t ip_header_length : 4, ip_version : 4;
#endif
	u_int8_t ip_tos;/* 服务质量 */
	u_int16_t ip_length;/* 长度 */
	u_int16_t ip_id;/* 标识 */
	u_int16_t ip_off;/* 偏移 */
	u_int8_t ip_ttl; /* 生存时间 */
	u_int8_t ip_protocol;/* 协议类型 */
	u_int16_t ip_checksum;/* 校验和 */
	struct in_addr ip_souce_address;/* 源IP地址 */
	struct in_addr ip_destination_address;/* 目的IP地址 */
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*下面是实现IP协议分析的函数，其函数类型与回调函数相同 */
void ip_protocol_packet_callback(u_char *argument, const struct pcap_pkthdr *packet_header, const u_char *packet_content){

	FILE *fp = fopen("./get.txt", "a");

	static int total = 0;

	struct ip_header *ip_protocol;/* IP协议变量 */
	u_int header_length;/* 长度 */
	u_int offset;/* 偏移 */
	u_char tos; /* 服务质量 */
	u_int16_t checksum; /* 校验和 */
	ip_protocol = (struct ip_header*)(packet_content + 14);/* 获得IP协议内容 */
	checksum = ntohs(ip_protocol->ip_checksum); /* 获得校验和 */
	header_length = ip_protocol->ip_header_length * 4; /* 获得长度 */
	tos = ip_protocol->ip_tos;/* 获得服务质量 */
	offset = ntohs(ip_protocol->ip_off);/* 获得偏移 */

	system("cls");
	total++;
	printf("IP数据包捕获与解析Demo by Chain\n");
	printf("\n[%d]\n",total);
	printf("┌──────────IP协议────────────┐\n");
	printf("│       版本号:        %-8d         (0x%02x)\n", ip_protocol->ip_version, ip_protocol->ip_version);
	printf("│     报头长度:        %-8d         (0x%02x)\n", header_length, header_length);
	printf("│     服务类型:        %-8d         (0x%02x)\n",tos,tos);
	printf("│       总长度:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_length), ntohs(ip_protocol->ip_length));
	printf("│       标识符:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_id), ntohs(ip_protocol->ip_id));
	printf("│       标志域:        %-8d         (0x%02x)\n", (offset & 0xe000)>>13, (offset & 0xe000)>>13);
	printf("│   分段偏移值:        %-8d         (0x%02x)\n", (offset & 0x1fff)<<3, (offset & 0x1fff)<<3);
	printf("│     生存时间:        %-8d         (0x%02x)\n", ip_protocol->ip_ttl, ip_protocol->ip_ttl);
	printf("│ 上层协议类型:        %-8d         ", ip_protocol->ip_protocol);
	switch (ip_protocol->ip_protocol){
	case 6:
		printf("(TCP)\n");
		break;
	case 17:
		printf("(UDP)\n");
		break;
	case 1:
		printf("(ICMP)\n");
		break;
	default:
		break;
	}
	printf("│   报头校验和:        %-8d         (0x%02x)\n", checksum, checksum);
	printf("│     源IP地址:        %-36s\n", inet_ntoa(ip_protocol->ip_souce_address));/* 获得源IP地址 */
	printf("│   目的IP地址:        %-36s\n", inet_ntoa(ip_protocol->ip_destination_address));/* 获得目的IP地址 */
	printf("└─────────────────────────┘\n");

	if (total == 1) fprintf(fp, "IP数据包捕获与解析Demo by Chain\n");
	fprintf(fp,"\n[%d]\n", total);
	fprintf(fp, "┌──────────IP协议────────────┐\n");
	fprintf(fp,"│       版本号:        %-8d         (0x%02x)\n", ip_protocol->ip_version, ip_protocol->ip_version);
	fprintf(fp,"│     报头长度:        %-8d         (0x%02x)\n", header_length, header_length);
	fprintf(fp,"│     服务类型:        %-8d         (0x%02x)\n", tos, tos);
	fprintf(fp,"│       总长度:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_length), ntohs(ip_protocol->ip_length));
	fprintf(fp,"│       标识符:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_id), ntohs(ip_protocol->ip_id));
	fprintf(fp,"│       标志域:        %-8d         (0x%02x)\n", (offset & 0xe000) >> 13, (offset & 0xe000) >> 13);
	fprintf(fp,"│   分段偏移值:        %-8d         (0x%02x)\n", (offset & 0x1fff) << 3, (offset & 0x1fff) << 3);
	fprintf(fp,"│     生存时间:        %-8d         (0x%02x)\n", ip_protocol->ip_ttl, ip_protocol->ip_ttl);
	fprintf(fp,"│ 上层协议类型:        %-8d         ", ip_protocol->ip_protocol);
	switch (ip_protocol->ip_protocol){
	case 6:
		fprintf(fp,"(TCP)\n");
		break;
	case 17:
		fprintf(fp,"(UDP)\n");
		break;
	case 1:
		fprintf(fp,"(ICMP)\n");
		break;
	default:
		break;
	}
	fprintf(fp,"│   报头校验和:        %-8d         (0x%02x)\n", checksum, checksum);
	fprintf(fp,"│     源IP地址:        %-36s\n", inet_ntoa(ip_protocol->ip_souce_address));/* 获得源IP地址 */
	fprintf(fp,"│   目的IP地址:        %-36s\n", inet_ntoa(ip_protocol->ip_destination_address));/* 获得目的IP地址 */
	fprintf(fp,"└─────────────────────────┘\n");
}

/*主函数 */
int main(){

	FILE *fp = fopen("./get.txt","w");
	fputc(' ',fp);
	fclose(fp);

	pcap_if_t *alldevs;
	pcap_if_t *d;
	//WinPcap句柄
	pcap_t *pcap_handle;
	//error_content错误缓冲区，相当于effbuf，存储错误信息
	char error_content[PCAP_ERRBUF_SIZE];
	//i指的是网卡的序列号
	int i = 0; 
	//网卡的编号
	int inum;
	//BPF过滤规则
	struct bpf_program bpf_filter;
	//过滤规则字符串
	char packet_filter[20];
	//掩码
	bpf_u_int32 net_mask;
	//网路地址
	bpf_u_int32 net_ip;

	printf("IP数据包捕获与解析Demo by Chain\n\n");

	//取得列表
	//pcap_findalldevs()是用来获得主机的网络设备列表，获取的网络设备就存储在参数alldevs中
	if (pcap_findalldevs(&alldevs, error_content) == -1)  {
		fprintf(stderr, "pcap_findalldevs发生错误: %s\n", error_content);
		exit(1);
	}
	// 输出网卡信息
	for (d = alldevs; d; d = d->next){      
		//next表示指向下一个网络设备
		printf("%d. 网卡名: %s \n", ++i, d->name);
		if (d->description)
			printf(" 网卡描述: %s \n", d->description);
		else
			printf(" (No description available)\n");
	}
	if (i == 0) {
		printf("\n没有找到任何网卡，请确认Winpcap已经安装.\n");
		return -1;
	}
	printf("\n\n请输入网卡编号(1-%d): ", i);
	scanf("%d", &inum);
	// 检测用户是否指定了有效网卡
	if (inum < 1 || inum > i){
		printf("\n网卡编号超出范围.\n");
		//释放列表
		pcap_freealldevs(alldevs);
		return -1;
	}else{
		printf("\n已选定编号为[%d]的网卡!\n",inum);
	}

	//跳转到选中的适配器
	for (d = alldevs, i = 0; i< inum - 1; d = d->next, i++); 
	//获得网络地址和掩码
	pcap_lookupnet(d->name, &net_ip, &net_mask, error_content);
	//打开网路接口
	pcap_handle = pcap_open_live(d->name, BUFSIZ, 1, 1, error_content); 
	//选择过滤包类型
	printf("\n监听的数据包协议类型已设置为IP,按回车键开始抓包分析..");
	strcpy(packet_filter, "ip");

	//pcap_compile()编译BPF过滤规则
	pcap_compile(pcap_handle, &bpf_filter, packet_filter, 0, net_ip);
	
	//pcap_setfilter设置过滤规则
	pcap_setfilter(pcap_handle, &bpf_filter);
	if (pcap_datalink(pcap_handle) != DLT_EN10MB) return -1;

	getchar();
	getchar();
	printf("\n开始抓包分析...\n抓包分析结果保存在文件中..");

	//pcap_loop()表示捕获多个数据包，可以总是循环捕获数据包
	pcap_loop(pcap_handle, -1, ip_protocol_packet_callback, NULL);
	
	//关闭Winpcap操作
	pcap_close(pcap_handle);
	return 0;
}