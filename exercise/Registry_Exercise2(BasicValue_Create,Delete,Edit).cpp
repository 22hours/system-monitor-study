#include<windows.h>
#include<stdio.h>
#include<conio.h>

int main() {
	HKEY hKey;
	TCHAR szValue1[128] = TEXT("Registry Default Value Test");
	TCHAR szValue2[128] = TEXT("jongwuner.github.io");
	LONG result = 0;

	printf("HKCU\\jongwuner Ű�� �����մϴ�.\n");
	result = RegCreateKey(HKEY_CURRENT_USER, TEXT("jongwuner"), &hKey);

	if (result == ERROR_SUCCESS) printf("Ű ���� ����\n");
	else printf("Ű ���� ����\n");

	result = RegSetValue(hKey, NULL, REG_SZ, szValue1, 0);
	if (result == ERROR_SUCCESS) printf("1��° �׽�Ʈ ����\n");
	else printf("1��° �׽�Ʈ ����.\n");

	RegCloseKey(hKey);
	printf("��� �����Ϸ��� �ƹ�Ű�� ��������.\n");
	_getch();

	result = RegSetValue(HKEY_CURRENT_USER, TEXT("jongwuner"), REG_SZ, szValue2, 0);
	if (result == ERROR_SUCCESS) printf("2��° �׽�Ʈ ����\n");
	else printf("2��° �׽�Ʈ ����\n");

	printf("��� �����Ϸ��� �ƹ� Ű�� ��������.\n");
	_getch();

	result = RegDeleteKey(HKEY_CURRENT_USER, TEXT("jongwuner"));
	if (result == ERROR_SUCCESS) printf("Ű ���� ����\n");
	else printf("Ű ���� ����\n");

	printf("��\n");
	getch();

	return 0;
}