/*
Prototype
BOOL GetDiskFreeSpaceEx   (
						   LPCWSTR                   lpDirectoryName,
						   PULARGE_INTEGER     lpFreeBytesAvailableToCaller,
						   PULARGE_INTEGER     lpTotalNumberOfBytes,
						   PULARGE_INTEGER     lpTotalNumberBytesOfFreeBytes  )

						   Parameters
						   lpDirectoryName [in]
						   - �˰��� �ϴ� �����ü�� ���丮 �̸�
						   - NULL �̸� Object Stroe �� ���� ������ �����´�.

						   lpFreeBytesAvailableToCaller [out]
						   - ���� ������� �뷮

						   lpTotalNumberOfBytes [out]
						   - ��ü ������� �뷮
						   - NULL
						   -

						   lpTotalNumberBytesOfFreeBytes [out]
						   - ��ü ����������� ���� ������� �뷮
						   - NULL

						   Retrun Value
						   - 0 �̸� ���� ( GetLastError() �� ���� Ȯ�� ���� )
						   - 0 �� �ƴϸ� ����
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
		//�����ִ� ���� ���� ���� ����
		//ByteVariable 1�� : Kilo
		//ByteVariable 2�� : Mega
		//ByteVariable 3�� : Giga
		//�Ҽ��� �ʿ���� ���� ������ �ʿ��� ��� return ((float)dwParam.u.HighPart * 4) + ((float)(dwParam.u.LowPart >>30));
		return ((float)dwParam.u.HighPart * 4) + ((float)dwParam.u.LowPart / (ByteVariable*ByteVariable*ByteVariable));
	}
	void PrintSystemDriveByte()
	{
		GetDiskFreeSpaceEx(TEXT("c:\\"), &FreeByteAvailableToCaller, &TotalNumberBytes, &dwDiskFree);

		cout << "C: �� �뷮 : " << GetByte(TotalNumberBytes) << "G byte " << endl;
		cout << "C: �������  �뷮 : " << GetByte(TotalNumberBytes) - GetByte(dwDiskFree) << "G byte " << endl;
		cout << "C: ���� �뷮 : " << GetByte(dwDiskFree) << "G byte " << endl;
	}
	void PrintEtcDriveByte() {
		GetDiskFreeSpaceEx(TEXT("d:\\"), &FreeByteAvailableToCaller, &TotalNumberBytes, &dwDiskFree);

		cout << "-------------------------------------------------------" << endl;
		cout << "D: �� �뷮 : " << GetByte(TotalNumberBytes) << "G byte " << endl;
		cout << "D: �������  �뷮 : " << GetByte(TotalNumberBytes) - GetByte(dwDiskFree) << "G byte " << endl;
		cout << "D: ���� �뷮 : " << GetByte(dwDiskFree) << "G byte " << endl;
	}

};



int main()
{
	StorageData sd;
	sd.PrintSystemDriveByte();

	return 0;
}
