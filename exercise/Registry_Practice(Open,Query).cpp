#include<Windows.h>
#include<conio.h>
#include<iostream>
#include<stdio.h>
using namespace std;

int main() {
	HKEY h_key;
	DWORD data_size;
	// ������Ʈ�� ����
	LONG OpenSuccess = RegOpenKeyEx(HKEY_LOCAL_MACHINE,
		"SOFTWARE\\Bandizip\\Capabilities\\", 0,
		KEY_ALL_ACCESS | KEY_WOW64_64KEY, &h_key);
	if (OpenSuccess == ERROR_SUCCESS) {
		// ������ ������ �켱 ������ �Ŀ�
		cout << "Open Success!!\n";
		LONG QuerySuccess = RegQueryValueEx(h_key, "ApplicationDescription"
			, 0, NULL, NULL, &data_size);
		if (data_size != 0) {
			// �׿� �´� ������ ��ŭ ������ �ޱ�
			char* p_data = new char[data_size];
			RegQueryValueEx(h_key, "ApplicationDescription"
				, 0, NULL, (LPBYTE)p_data, &data_size);
		}
		if (QuerySuccess == ERROR_SUCCESS) {
			cout << "Query Success!!\n";
		}
	}
	
	if (h_key != NULL)
		RegCloseKey(h_key);
}