#include<Windows.h>
#include<iostream>

using namespace std;

int main() {
	HKEY h_key;
	LONG result = RegOpenKeyEx(HKEY_USERS,
		"S-1-5-18\\Printers\\ConvertUserDevModesCount\\",
		0,
		KEY_SET_VALUE, &h_key
	);
	if (result == ERROR_SUCCESS) {
		char buffer[64] = "Tired";
		RegSetValueEx(h_key,
			"Damin", //���� �׸�
			0,
			REG_SZ,
			(const unsigned char*)buffer,
			NULL
		);
	}
	else {
		cout << "���� ����\n";
	}
	if (h_key != NULL) {
		RegCloseKey(h_key);
	}
}