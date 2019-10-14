# Storage(HDD, SDD)

**GetDiskFreeSpaceEx**를 사용

```C++
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
```



## Reference 

https://m.blog.naver.com/sorkelf/40135483947