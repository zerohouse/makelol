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
		Setting setting = new Setting(new MappingSetting("localhost:8080", "me.controllers", "/WEB-INF/jsp/", "/api/*", "/user/*"), new DatabaseSetting(
				"me.model", "me.model.test", "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8", "root", ""));
		Setting.set(setting);
	}
}
