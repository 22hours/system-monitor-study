#include<iostream>
#include<WinSock2.h> // socket 사용 헤더파일
#include<string>

#pragma comment (lib, "ws2_32") // 위에 선언한 헤더파일들을 가져다 쓰기 위한 링크

#define PORT 11000

using namespace std;

void showError(string str) {
	cout << "에러 : " << str << "\n";
	return;
}

int main() {
	WSADATA data;
	WSAStartup(MAKEWORD(2, 2), &data);
	//WSADATA = Windows 소켓 초기화 정보 저장을 위한 구조체
	//WSAStartup = 윈도우즈에 어느 소켓을 활용할 것인지
	//MAKEWORD = WORD(unsigned char)를 만들어주기 위함 = 2.2버전

	SOCKET server;
	server = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	//PF_INET = IPv4 타입 사용한다는 뜻
	//SOCK_STRAM = 연결 지향형 소켓
	//IPPROTO_TCP = 프로토콜로 TCP 사용

	if (server == INVALID_SOCKET) showError("서버 생성 실패");

	SOCKADDR_IN Addr = {};
	Addr.sin_family = AF_INET;
	//#defind AF_INET 2	// internetwork : UDP, TCP, et.
	Addr.sin_port = htons(PORT);
	//htons = 빅엔디안 방식
	Addr.sin_addr.s_addr = htonl(INADDR_ANY);
	//s_addr = IPv4 Internet address
	//INADDR_ANY = 현재 동작되는 컴퓨터의 IP 주소
	//다민 컴퓨터 IP

	if (bind(server, (SOCKADDR*)&Addr, sizeof(Addr)) == SOCKET_ERROR)
		showError("바인딩 실패");

	if (listen(server, SOMAXCONN) == SOCKET_ERROR)
		showError("듣기 실패");

	cout << "클라이언트 기다리는 중...\n";

	SOCKADDR_IN ClientAddr = {};
	int CSize = sizeof(ClientAddr);
	SOCKET TClient = accept(server, (SOCKADDR*)&ClientAddr, &CSize);

	char Rc[1024] = {};
	recv(TClient, Rc, 1024, 0);
	cout << "Recv Msg : " << Rc << "\n";

	char Sd[] = "Server Send";
	send(TClient, Sd, strlen(Sd), 0);

	closesocket(TClient);
	closesocket(server);

	WSACleanup();
	//WSACleanup = Startup 에서 지정된 소켓 지워준다 = free와 같은 개념
	return 0;
}