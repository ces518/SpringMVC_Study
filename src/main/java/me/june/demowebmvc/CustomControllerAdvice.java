package me.june.demowebmvc;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @ControllerAdvice
 *
 * @ModelAttribute , @InitBinder, @ExceptionHandler ...
 * 등 여러가지 설정을 전역으로 설정하고싶을때 사용할 수 있다.
 *
 *
 * 특정 클래스, 패키지 등에만 설정하고싶을때 ,
 * 범위를 설정할수있다.
 *
 * @RestControllerAdvice
 * > ControllerAdvice와 동일하지만 ,
 * 모든 응답에 @ResponseBody가 붙는것과 동일하다.
 *
 */
@ControllerAdvice
public class CustomControllerAdvice {

    @InitBinder
    public void customBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new EventValidator());
    }
}
