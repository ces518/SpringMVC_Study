## HTTP Method 

##### 1. GET
- 클라이언트가 리소스를 요청할때 사용하낟.
- 캐싱이 가능하다 . (조건적인 GET이 가능, HEADER정보에 의해 .. NOT MODIFIED등 의 응답으로 인해 BODY로 보내지않고 CACHING된 정보를 보낼수있다.)
- 브라우저 기록에 남는다.
- 북마크가 가능하다.
- 민감한 데이터를 보낼때 사용하지맑ㅅ.
- idempoent = 동일한 요청에는 동일한 응답을 해야한다.


##### 2. POST
- 클라이언트가 서버의 리소스를 새로 생성하거나 수정할때 사용한다.
- 서버에 보내는 데이터를 POST요청 본문에 보낸다.
- 캐싱이 불가능하다.
- 부라우저 기록에 남지않는다.
- 북마크 할 수 없다.
- 데이터 길이 제한이없다.

##### 3. PUT
- URI에 해당하는 데이터를 새로 생성하거나, 수정할때 사용한다.
- POST와 차이점은 URI의 의미가 다르다.

 POST : 보내는 데이터를 처리할 리소스를 지칭한다.
 
 PUT : 보내는 데이터에 해당하는 리소스를 지칭한다.

- idempoent 

##### 4. PATCH 
- put과 비슷하지만 , 기존 엔티티와 수정할 엔티티의 차이점만 보낸다.
- 리소스의 일부분만 수정하고 싶은경우 사용한다.
- idempoent

##### 5. DELETE
- 리소스를 삭제할때 사용한다.
- idempoent



## URI Pattern Mapping 
- /uri?  : uri 뒤에 1글자가 와야한다.
- /uri*  : uri 뒤에 여러글자가 올수있다.
- /uri/* : uri 뒤에 1개의 path만 매핑한다.
- /uri/**: uri 뒤에 여러개의 path매핑
- {name: 정규식} : pathvariable로 받아올 문자를 정규식 체크가 가능하다.

- 다음과 같이 uri 매핑정보가 중복되는경우 , 1번과 같이 명확한 매핑을 가진 핸들러가 우선순위를 가진다.
- 1./june
- 2./**

- URI 확장자 패턴 지원 (SpringBoot는 기본적으로 off)
- RDF Attack (Reflected File Download) > Spring4.3 이전버전에는 해당 이슈가 존재함.
- /june 으로 매핑하면 /june.* 의 확장자 패턴 매핑을 자동으로 설정해준다.
- ps . 확장자패턴은 최근에는 걷어내는 추세이다.. 다양한 타입의 데이터를 제공해야한다면 , contentType에 싣는것을 가장 먼저 고려해볼것.
- 차선책으로는 파라메터에 싣는것을 권장한다.

```
    @GetMapping("/uri/{name:[a-z]}")
    @ResponseBody
    public String helloUri(@PathVariable String name){
        return "hello " + name;
    }
```


## MediaType Mapping 

- RequestMapping 의 attributes 중..
- 1. consumes에 contentsType을 명시해주면 , 해당 contentType의 요청만 받아 오도록 매핑할 수 있다.
org.springframework.http.MediaType.class의 상수를 사용하는것이좋다.
- **_VALUE는 문자열을 리턴, ** 는 MediaType을 리턴한다.
- 매치되지 않는경우 415 error code (Not Supported Media Type) 

- 2. produces 에 contentType을 명시해주면 해당 contentType의 응답만 처리하도록 매핑할 수 있다. (accept Header정보를 참조함)
- 약간 오묘한 점이있다... accept 헤더에 아무런 정보가 없다면 , 아무런 타입으로 응답을 받겠다는 의미이기 때문에.. 매핑이된다 .... ?! 
- * ClassLEVEL에도 설정이 가능한데.. ClassLEVEL에 설정한 정보와 조합되지않고, MethodLEVEL에 설정한 정보로 overwrite 된다.
```
    @RequestMapping(value = "/media"
            ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            ,produces = MediaType.TEXT_PLAIN_VALUE
                    )
    @ResponseBody
    public String media(){
        return "json";
    }
```

