#include<Windows.h>
#include<conio.h>
#include<iostream>
using namespace std;

int main() {
	HKEY h_key;
	DWORD i = 0;
	TCHAR Subkeys[512]; // 키들을 담을 TCHAR 배열
	cout << "HKEY_USERS's Subkeys : ";
	while (RegEnumKey(HKEY_USERS, i, Subkeys, 512) != ERROR_NO_MORE_ITEMS) {
		cout << Subkeys << "\n";
		memset(Subkeys, 0, 512 * sizeof(TCHAR));
		i++;
	}
	memset(Subkeys, 0, 512 * sizeof(TCHAR));

	cout << "\nCreate Subkey on HKEY_USERS : Damin\n";
	LONG Success = RegCreateKey(HKEY_USERS,TEXT("Damin"), &h_key);
	if (Success == ERROR_SUCCESS) {
		cout << "Create Complete!\n";
	}
	else {
		cout << "Create Fail\n" << GetLastError();
	}
	cout << "\nCheck-------------------";
	cout << "\nHKEY_USERS's Subkeys : ";
	i = 0;
	while (RegEnumKey(HKEY_USERS, i, Subkeys, 512)
		!= ERROR_NO_MORE_ITEMS) {
		cout << Subkeys << "\n";
		memset(Subkeys, 0, 512 * sizeof(TCHAR));
		i++;
	}
	RegCloseKey(h_key);
	memset(Subkeys, 0, 512 * sizeof(TCHAR));
	cout << "\n Delete Subkey on HKEY_USERS : Damin\n";
	Success = RegDeleteKey(HKEY_USERS, TEXT("Damin"));
	i = 0;
	memset(Subkeys, 0, 512 * sizeof(TCHAR));
	while (RegEnumKey(HKEY_USERS, i, Subkeys, 512)
		!= ERROR_NO_MORE_ITEMS) {
		cout << Subkeys << "\n";
		memset(Subkeys, 0, 512 * sizeof(TCHAR));
		i++;
	}
}