package next.mapping.annotation.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SessionAttribute {
	String value();
	
	boolean require() default true;
}
