1. 템플릿 엔진을 바꿀 때 서버재기동 없이 바로 적용 하는 방법
 1-1. spring-boot-devtools 디팬던시 추가
     - implementation 'org.springframework.boot:spring-boot-devtools'
      => 개발 보조 기능(캐시를 제거해서 리로딩 가능)
    
 1-2. 템플릿 엔진 수정 후 recompile하면 바로 적용