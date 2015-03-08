package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.annotation.*;


@Repeatable(ColumnMapperContainer.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnMapper {
	public static final String invalidString = "invalid string";
	
	String column();
	String columnEntityGetter() default invalidString;
	String columnEntitySetter() default invalidString;
}
