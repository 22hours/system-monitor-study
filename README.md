# **system-monitor-study**
:computer: Catholic Univ PC Remote Management System. v3.0.0<br>
<br><br>
# Architecture
![image](https://user-images.githubusercontent.com/16419202/71482833-01bad800-2848-11ea-92d8-d36137462694.png)


# Process
1. **전체적인 기획부터 잡는 것이 아니라, 추상적인 방향성을 가지고,**<br> 
모듈단위에서부터 개발하자<br>

* **추상적인 방향성 : 다솔관 PC 관리 에이전트**<br>

2. **기획(모듈단위)**<br>
-> 기술조사 <br>
-> 기술연구 <br>
-> poc작성 <br>
-> 모듈개발 <br>
-> UI 명세 <br>
-> 모듈화 <br>
-> 제품에 포함<br>

<br><br>

# How-to
1. Kanban board:+1: **거시적인** 프로젝트의 흐름과 **모듈별** 개발과정을 한 눈에 보여줌 
2. Issues : **의견공유** 및 **개진**을 위해 소통수단으로 사용중.
3. Pull Request : 
   -  `Merging`이랑 `Pull Rueset` 구분
   - `Pull Request` -> `Code Review`
4. Milestone : Grouping `Issues`
5. Branch : `Issue` 별로 생성
6. Slack으로 공유

# Repo
- `~/dev` : 개발의 뼈대
- `~/exercise` : 개발에 필요한 예제 및 연습
- `~/module` : 뼈대에 포함되는 모듈

# Reference

##### -----Win32API

1. [Win32API 관련 주요 자료형](https://jongwuner.github.io/2019/09/08/Win32API-주요-자료형/)

##### -----Registry

1. [레지스트리 개념과 키(Key)의 열기/닫기](https://jongwuner.github.io/2019/09/08/레지스트리-키의-열기,닫기/)
2. [레지스트리 키(Key)의 조회/생성/삭제](https://jongwuner.github.io/2019/10/03/레지스트리-키의-조회,생성,삭제/)
3. [레지스트리 값(Value)의 조회/생성/삭제 (1)](https://jongwuner.github.io/2019/10/03/레지스트리-기본값의-조회,수정/)
4. [레지스트리 값(Value)의 조회/생성/삭제 (2)](https://jongwuner.github.io/2019/10/03/레지스트리-기본값의-조회,수정/)

##### -----PDH(Performance Data Helper) 

1. [PDH함수 활용하여 InitCPUUsage(), GetCpuUsage() 제작](https://github.com/22hours/system-monitor-study/blob/master/module/CPU/CpuData.cpp)

