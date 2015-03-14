package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AIPKColMapper {
	String column();
	String columnEntityGetter() default AnnotationsUtils.invalidString;
	String columnEntitySetter() default AnnotationsUtils.invalidString;
}
