/*
Prototype
BOOL GetDiskFreeSpaceEx   (
						   LPCWSTR                   lpDirectoryName,
						   PULARGE_INTEGER     lpFreeBytesAvailableToCaller,
						   PULARGE_INTEGER     lpTotalNumberOfBytes,
						   PULARGE_INTEGER     lpTotalNumberBytesOfFreeBytes  )

						   Parameters
						   lpDirectoryName [in]
						   - 알고자 하는 저장매체의 디렉토리 이름
						   - NULL 이면 Object Stroe 의 공간 정보를 가져온다.

						   lpFreeBytesAvailableToCaller [out]
						   - 남은 저장공간 용량

						   lpTotalNumberOfBytes [out]
						   - 전체 저장공간 용량
						   - NULL
						   -

						   lpTotalNumberBytesOfFreeBytes [out]
						   - 전체 저장공간에서 남은 저장공간 용량
						   - NULL

						   Retrun Value
						   - 0 이면 실패 ( GetLastError() 로 실패 확인 가능 )
						   - 0 이 아니면 성공
*/

#include <Windows.h>
#include <iostream>

using namespace std;

const int ByteVariable = 1024;

class StorageData {
private:
	ULARGE_INTEGER FreeByteAvailableToCaller;
	ULARGE_INTEGER TotalNumberBytes;
	ULARGE_INTEGER dwDiskFree;
public:
	StorageData() {}
	~StorageData() {}
	float GetByte(ULARGE_INTEGER dwParam)
	{
		//곱해주는 수의 따라 단위 변경
		//ByteVariable 1번 : Kilo
		//ByteVariable 2번 : Mega
		//ByteVariable 3번 : Giga
		//소수점 필요없이 빠른 연산이 필요한 경우 return ((float)dwParam.u.HighPart * 4) + ((float)(dwParam.u.LowPart >>30));
		return ((float)dwParam.u.HighPart * 4) + ((float)dwParam.u.LowPart / (ByteVariable*ByteVariable*ByteVariable));
	}
	void PrintSystemDriveByte()
	{
		GetDiskFreeSpaceEx(TEXT("c:\\"), &FreeByteAvailableToCaller, &TotalNumberBytes, &dwDiskFree);

		cout << "C: 총 용량 : " << GetByte(TotalNumberBytes) << "G byte " << endl;
		cout << "C: 사용중인  용량 : " << GetByte(TotalNumberBytes) - GetByte(dwDiskFree) << "G byte " << endl;
		cout << "C: 남은 용량 : " << GetByte(dwDiskFree) << "G byte " << endl;
	}
	void PrintEtcDriveByte() {
		GetDiskFreeSpaceEx(TEXT("d:\\"), &FreeByteAvailableToCaller, &TotalNumberBytes, &dwDiskFree);

		cout << "-------------------------------------------------------" << endl;
		cout << "D: 총 용량 : " << GetByte(TotalNumberBytes) << "G byte " << endl;
		cout << "D: 사용중인  용량 : " << GetByte(TotalNumberBytes) - GetByte(dwDiskFree) << "G byte " << endl;
		cout << "D: 남은 용량 : " << GetByte(dwDiskFree) << "G byte " << endl;
	}

};



int main()
{
	StorageData sd;
	sd.PrintSystemDriveByte();

	return 0;
}
