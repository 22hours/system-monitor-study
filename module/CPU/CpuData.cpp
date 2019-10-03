#pragma comment(lib, "pdh.lib")

#include<iostream>
#include<string>
#include<windows.h>
#include "TCHAR.h"
#include "pdh.h"
#include "atlstr.h"

using namespace std;

static PDH_HQUERY cpuQuery;
static PDH_HCOUNTER cpuTotal;

using namespace std;


class CpuData {
private:
	SYSTEM_INFO SysInfo;
	TCHAR CpuName[100];//cpu 이름 O
	double CpuUsage;//cpu 사용량 O
	double Speed;	//cpu 속도 
	int ProcessNum;	//process 갯수 O
	int ThreadNum;		//thread 갯수 O 
	int HandleNum;	//핸들러 개수(?)
	time_t WorkTime; // 작동된 시간

	static PDH_HQUERY cpuQuery;
	static PDH_HCOUNTER cpuTotal;

	double Speed_basic;	// 기본속도(?)
	int SocketNum;	//소켓 갯수(?)
	int CoreNum;	//코어 갯수(?)
	int logicProc;  //논리 프로세서(?)
	bool isVirt;	//가상화 사용여부
	double L1Cach;	//L1 캐시 용량
	double L2Cach;  //L2 캐시 용량
	double L3Cach;	//L3 캐시 용량

public:
	CpuData() {
		//init 생성자
		GetSystemInfo(&SysInfo);
		//cpu 타입, 
		InitCPUName();
		InitThreadNum();
	}
	void InitCPUName() {
		HKEY hKey;
		HKEY hk_1;
		int i = 0;
		long result = 0;
		DWORD c_size = sizeof(CpuName);
		
		RegOpenKeyEx(HKEY_LOCAL_MACHINE, TEXT("Hardware\\Desccription\\System\\CentralProcessor\\0"),0, KEY_QUERY_VALUE, &hKey);
		RegQueryValueEx(hKey, TEXT("ProcessorNameString"), NULL, NULL, (LPBYTE)CpuName, &c_size);

		RegCloseKey(hKey);
	}
	void InitThreadNum() {
		ThreadNum = SysInfo.dwNumberOfProcessors; // cpu의 스레드 개수임.
	}

	void InitCPUUsage() {
		PdhOpenQuery(NULL, NULL, &cpuQuery);
		PdhAddCounter(cpuQuery, L"\\Processor(_Total)\\% Processor Time", NULL, &cpuTotal);
		PdhCollectQueryData(cpuQuery);
	}

	CString getCpuName() {
		return CpuName;
	}
	
	double getCpuUsage() {
		PDH_FMT_COUNTERVALUE counterVal;

		PdhCollectQueryData(cpuQuery);
		PdhGetFormattedCounterValue(cpuTotal, PDH_FMT_DOUBLE, NULL, &counterVal);
		return counterVal.doubleValue;
	}
};


int main() {
	CpuData c;
	cout << c.getCpuName();

	return 0;
}