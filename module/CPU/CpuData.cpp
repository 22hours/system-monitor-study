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
	wchar_t CpuName[255];//cpu 이름 O
	double CpuUsage;//cpu 사용량 O
	double Speed;	//cpu 속도 
	int ProcessNum;	//process 갯수 O
	int ThreadNum;		//thread 갯수 O 
	int HandleNum;	//핸들러 개수(?)
	time_t WorkTime; // 작동된 시간

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
		InitCPUUsage();
	}

	void InitThreadNum() {
		ThreadNum = SysInfo.dwNumberOfProcessors; // cpu의 스레드 개수임.
	}

	void InitCPUUsage() {
		PdhOpenQuery(NULL, NULL, &cpuQuery);
		PdhAddCounter(cpuQuery, L"\\Processor(_Total)\\% Processor Time", NULL, &cpuTotal);
		PdhCollectQueryData(cpuQuery);
	}

	void InitCPUName() {
		HKEY hKey;
		int i = 0;
		long result = 0;
		DWORD c_size = sizeof(CpuName);

		//레지스트리를 조사하여 프로세서의 모델명을 얻어냅니다.
		RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"Hardware\\Description\\System\\CentralProcessor\\0", 0, KEY_QUERY_VALUE, &hKey);
		RegQueryValueEx(hKey, L"ProcessorNameString", NULL, NULL, (LPBYTE)CpuName, &c_size);
		RegCloseKey(hKey);

		//GetSystemInfo 함수를 이용해 논리적 코어 개수를 얻어냅니다.
		wchar_t num[8];
		SYSTEM_INFO systemInfo;
		GetSystemInfo(&systemInfo);
		swprintf(num, 8, L" * %d", systemInfo.dwNumberOfProcessors);
		wcscat_s(CpuName, 100, num);
	}
	
	double GetCpuUsage() {
		PDH_FMT_COUNTERVALUE counterVal;

		PdhCollectQueryData(cpuQuery);
		PdhGetFormattedCounterValue(cpuTotal, PDH_FMT_DOUBLE, NULL, &counterVal);
		return counterVal.doubleValue;
	}
	
	wstring GetCpuName() {
		return CpuName;
	}
};


int main() {
	CpuData c;
	
	wcout << "CPU Name : "<<c.GetCpuName()<<"\n";

	return 0;
}