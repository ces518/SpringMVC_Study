package me.june.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class SimpleController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    /**
     * uri pattern mapping
     *
     * uri?  uri 뒤에 1글자가 와야한다.
     *
     * uri* uri 뒤에 여러글자가온다.
     *
     * uri/* uri 뒤에 1개의 path만 매핑
     *
     * uri/** uri뒤에 여러개의 path매핑
     *
     * {name: 정규식} 으로사용하면 pathvariable로 받아올 문자를 정규식으로 체크할 수 있다.
     *
     * 다음과 같이 uri에 매핑정보가 중복되는경우 1번과 같이 좀더 명확한 매핑을 가진 핸들러가 우선순위를 가진다.
     *
     * 1. /june
     * 2. /**
     *
     * uri 확장자 패턴 지원 (Spring Boot 는 기본적으로 off)
     * RFD Attack 방지 (Reflected File Download) > Spring4.3 이후로는 해당 이슈가없지만, 4.1 , 4.2 .. 등 하위버전에서는 해당공격이 가능했었음.
     * /june 으로 매핑하면 /june.* 의 확장자패턴 매핑을 자동으로 설정해준다.
     *
     * 확장자 패턴은 최근에는 걷어내는 추세임..
     * 다양한 타입의 데이터를 제공해야한다면 contentsType 에 싣는것을 가장 먼저 고려해볼것.
     * 차선책으로는 파라메터에 싣는것을 권장한다.
     * @return
     */
    @GetMapping("/uri/{name:[a-z]}")
    @ResponseBody
    public String helloUri(@PathVariable String name){
        return "hello " + name;
    }

    /**
     * MediaType으로 매핑
     *
     * RequestMapping의 attributes중
     * 1. consumes 에 contentType을 명시해주면 해당 contentType의 요청만받아 오도록 매핑할수있다.
     * org.springframework.http.MediaType class 의 상수를 사용하는것이 좋다. **_VALUE는 문자열을 리턴, ** 는 MediaType을 리턴한다.
     * > 매치되지 않는경우 415 error code Not Supported Media Type
     *
     * 2. produces 에 ContentType을 명시해주시면 해당 contentType의 응답만 처리하도록 매핑할수있다 (accept Header정보 참조)
     * > 매치되지 않는경우 406 error code Not Supported
     * 하지만 .. 약간 오묘한 점이있음
     * accept 헤더에 아무런 정보도 없다면 , 아무런 타입으로 응답을 받겠다는 의미이기 때문에 매핑이된다.
     *
     * * Class Level에 설정한 정보와 조합되지않고
     *   Method Level에 설정한 정보로 overwrite된다.
     * @return
     */
    @RequestMapping(value = "/media"
            ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            ,produces = MediaType.TEXT_PLAIN_VALUE
                    )
    @ResponseBody
    public String media(){
        return "json";
    }


    /**
     * header의 정보를 이용하여 매핑이 가능하다.
     *
     * 1. header = "key" 해당키가 있을경우 매핑
     * 2. header = "!key" 해당키가 없을경우 매핑
     * 3. header = "key=value" 해당 키와 벨류가있을경우 매핑
     *
     * parameter 정보를 이용한 매핑
     *
     * 헤더와 동일한 방식이며 params 속성을 이용.
     * @return
     */
    @RequestMapping(value = "/header", headers = HttpHeaders.FROM)
    @ResponseBody
    public String header(){
        return "header";
    }


    /**
     * Http Method를 이용한 매핑.
     *
     * HEAD = get요청으로 보내지만 , body에 본문을 보내지않고 헤더정보만 받아온다.
     * (해당 리소스가 요청을 잘 처리하고 응답을 하는지 확인하는 의미 )
     *
     * OPTIONS = 해당 리소스가 어떠한 Method들을 지원하는지 받아온다.
     *
     * 위 기능들은 Spring MVC를 사용한다면 기본적으로 구현이 되어있다.
     * @return
     */
    @GetMapping("/head")
    @ResponseBody
    public String head(){
        return "head";
    }

    @PostMapping("/head")
    @ResponseBody
    public String heads(){
        return "heads";
    }


    /**
     * @PathVariable
     * 요청 URI의 일부를 핸들러 메서드의 아규먼트로 받는방법.
     * 타입컨버전 지원
     * 값이 반드시 있어야함
     * Optional을 지원함.
     *
     * @MatrixVariable
     * 요청 URI패턴에서 키=값 쌍의 데이터를 핸들러 메서드의 아규먼트로 받는방법.
     * 타입컨버전 지원
     * 값이 있어야한다.
     * Optional지원
     * 기본적으로 설정이 되어있지않으며 추가 설정을해야만 사용할 수 있다.
     *
     *
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/events/{id}")
    @ResponseBody
    public Event getEvents(
            @PathVariable Integer id
            ,@MatrixVariable String name
    ){
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        return event;
    }


    /**
     * 요청 매개변수
     *
     * @RequestParam
     * queryString 또는 form data를 요청 매개변수라고한다.
     * 요청 매개변수에 있는 단순 타입 데이터를 메서드의 아규먼트로 받아올수 있다.
     * 값이 반드시 있어야한다.
     *  - required=false , Optional로 대체가능.
     * Optional 을 지원한다.
     * String 이 아닌 데이터타입은 타입 컨버전을 지원한다.
     * 생략이 가능하다.
     * Map<String,String>
     * MultiValueMap<String,String>을 사용하여 모든 매개변수들을 받아올수 있다.
     * @param name
     * @return
     */
    @PostMapping("/events")
    @ResponseBody
    public Event createEvents(
            @RequestParam String name
    ){
        Event event = new Event();
        event.setName(name);
        return event;
    }

    @GetMapping("/events/form")
    public String eventsForm(
            Model model
    ){
        model.addAttribute("event",new Event());
        return "events/form";
    }


    /**
     * @ModelAttribute
     * @RequestParam이 단일타입이라면 , ModelAttribute는 컴포짓타입이다.
     * 여러개의 요청매개변수를 하나의 object로 받을수있다.
     * 해당 객체를 새로만들때 사용할수 있다.
     * 타입커버전 지원
     * @Valid(JSR-303), @Validated(Spring) 을 사용하여 바인딩후 , 검증도 가능하다.
     *
     * 바인딩 에러나 , 검증 에러가 발생한뒤 핸들러메서드에서 처리하고싶다면,
     * 해당 컴포짓 객체 바로 우측에다가 BindingResult를 아규먼트로 받아, 해당 처리 결과를 핸들링 할수있다.
     *
     * 생략이 가능하다.
     *
     *
     * @Valid 는 group을 사용할 수 없다.
     * @Validated는 사용이가능
     *
     * @param event
     * @return
     */
    @PostMapping("/events/create")
    public String create(
            @Validated @ModelAttribute Event event
            , BindingResult bindingResult
    ){

        if(bindingResult.hasErrors()){
            return "events/form";
        }

        return "redirect:/events";
    }

    @GetMapping("/events")
    public String events(){
        return "events/list";
    }

}
