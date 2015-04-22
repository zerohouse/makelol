package next.setting;

import next.util.LoggerUtil;

import org.junit.Test;
import org.slf4j.Logger;

public class LoggerTest {

	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		Logger foo = (Logger) LoggerUtil.getLogger(LoggerTest.class);
		foo.info("test");
		foo.info("test");
		foo.info("test");
	}
}
