package me.june.demowebmvc;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * org.springframework.validation.Validator을 구현 또는
 * Bean으로 등록하여 사용가능하다. Bean으로 등록하여 사용할경우, Validator Interface를 Implements할 필요가없음.
 */
public class EventValidator implements Validator {

    /**
     * Validate for Event.class setting
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.isAssignableFrom(aClass);
    }

    /**
     * Implemetation for validate
     *
     * @param o
     * @param errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        Event event = (Event) o;

        if(StringUtils.isEmpty(event.getName())) {
            errors.rejectValue("name","errorCode","hey");
        }
    }
}
