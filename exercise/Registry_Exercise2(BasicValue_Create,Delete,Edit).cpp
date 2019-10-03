#include<windows.h>
#include<stdio.h>
#include<conio.h>

int main() {
	HKEY hKey;
	TCHAR szValue1[128] = TEXT("Registry Default Value Test");
	TCHAR szValue2[128] = TEXT("jongwuner.github.io");
	LONG result = 0;

	printf("HKCU\\jongwuner 키를 생성합니다.\n");
	result = RegCreateKey(HKEY_CURRENT_USER, TEXT("jongwuner"), &hKey);

	if (result == ERROR_SUCCESS) printf("키 생성 성공\n");
	else printf("키 생성 실패\n");

	result = RegSetValue(hKey, NULL, REG_SZ, szValue1, 0);
	if (result == ERROR_SUCCESS) printf("1번째 테스트 성공\n");
	else printf("1번째 테스트 실패.\n");

	RegCloseKey(hKey);
	printf("계속 진행하려면 아무키나 누르세요.\n");
	_getch();

	result = RegSetValue(HKEY_CURRENT_USER, TEXT("jongwuner"), REG_SZ, szValue2, 0);
	if (result == ERROR_SUCCESS) printf("2번째 테스트 성공\n");
	else printf("2번째 테스트 실패\n");

	printf("계속 진행하려면 아무 키나 누르세요.\n");
	_getch();

	result = RegDeleteKey(HKEY_CURRENT_USER, TEXT("jongwuner"));
	if (result == ERROR_SUCCESS) printf("키 삭제 성공\n");
	else printf("키 삭제 실패\n");

	printf("끝\n");
	getch();

	return 0;
}