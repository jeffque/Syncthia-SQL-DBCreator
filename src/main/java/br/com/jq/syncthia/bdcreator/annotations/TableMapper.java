package br.com.jq.syncthia.bdcreator.annotations;

import java.lang.annotation.*;

import br.com.jq.syncthia.bdcreator.table.Table;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableMapper {
	Class<? extends Table> table();
	String uniqueKeyUsed() default "PRIMARY_KEY_CONSTRAINT";
}
