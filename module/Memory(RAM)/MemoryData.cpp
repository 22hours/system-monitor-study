/*
typedef struct _MEMORYSTATUS { // mst
	DWORD dwLength;        // MEMORYSTATUS ����ü ũ��
	DWORD dwMemoryLoad;    // ���� ���ǰ� �ִ� �޸� �ۼ�Ʈ
	DWORD dwTotalPhys;     // ��ü ������ �޸� ũ��
	DWORD dwAvailPhys;     // ���� �ִ� ������ �޸�
	DWORD dwTotalPageFile; // ��ü ������ ���ϼ�
	DWORD dwAvailPageFile; // ���� ����Ҽ� �մ� ������ ���ϼ�
	DWORD dwTotalVirtual;  // ��ü ���� �޸�
	DWORD dwAvailVirtual;  // ���� ��밡���� ����޸�
} MEMORYSTATUS, *LPMEMORYSTATUS;
*/

#include <Windows.h>
#include <iostream>

using namespace std;

const int DIV = 1024;
//���ϴ� ������ ��ŭ�� �޸𸮸� ����
const int AvailableSize = 1000;

class MemoryData {
private:
	DWORD usage;
	DWORD physTotal;
	DWORD physAvail;
	DWORD virtTotal;
	DWORD virtAvail;
	DWORD pageTotal;
	DWORD pageAvail;

public:
	MemoryData() {
		MEMORYSTATUS ms;
		GlobalMemoryStatus(&ms);

		printf("�޸����� ����\n\n");
		usage = ms.dwMemoryLoad;
		physTotal = ms.dwTotalPhys;
		physAvail = ms.dwAvailPhys;
		virtTotal = ms.dwTotalVirtual;
		virtAvail = ms.dwAvailVirtual;
		pageTotal = ms.dwTotalPageFile;
		pageAvail = ms.dwAvailPageFile;
	}
	~MemoryData() {
		printf("~�޸����� �Ҹ�\n\n---------------------------------------------\n");
	}

	DWORD GetUsage() { return usage; }
	DWORD GetPhysTotal() { return physTotal; }
	DWORD GetPhysAvail() { return physAvail; }
	DWORD GetVirtTotal() { return virtTotal; }
	DWORD GetVirtAvail() { return virtAvail; }
	DWORD GetPageTotal() { return pageTotal; }
	DWORD GetPageAvail() { return pageAvail; }
};

int main()
{

	//�޸𸮸� ����
	while (true) {
		MemoryData md;

		//���ϴ� ��ŭ �޸𸮰� ����.
		if (md.GetVirtAvail() < AvailableSize)
			cout << "�޸𸮺���\n";
		else
		{
			cout << "���� " << md.GetUsage() << "%�� �޸𸮸� ����ϰ� �ֽ��ϴ�\n";
			cout << md.GetPhysTotal() / DIV << " Kbyte of Physical Memory (total) \n";
			cout << md.GetPhysAvail() / DIV << " Kbyte of Physical Memory (free) \n";

			cout << md.GetPageTotal() / DIV << " Kbyte of Paging File (total)\n";
			cout << md.GetPageAvail() / DIV << " Kbyte of Paging File (free) \n";

			cout << md.GetVirtTotal() / DIV << " Kbyte of virtual memory (total)\n";
			cout << md.GetVirtAvail() / DIV << " Kbyte of virtual memory (free)\n";
		}
		Sleep(3000);
	}
	return 0;
}
