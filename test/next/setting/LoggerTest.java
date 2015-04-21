package next.setting;

import org.junit.Test;

import ch.qos.logback.classic.Logger;

public class LoggerTest {

	@Test
	public void test() {
		Logger foo = (Logger) LoggerUtil.getLogger(LoggerTest.class);
        foo.info("test");
        foo.info("test");
        foo.info("test");
	}
	
	

}
