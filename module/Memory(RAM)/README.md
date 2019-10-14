# Memory(RAM)

 _MEMORYSTATUS 구조체를 활용하여 만듬.

```c++

typedef struct _MEMORYSTATUS { // mst
	DWORD dwLength;        // MEMORYSTATUS 구조체 크기
	DWORD dwMemoryLoad;    // 현재 사용되고 있는 메모리 퍼센트
	DWORD dwTotalPhys;     // 전체 물리적 메모리 크기
	DWORD dwAvailPhys;     // 남아 있는 물리적 메모리
	DWORD dwTotalPageFile; // 전체 페이지 파일수
	DWORD dwAvailPageFile; // 현재 사용할수 잇는 페이지 파일수
	DWORD dwTotalVirtual;  // 전체 가상 메모리
	DWORD dwAvailVirtual;  // 현재 사용가능한 가상메모리
} MEMORYSTATUS, *LPMEMORYSTATUS;

```



## 가상메모리, 물리메모리



##  페이징

