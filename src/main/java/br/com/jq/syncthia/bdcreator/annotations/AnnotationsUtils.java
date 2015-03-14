package br.com.jq.syncthia.bdcreator.annotations;

public class AnnotationsUtils {
	public static final String invalidString = "invalid string";
	
	public static String generateGenericIfInvalidName(String columnEntityMethod, String columnName, String preffix) {
		if (columnEntityMethod.equals(AnnotationsUtils.invalidString)) {
			StringBuilder methodNameBuilder = new StringBuilder(preffix);
			for (String part: columnName.split("_")) {
				methodNameBuilder.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
			}
			
			return methodNameBuilder.toString();
		} else {
			return columnEntityMethod;
		}
	}
	
	public static String generateSetterName(String columnEntitySetter, String columnName) {
		return generateGenericIfInvalidName(columnEntitySetter, columnName, "set");
	}

	public static String generateGetterName(String columnEntityGetter, String columnName) {
		return generateGenericIfInvalidName(columnEntityGetter, columnName, "get");
	}
}
