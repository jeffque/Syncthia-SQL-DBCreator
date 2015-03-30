package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.annotation.*;

@Repeatable(ColumnMapperContainer.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnMapper {
	String column();
	String columnEntityGetter() default AnnotationsUtils.invalidString;
	String columnEntitySetter() default AnnotationsUtils.invalidString;
}
