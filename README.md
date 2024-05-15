# 장애 알림 서비스  

## 버전
JAVA 17
SpringBoot 3.2.5
Docker 23.0.5  
Docker Compose 2.17.3

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
