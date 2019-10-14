/*
typedef struct _MEMORYSTATUS { // mst
	DWORD dwLength;        // MEMORYSTATUS 구조체 크기
	DWORD dwMemoryLoad;    // 현재 사용되고 있는 메모리 퍼센트
	DWORD dwTotalPhys;     // 전체 물리적 메모리 크기
	DWORD dwAvailPhys;     // 남아 있는 물리적 메모리
	DWORD dwTotalPageFile; // 전체 페이지 파일수
	DWORD dwAvailPageFile; // 현재 사용할수 잇는 페이지 파일수
	DWORD dwTotalVirtual;  // 전체 가상 메모리
	DWORD dwAvailVirtual;  // 현재 사용가능한 가상메모리
} MEMORYSTATUS, *LPMEMORYSTATUS;
*/

#include <Windows.h>
#include <iostream>

using namespace std;

const int DIV = 1024;
//원하는 사이즈 만큼에 메모리를 지정
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

		printf("메모리정보 갱신\n\n");
		usage = ms.dwMemoryLoad;
		physTotal = ms.dwTotalPhys;
		physAvail = ms.dwAvailPhys;
		virtTotal = ms.dwTotalVirtual;
		virtAvail = ms.dwAvailVirtual;
		pageTotal = ms.dwTotalPageFile;
		pageAvail = ms.dwAvailPageFile;
	}
	~MemoryData() {
		printf("~메모리정보 소멸\n\n---------------------------------------------\n");
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

	//메모리를 얻어옴
	while (true) {
		MemoryData md;

		//원하는 만큼 메모리가 없다.
		if (md.GetVirtAvail() < AvailableSize)
			cout << "메모리부족\n";
		else
		{
			cout << "현재 " << md.GetUsage() << "%의 메모리를 사용하고 있습니다\n";
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
