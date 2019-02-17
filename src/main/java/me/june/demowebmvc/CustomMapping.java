package me.june.demowebmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta 애노테이션
 * 애노테이션 위에 사용할수 있는 애노테이션.
 * ex RequestMapping , Retention Document 등 ..
 * info) 스프링에서 제공하는 대부분의 애노테이션들은 메타애노테이션으로 이용가능함.
 *
 * Composed 애노테이션
 * 여러개의 메타 애노테이션을 조합하여 사용하는 애노테이션.
 *
 * @Rentention
 *
 * 해당 어노테이션의 생명주기 (해당 어노테이션의 정보를 언제까지 유지할것인가.)를 표기할때 사용한다.
 *
 * 1. RententionPolicy.CLASS
 *  - 컴파일시 까지 유지되며 (.class파일까지 정보가 유지되지만), 런타임시에는 정보가 사라진다.
 *
 * 2. RetentionPolicy.RUNTIME
 *  - 런타임시까지 정보를 유지한다.
 *
 * 3. RententionPolicy.SOURCE
 *  - 소스코드 까지만 유지하고 , 컴파일시 사라진다.
 *
 * 기본값은 .CLASS
 *
 * @Target
 *  - 해당 애노테이션을 어디에 적용할것인지 표시
 *
 * @Document
 *  - 해당 애노테이션을 사용한 코드에 DOC 정보를 남긴다.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequestMapping(value = "/custom",method = RequestMethod.GET)
public @interface CustomMapping {
}
