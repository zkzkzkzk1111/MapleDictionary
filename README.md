# MapleDictionary
이 코드는 Jetpack Compose UI + Clean Architecture + MVVM + Hilt 사용을 위한 연습 진행중인 앱 입니다.
<br><br>

## Clean Architecture Module

app → presentation → domain ← data ← remote

MyApplication
│
├── app                       
│   
├── presentation              # UI Layer
│   ├── manifests             
│   └── kotlin+java         
│       └── com.kmj           
│           └── ...           # UI 
│
├── domain                    # Business Logic Layer
│   ├── manifests            
│   └── kotlin+java
│       └── com.kmj.domain
│           ├── model         
│           ├── repository    
│           ├── usecase       
│           └── util         
│
├── data                      # Data Layer
│   ├── manifests             
│   └── kotlin+java
│       └── com.kmj.data
│           ├── bound         # Data binding
│           ├── di           
│           ├── model        
│           ├── remote      
│           ├── repository    
│           └── DataMapper.kt 
│
└── remote                    # Network Layer
├── manifests            
└── kotlin+java
└── com.kmj.remote
├── api           
├── di           
├── impl         
└── RemoteMapper  
<br><br>

## TODO

- Monster Map 연동
- NPC API 연동
- NPC MAP 연동
- API 속도 개선