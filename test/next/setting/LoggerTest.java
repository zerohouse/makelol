package next.setting;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import next.mapping.annotation.parameters.JsonParameter;

import org.junit.Test;

public class LoggerTest {
	
	@Test
	public void substring(){
		String abc = "setABCDE";
		
	 System.out.println(subString(abc));
	}
	
	private static Object subString(String method) {
		String start = method.substring(3, 4).toLowerCase();
		return start + method.substring(4, method.length());
	}

	public void test2(@JsonParameter(value = "") String a, String b) {

	}

	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		Method m = this.getClass().getMethod("test2", String.class, String.class);
		Parameter[] ss = m.getParameters();

		boolean s3 = ss[0].isAnnotationPresent(JsonParameter.class);
		System.out.println(ss[0].getClass().getDeclaringClass().getSimpleName());
		System.out.println(ss.toString());
		String s = getClass().getName();
		int i = s.lastIndexOf(".");
		if (i > -1)
			s = s.substring(i + 1);
		s = s + ".class";
		System.out.println("name " + s);
		Object testPath = this.getClass().getResource(s);
		System.out.println(testPath);
		// Logger foo = (Logger) LoggerUtil.getLogger(LoggerTest.class);
		// foo.info("test");
		// foo.info("test");
		// foo.info("test");
	}
}
