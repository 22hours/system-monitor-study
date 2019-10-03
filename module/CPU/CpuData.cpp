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
	wchar_t CpuName[255];//cpu �̸� O
	double CpuUsage;//cpu ��뷮 O
	double Speed;	//cpu �ӵ� 
	int ProcessNum;	//process ���� O
	int ThreadNum;		//thread ���� O 
	int HandleNum;	//�ڵ鷯 ����(?)
	time_t WorkTime; // �۵��� �ð�

	double Speed_basic;	// �⺻�ӵ�(?)
	int SocketNum;	//���� ����(?)
	int CoreNum;	//�ھ� ����(?)
	int logicProc;  //�� ���μ���(?)
	bool isVirt;	//����ȭ ��뿩��
	double L1Cach;	//L1 ĳ�� �뷮
	double L2Cach;  //L2 ĳ�� �뷮
	double L3Cach;	//L3 ĳ�� �뷮

public:
	CpuData() {
		//init ������
		GetSystemInfo(&SysInfo);
		//cpu Ÿ��, 
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

	void InitCPUName() {
		HKEY hKey;
		int i = 0;
		long result = 0;
		DWORD c_size = sizeof(CpuName);

		//������Ʈ���� �����Ͽ� ���μ����� �𵨸��� �����ϴ�.
		RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"Hardware\\Description\\System\\CentralProcessor\\0", 0, KEY_QUERY_VALUE, &hKey);
		RegQueryValueEx(hKey, L"ProcessorNameString", NULL, NULL, (LPBYTE)CpuName, &c_size);
		RegCloseKey(hKey);

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
};


int main() {
	CpuData c;
	
	wcout << "CPU Name : "<<c.GetCpuName()<<"\n";

	return 0;
}