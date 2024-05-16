# 장애 알림 서비스  

## 기능
1. 사용자 알림 그룹을 생성하고, 원하는 사용자를 알림 그룹에 참여시킬 수 있습니다.  
2. 사용자 이름/그룹 이름/와일드 카드(@all)를 사용하여 서버 장애에 대한 알림을 보낼 수 있습니다.  
3. 알림을 처리하는 외부 서버가 한 번에 처리할 수 있는 메시지 건수가 N일 때,  
   N건을 하나의 이벤트로 발행하여 한 번에 N건씩 처리할 수 있게 합니다.
5. 알림을 처리하는 외부 서버의 장애로 이벤트를 처리하지 못하는 경우에는
   별도의 대기열에 추가하여 될때까지 재시도 합니다. (목표)

## 버전
- JAVA 17  
- SpringBoot 3.2.5  
- Docker 23.0.5  
- Docker Compose 2.17.3

## 사전 요구사항
- git
- docker-compose

## 실행 방법
```shell
~$ docker-compose up
```

## 패키지 구조
```bash
app                         
├─data                         // 데이터 관련 코드                                                                           
│  ├─entity                    // ├ 엔티티                                                                
│  ├─enums                     // ├ 이넘                                                                 
│  └─repository                // └ 엔티티 Repository                                                                 
├─domain                       // API 도메인                                                                
│  └─domain1                   // 도메인별 폴더 구분                                                                 
│     ├─controller             // ├ 컨트롤러                                                                         
│     ├─dto                    // ├ DTO                                                                
│     └─service                // └ 서비스                                                                                        
├─global                       // 프로젝트 전체에 해당하는 코드                                                                 
│  ├─config                    // 설정                                                                 
│  ├─error                     // 오류 처리                                                                
│  │  └─exception              // └ custom exception 클래스, 에러코드 클래스                                                                     
│  └─response                  // 응답                                                                 
│   ├── DataResponse.java      // ├ 정상 처리 시 응답 형태
│   └── ErrorResponse.java     // └ 오류 발생 시 응답 형태
└─kafka                        // 카프카 클라이언트로 기능하기 위한 코드                                                              
   ├─Consumer.java             // ├ 컨슈머                                                                      
   └─Producer.java             // └ 프로듀서     
```
