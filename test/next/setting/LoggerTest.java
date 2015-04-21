package next.setting;

import org.junit.Test;

import ch.qos.logback.classic.Logger;

public class LoggerTest {

	@Test
	public void test() {
		
		String s = getClass().getName();
	    int i = s.lastIndexOf(".");
	    if(i > -1) s = s.substring(i + 1);
	    s = s + ".class";
	    System.out.println("name " +s);
	    Object testPath = this.getClass().getResource(s);
	    System.out.println(testPath);
//		Logger foo = (Logger) LoggerUtil.getLogger(LoggerTest.class);
//        foo.info("test");
//        foo.info("test");
//        foo.info("test");
	}
	
	

}
