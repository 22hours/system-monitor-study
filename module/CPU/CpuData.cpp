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
	TCHAR CpuName[100];//cpu �̸� O
	double CpuUsage;//cpu ��뷮 O
	double Speed;	//cpu �ӵ� 
	int ProcessNum;	//process ���� O
	int ThreadNum;		//thread ���� O 
	int HandleNum;	//�ڵ鷯 ����(?)
	time_t WorkTime; // �۵��� �ð�

	static PDH_HQUERY cpuQuery;
	static PDH_HCOUNTER cpuTotal;

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
		ThreadNum = SysInfo.dwNumberOfProcessors; // cpu�� ������ ������.
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