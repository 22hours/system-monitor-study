#include<windows.h>
#include<stdio.h>
#include<conio.h>

int main() {
	HKEY hKey1, hKey2;
	TCHAR keyName[512];
	DWORD i = 0;
	LONG result;

	printf("1. HKCU ����Ű���� ��ȸ�մϴ�.");
	memset(keyName, 0, 512 * sizeof(TCHAR));
	printf("* HKEY_CURRENT_USER\n");
	while (RegEnumKey(HKEY_CURRENT_USER, i, keyName, 512) != ERROR_NO_MORE_ITEMS) {
		
        #if defined(_UNICODE) || defined(UNICODE)
		printf("\t* %ws\n", keyName);
		#else
		printf("\t* %s\n", keyName);
		#endif	

		i++;
		memset(keyName, 0, 512 * sizeof(TCHAR));
	}

	printf("����Ϸ��� �ƹ�Ű�� ��������.\n\n");
	_getch();
	///////////////////////////////////////////////////
	printf("2. HKEY_CURRENT_USER\\Tapito\\������Ʈ�� Ű�� �����մϴ�.\n");
	result = RegCreateKey(HKEY_CURRENT_USER, TEXT("Tapito"), &hKey1);
	if (result == ERROR_SUCCESS) {
		printf("Tapito Ű ���� ����\n");
		result = RegCreateKey(hKey1, TEXT("������Ʈ��"), &hKey2);
		if (result == ERROR_SUCCESS) printf("'������Ʈ��' Ű ���� ���� \n");
		else printf("������Ʈ�� Ű ���� ���� \n");
	}
	else printf("Tapito Ű ���� ����\n");

	RegCloseKey(hKey2);
	RegCloseKey(hKey1);

	printf("��� �Ϸ��� �ƹ� Ű�� ��������.\n\n");
	_getch();
	/////////////////////////////////////////////////////

	printf("3. HKCU\\Tapito\\������Ʈ�� Ű�� ���� �մϴ�.\n");
	RegDeleteKey(HKEY_CURRENT_USER, TEXT("Tapito\\������Ʈ��"));
	RegDeleteKey(HKEY_CURRENT_USER, TEXT("Tapito"));

	printf("��� �Ϸ��� �ƹ� Ű�� ��������.\n");
	_getch();
	////////////////////////////////////////////////////
	printf("��.\n");
	_getch();
	return 0;
}