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
	wchar_t CpuName[255];//cpu �̸� O
	double CpuUsage;//cpu ��뷮 O
	DWORD CpuSpeed;	//cpu �ӵ� O �⺻�ӵ�
	int ProcessNum;	//process ���� O
	int ThreadNum;	//thread ���� O 
	int HandleNum;	//�ڵ鷯 ����(?)
	time_t BeginTime, WorkTime; // �۵��� �ð� O


	int SocketNum;	//���� ����(?)
	int CoreNum;	//�ھ� ����(?)
	int logicProc;  //�� ���μ���(O)
	bool isVirt;	//����ȭ ��뿩��(?)
	double L1Cach;	//L1 ĳ�� �뷮(?)
	double L2Cach;  //L2 ĳ�� �뷮(?)
	double L3Cach;	//L3 ĳ�� �뷮(?)

	HKEY hKey;
	DWORD cname_size;
	DWORD cspeed_size;

public:
	CpuData() {
		BeginTime = clock();
		
		//init ������
		GetSystemInfo(&SysInfo);
		//cpu Ÿ��, 
		InitReg();
		InitCPUName();
		InitThreadNum();
		InitCPUUsage();

	}

	void InitThreadNum() {
		ThreadNum = SysInfo.dwNumberOfProcessors; // cpu�� ������ ������.
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

		//GetSystemInfo �Լ��� �̿��� ���� �ھ� ������ �����ϴ�.
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