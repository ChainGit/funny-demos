#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS

#include "pcap.h"
#include <stdio.h>
#pragma comment(lib,"wpcap.lib")
#pragma comment(lib,"wsock32.lib")

/* IPv4 ͷ�Ķ��� */
struct ip_header{
#if defined(WORDS_BIGENDIAN)
	u_int8_t ip_version : 4,/* �汾 */
	ip_header_length : 4;/* �ײ����� */
#else
	u_int8_t ip_header_length : 4, ip_version : 4;
#endif
	u_int8_t ip_tos;/* �������� */
	u_int16_t ip_length;/* ���� */
	u_int16_t ip_id;/* ��ʶ */
	u_int16_t ip_off;/* ƫ�� */
	u_int8_t ip_ttl; /* ����ʱ�� */
	u_int8_t ip_protocol;/* Э������ */
	u_int16_t ip_checksum;/* У��� */
	struct in_addr ip_souce_address;/* ԴIP��ַ */
	struct in_addr ip_destination_address;/* Ŀ��IP��ַ */
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*������ʵ��IPЭ������ĺ������亯��������ص�������ͬ */
void ip_protocol_packet_callback(u_char *argument, const struct pcap_pkthdr *packet_header, const u_char *packet_content){

	FILE *fp = fopen("./get.txt", "a");

	static int total = 0;

	struct ip_header *ip_protocol;/* IPЭ����� */
	u_int header_length;/* ���� */
	u_int offset;/* ƫ�� */
	u_char tos; /* �������� */
	u_int16_t checksum; /* У��� */
	ip_protocol = (struct ip_header*)(packet_content + 14);/* ���IPЭ������ */
	checksum = ntohs(ip_protocol->ip_checksum); /* ���У��� */
	header_length = ip_protocol->ip_header_length * 4; /* ��ó��� */
	tos = ip_protocol->ip_tos;/* ��÷������� */
	offset = ntohs(ip_protocol->ip_off);/* ���ƫ�� */

	system("cls");
	total++;
	printf("IP���ݰ����������Demo by Chain\n");
	printf("\n[%d]\n",total);
	printf("����������������������IPЭ�驤������������������������\n");
	printf("��       �汾��:        %-8d         (0x%02x)\n", ip_protocol->ip_version, ip_protocol->ip_version);
	printf("��     ��ͷ����:        %-8d         (0x%02x)\n", header_length, header_length);
	printf("��     ��������:        %-8d         (0x%02x)\n",tos,tos);
	printf("��       �ܳ���:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_length), ntohs(ip_protocol->ip_length));
	printf("��       ��ʶ��:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_id), ntohs(ip_protocol->ip_id));
	printf("��       ��־��:        %-8d         (0x%02x)\n", (offset & 0xe000)>>13, (offset & 0xe000)>>13);
	printf("��   �ֶ�ƫ��ֵ:        %-8d         (0x%02x)\n", (offset & 0x1fff)<<3, (offset & 0x1fff)<<3);
	printf("��     ����ʱ��:        %-8d         (0x%02x)\n", ip_protocol->ip_ttl, ip_protocol->ip_ttl);
	printf("�� �ϲ�Э������:        %-8d         ", ip_protocol->ip_protocol);
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
	printf("��   ��ͷУ���:        %-8d         (0x%02x)\n", checksum, checksum);
	printf("��     ԴIP��ַ:        %-36s\n", inet_ntoa(ip_protocol->ip_souce_address));/* ���ԴIP��ַ */
	printf("��   Ŀ��IP��ַ:        %-36s\n", inet_ntoa(ip_protocol->ip_destination_address));/* ���Ŀ��IP��ַ */
	printf("������������������������������������������������������\n");

	if (total == 1) fprintf(fp, "IP���ݰ����������Demo by Chain\n");
	fprintf(fp,"\n[%d]\n", total);
	fprintf(fp, "����������������������IPЭ�驤������������������������\n");
	fprintf(fp,"��       �汾��:        %-8d         (0x%02x)\n", ip_protocol->ip_version, ip_protocol->ip_version);
	fprintf(fp,"��     ��ͷ����:        %-8d         (0x%02x)\n", header_length, header_length);
	fprintf(fp,"��     ��������:        %-8d         (0x%02x)\n", tos, tos);
	fprintf(fp,"��       �ܳ���:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_length), ntohs(ip_protocol->ip_length));
	fprintf(fp,"��       ��ʶ��:        %-8d         (0x%02x)\n", ntohs(ip_protocol->ip_id), ntohs(ip_protocol->ip_id));
	fprintf(fp,"��       ��־��:        %-8d         (0x%02x)\n", (offset & 0xe000) >> 13, (offset & 0xe000) >> 13);
	fprintf(fp,"��   �ֶ�ƫ��ֵ:        %-8d         (0x%02x)\n", (offset & 0x1fff) << 3, (offset & 0x1fff) << 3);
	fprintf(fp,"��     ����ʱ��:        %-8d         (0x%02x)\n", ip_protocol->ip_ttl, ip_protocol->ip_ttl);
	fprintf(fp,"�� �ϲ�Э������:        %-8d         ", ip_protocol->ip_protocol);
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
	fprintf(fp,"��   ��ͷУ���:        %-8d         (0x%02x)\n", checksum, checksum);
	fprintf(fp,"��     ԴIP��ַ:        %-36s\n", inet_ntoa(ip_protocol->ip_souce_address));/* ���ԴIP��ַ */
	fprintf(fp,"��   Ŀ��IP��ַ:        %-36s\n", inet_ntoa(ip_protocol->ip_destination_address));/* ���Ŀ��IP��ַ */
	fprintf(fp,"������������������������������������������������������\n");
}

/*������ */
int main(){

	FILE *fp = fopen("./get.txt","w");
	fputc(' ',fp);
	fclose(fp);

	pcap_if_t *alldevs;
	pcap_if_t *d;
	//WinPcap���
	pcap_t *pcap_handle;
	//error_content���󻺳������൱��effbuf���洢������Ϣ
	char error_content[PCAP_ERRBUF_SIZE];
	//iָ�������������к�
	int i = 0; 
	//�����ı��
	int inum;
	//BPF���˹���
	struct bpf_program bpf_filter;
	//���˹����ַ���
	char packet_filter[20];
	//����
	bpf_u_int32 net_mask;
	//��·��ַ
	bpf_u_int32 net_ip;

	printf("IP���ݰ����������Demo by Chain\n\n");

	//ȡ���б�
	//pcap_findalldevs()��������������������豸�б���ȡ�������豸�ʹ洢�ڲ���alldevs��
	if (pcap_findalldevs(&alldevs, error_content) == -1)  {
		fprintf(stderr, "pcap_findalldevs��������: %s\n", error_content);
		exit(1);
	}
	// ���������Ϣ
	for (d = alldevs; d; d = d->next){      
		//next��ʾָ����һ�������豸
		printf("%d. ������: %s \n", ++i, d->name);
		if (d->description)
			printf(" ��������: %s \n", d->description);
		else
			printf(" (No description available)\n");
	}
	if (i == 0) {
		printf("\nû���ҵ��κ���������ȷ��Winpcap�Ѿ���װ.\n");
		return -1;
	}
	printf("\n\n�������������(1-%d): ", i);
	scanf("%d", &inum);
	// ����û��Ƿ�ָ������Ч����
	if (inum < 1 || inum > i){
		printf("\n������ų�����Χ.\n");
		//�ͷ��б�
		pcap_freealldevs(alldevs);
		return -1;
	}else{
		printf("\n��ѡ�����Ϊ[%d]������!\n",inum);
	}

	//��ת��ѡ�е�������
	for (d = alldevs, i = 0; i< inum - 1; d = d->next, i++); 
	//��������ַ������
	pcap_lookupnet(d->name, &net_ip, &net_mask, error_content);
	//����·�ӿ�
	pcap_handle = pcap_open_live(d->name, BUFSIZ, 1, 1, error_content); 
	//ѡ����˰�����
	printf("\n���������ݰ�Э������������ΪIP,���س�����ʼץ������..");
	strcpy(packet_filter, "ip");

	//pcap_compile()����BPF���˹���
	pcap_compile(pcap_handle, &bpf_filter, packet_filter, 0, net_ip);
	
	//pcap_setfilter���ù��˹���
	pcap_setfilter(pcap_handle, &bpf_filter);
	if (pcap_datalink(pcap_handle) != DLT_EN10MB) return -1;

	getchar();
	getchar();
	printf("\n��ʼץ������...\nץ����������������ļ���..");

	//pcap_loop()��ʾ���������ݰ�����������ѭ���������ݰ�
	pcap_loop(pcap_handle, -1, ip_protocol_packet_callback, NULL);
	
	//�ر�Winpcap����
	pcap_close(pcap_handle);
	return 0;
}