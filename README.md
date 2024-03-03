# 📌 COIN / 코인

> COMMON INTEREST, 공통 관심사를 찾아주는 빙고 게임
- Link: [COIN APP LINK](https://coin-bingo.vercel.app/)

# 🏛️ Project Architecture / 프로젝트 아키텍쳐

- 그림들


# 🗂️ Project Structure / 프로젝트 구조

```java
├── Dockerfile
├── README.md
├── build.gradle
├── docker-compose.yml
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── nginx.conf
├── scripts
│   ├── build-back.bat
│   ├── build-back.sh
│   ├── build-front.bat
│   └── build-front.sh
├── settings.gradle
└── src
    ├── main
    │   ├── generated
    │   ├── java
    │   │   └── com
    │   │       └── maps
    │   │           └── coin
    │   │               ├── CoinApplication.java
    │   │               ├── config
    │   │               │   ├── SwaggerConfig.java
    │   │               │   ├── WebMvcConfig.java
    │   │               │   └── WebSocketConfig.java
    │   │               ├── controller
    │   │               │   ├── GameController.java
    │   │               │   ├── ReadyController.java
    │   │               │   ├── RoomController.java
    │   │               │   └── StompController.java
    │   │               ├── domain
    │   │               │   ├── BaseEntity.java
    │   │               │   ├── question
    │   │               │   │   └── Question.java
    │   │               │   ├── room
    │   │               │   │   ├── Problem.java
    │   │               │   │   └── Room.java
    │   │               │   └── user
    │   │               │       ├── Answer.java
    │   │               │       ├── Board.java
    │   │               │       └── Gamer.java
    │   │               ├── dto
    │   │               │   ├── answer
    │   │               │   │   ├── AnswerBoardIndex.java
    │   │               │   │   ├── AnswerListRequest.java
    │   │               │   │   ├── AnswerRequest.java
    │   │               │   │   └── AnswerResponse.java
    │   │               │   ├── avatar
    │   │               │   │   ├── AvatarRequest.java
    │   │               │   │   └── AvatarResponse.java
    │   │               │   ├── room
    │   │               │   │   ├── CreateRoomRequest.java
    │   │               │   │   ├── CreateRoomResponse.java
    │   │               │   │   └── RoomInfoResponse.java
    │   │               │   └── user
    │   │               │       ├── GamerInfoResponse.java
    │   │               │       ├── GamerRequest.java
    │   │               │       ├── GamerResponse.java
    │   │               │       └── SameAnswerGamerListResponse.java
    │   │               ├── handler
    │   │               │   └── WebSocketHandler.java
    │   │               ├── repository
    │   │               │   ├── AnswerRepository.java
    │   │               │   ├── GamerRepository.java
    │   │               │   ├── ProblemRepository.java
    │   │               │   ├── QuestionRepository.java
    │   │               │   └── RoomRepository.java
    │   │               └── service
    │   │                   ├── AvatarService.java
    │   │                   ├── GameService.java
    │   │                   ├── GamerService.java
    │   │                   ├── RoomService.java
    │   │                   └── SessionService.java
    │   └── resources
    │       ├── application.yml
    │       └── data.sql
    └── test
        └── java
            └── com
                └── maps
                    └── coin
                        └── CoinApplicationTests.java

```

# ⚙️ Dependencies / 의존성
- Dependencies
```java
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-websocket'
	implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'org.postgresql:postgresql'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
- Usage
```java
// If you use MacOS or Linux
sh ./scripts/build-front.sh

// If you use Windows
./scripts/build-front.bat
```

# 🧑‍💻 Built With / 누구랑 만들었나요?
<br/>
<div align="center">
<table>
    <thead>
        <tr>
            <th colspan="2">Back</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td align="center"><a href="https://github.com/songyeongeun">송영은</a></td>
            <td align="center"><a href="https://github.com/minseok-oh">오민석</a></td>
        </tr>
        <tr>
            <td><a href="https://github.com/songyeongeun"><img src="https://avatars.githubusercontent.com/u/107869024?v=4" width="150px" height="150px"/></a></td>
            <td><a href="https://github.com/minseok-oh"><img src="https://avatars.githubusercontent.com/u/68336833?v=4" width="150px" height="150px"/></a></td>
        </tr>
    </tbody>
</table>
</div>
<br/>
