# 장애 알림 서비스 


&nbsp;
## Readme 변경 이력

| 일자     | 변경사항       | 작성자 |
|------------|------------|-----|
| 2024.05.19 | 최초 작성      | 정우진 |


&nbsp;   
## API 명세
[API 스웨거 명세](https://woojoovove.github.io/swagger/)

&nbsp;   
## 기능
1. 사용자 알림 그룹을 생성하고, 원하는 사용자를 알림 그룹에 참여시킬 수 있습니다.  
2. 사용자 이름/그룹 이름/와일드 카드(@all)를 사용하여 서버 장애에 대한 알림을 보낼 수 있습니다.  
3. 알림을 처리하는 외부 서버가 한 번에 처리할 수 있는 메시지 건수가 N일 때,  
   N건을 하나의 이벤트로 발행하여 한 번에 N건씩 처리할 수 있게 합니다.
5. (예정) 알림을 처리하는 외부 서버의 장애로 이벤트를 처리하지 못하는 경우에는
   별도의 대기열에 추가하여 될때까지 재시도 합니다. 

   
&nbsp;
## 소프트웨어 아키텍처
1. 본 앱의 클라이언트가 메시지 발송을 요청하면 본 앱은 이를 `이벤트 브로커(Kafka)`에 발행합니다.  
2. `이벤트 브로커(Kafka)`는 이벤트를 비동기적으로 처리할 수 있게 도와줍니다(비동기 통신).  
   비동기 통신으로 인해 이벤트 `Producer`는 `Consumer`의 가용성과 관계 없이 이벤트를 발행할 수 있습니다.  
   `Consumer`는 가능할 때 이벤트를 처리하면되므로 이벤트 발생과 무관하게 메인 업무를 수행할 수 있습니다 (`Consumer`의 가용성 증대).  
   `이벤트 브로커(Kafka)`는 이벤트를 보존하고 재큐잉할 수 있으므로 외부 이벤트 처리기의 장애가 시스템 장애로 전파되지 않습니다(장애 전파 방지).  

![dev](https://github.com/woojoovove/app/assets/47964928/b62776de-080a-4f65-9dbb-91b6d08869c5) | ![prd](https://github.com/woojoovove/app/assets/47964928/92752807-4ac7-4c8f-9e04-86cfda01b9f4)
---|---|  


&nbsp;
## 테이블 스키마
![app](https://github.com/woojoovove/app/assets/47964928/71c6b1c8-4a8c-4800-bd71-a634ee703ad4)


&nbsp;
## 버전
- JAVA 17  
- SpringBoot 3.2.5
- H2 2.2.224  
- Docker 23.0.5  
- Docker Compose 2.17.3
- kafka 3.2.3
- zookeeper 3.4.10
   
&nbsp;
## 사전 요구사항
- git
- docker-compose
   
&nbsp;
## 용도별 실행 방법
**개발용**  : 스프링을 로컬 머신에 띄울 때

```shell
user@hostname:/project/root/folder$ docker-compose -f docker-compose.yml up -d
user@hostname:/project/root/folder$ ./gradlew bootRun --args='--spring.profiles.active=dev'
```

**배포용**  : 스프링을 docker-compose에 포함시켜 띄울 때
```shell
user@hostname:/project/root/folder$ docker-compose -f docker-compose-docker.yml up -d
```
   
&nbsp;
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
