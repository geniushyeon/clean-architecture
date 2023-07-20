# clean-architecture
클린 아키텍처 스터디 레포

## 패키지 구조
- `module-app`: 애플리케이션 로직들이 들어있는 모듈
  - config 
  - controller
  - dto
  - service
- `module-common`: 애플리케이션 모듈이 늘어나더라도 공통으로 사용할 수 있는 모듈
  - entity
  - exception
  - repository
```text
├── module-app
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── geniushyeon
│       │   │           └── architecture
│       │   │               ├── CleanArchitectureApplication.java
│       │   │               ├── config
│       │   │               ├── controller
│       │   │               ├── dto
│       │   │               │   ├── request
│       │   │               │   └── response
│       │   │               └── service
│       │   └── resources
│       │       └── application.yaml
│       └── test
│           ├── java
│           └── resources
├── module-common
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── geniushyeon
│       │   │           └── architecture
│       │   │               ├── entity
│       │   │               ├── exception
│       │   │               └── repository
│       │   └── resources
│       └── test
│           ├── java
│           └── resources
└── build.gradle
└── settings.gradle
```