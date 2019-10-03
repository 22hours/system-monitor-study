#include<windows.h>
#include<stdio.h>
#include<conio.h>

int main() {
	HKEY hKey1, hKey2;
	TCHAR keyName[512];
	DWORD i = 0;
	LONG result;

	printf("1. HKCU 서브키들을 조회합니다.");
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

	printf("계속하려면 아무키나 누르세요.\n\n");
	_getch();
	///////////////////////////////////////////////////
	printf("2. HKEY_CURRENT_USER\\Tapito\\레지스트리 키를 생성합니다.\n");
	result = RegCreateKey(HKEY_CURRENT_USER, TEXT("Tapito"), &hKey1);
	if (result == ERROR_SUCCESS) {
		printf("Tapito 키 생성 성공\n");
		result = RegCreateKey(hKey1, TEXT("레지스트리"), &hKey2);
		if (result == ERROR_SUCCESS) printf("'레지스트리' 키 생성 성공 \n");
		else printf("레지스트리 키 생성 실패 \n");
	}
	else printf("Tapito 키 생성 실패\n");

	RegCloseKey(hKey2);
	RegCloseKey(hKey1);

	printf("계속 하려면 아무 키나 누르세요.\n\n");
	_getch();
	/////////////////////////////////////////////////////

	printf("3. HKCU\\Tapito\\레지스트리 키를 제거 합니다.\n");
	RegDeleteKey(HKEY_CURRENT_USER, TEXT("Tapito\\레지스트리"));
	RegDeleteKey(HKEY_CURRENT_USER, TEXT("Tapito"));

	printf("계속 하려면 아무 키나 누르세요.\n");
	_getch();
	////////////////////////////////////////////////////
	printf("끝.\n");
	_getch();
	return 0;
}