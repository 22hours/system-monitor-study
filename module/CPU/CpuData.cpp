#pragma comment(lib, "pdh.lib")

#include<iostream>
#include<string>
#include<windows.h>
#include "TCHAR.h"
#include "pdh.h"
#include "atlstr.h"
#include<time.h>

using namespace std;

static PDH_HQUERY cpuQuery;
static PDH_HCOUNTER cpuTotal;

using namespace std;


class CpuData {
private:
	SYSTEM_INFO SysInfo;
	wchar_t CpuName[255];//cpu 이름 O
	double CpuUsage;//cpu 사용량 O
	DWORD CpuSpeed;	//cpu 속도 O 기본속도
	int ProcessNum;	//process 갯수 O
	int ThreadNum;	//thread 갯수 O 
	int HandleNum;	//핸들러 개수(?)
	time_t BeginTime, WorkTime; // 작동된 시간 O


	int SocketNum;	//소켓 갯수(?)
	int CoreNum;	//코어 갯수(?)
	int logicProc;  //논리 프로세서(O)
	bool isVirt;	//가상화 사용여부(?)
	double L1Cach;	//L1 캐시 용량(?)
	double L2Cach;  //L2 캐시 용량(?)
	double L3Cach;	//L3 캐시 용량(?)

	HKEY hKey;
	DWORD cname_size;
	DWORD cspeed_size;

public:
	CpuData() {
		BeginTime = clock();
		
		//init 생성자
		GetSystemInfo(&SysInfo);
		//cpu 타입, 
		InitReg();
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
	void InitReg() {
		cname_size = sizeof(CpuName);
		cspeed_size = sizeof(CpuSpeed);

		RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"Hardware\\Description\\System\\CentralProcessor\\0", 0, KEY_QUERY_VALUE, &hKey);
		RegQueryValueEx(hKey, L"ProcessorNameString", NULL, NULL, (LPBYTE)CpuName, &cname_size);
		RegQueryValueEx(hKey, L"~MHz", NULL, NULL, (LPBYTE)&CpuSpeed, &cspeed_size); // CPU Speed
		RegCloseKey(hKey);
	}
	void InitCPUName() {

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
	DWORD GetCpuSpeed() {
		return CpuSpeed / 1000.0;
	}
	time_t GetWorkTime() {
		WorkTime = clock();
		return (WorkTime - BeginTime) / 1000.0;
	}
};


int main() {
	CpuData c;

	wcout << "CPU Name : " << c.GetCpuName() << "\n";
	while (true) {
		cout << "CPU Usage : " << c.GetCpuUsage() << "% \n";
		cout << "CPU Speed : " << c.GetCpuSpeed() << "GHz \n";
		cout << "CPU WorkTime : " << c.GetWorkTime() << "Sec \n";
		cout << "Sleep !\n\n";
		Sleep(1000);
	}

	return 0;
}