package next.mapping.dispatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.maker.PackageCreator;
import next.mapping.http.HttpImpl;
import next.setting.Setting;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

//@MultipartConfig(location = "webapp/uploads", maxFileSize = 1024 * 1024 * 10, fileSizeThreshold = 1024 * 1024, maxRequestSize = 1024 * 1024 * 20)
public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;
	private Mapper mapper;

	@Override
	public void init() throws ServletException {
		mapper = new Mapper();
		loggerSetting();
		databseSetting();
	}

	private void databseSetting() {
		boolean create = Boolean.parseBoolean(Setting.getString("database", "createTablesOnServerStart"));
		boolean reset = Boolean.parseBoolean(Setting.getString("database", "resetTablesOnServerStart"));
		if (!(create || reset))
			return;
		PackageCreator.createTable(reset, Setting.getString("database", "modelPath"));
	}

	private void loggerSetting() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		fileSetting(root, lc, ple);
		levelSetting(root);
		ple.setPattern(Setting.getString("logger", "pattern"));
		ple.setContext(lc);
		ple.start();
	}

	private void fileSetting(Logger root, LoggerContext lc, PatternLayoutEncoder ple) {
		String file = Setting.getString("logger", "file");
		if (file == null)
			return;
		if (file == "")
			return;
		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setFile(file);
		fileAppender.setEncoder(ple);
		fileAppender.setContext(lc);
		fileAppender.start();
		root.addAppender(fileAppender);
	}

	private void levelSetting(Logger root) {
		Level lv;
		switch (Setting.getString("logger", "level")) {
		case "OFF":
			lv = Level.OFF;
			break;
		case "INFO":
			lv = Level.INFO;
			break;
		case "DEBUG":
			lv = Level.DEBUG;
			break;
		case "ERROR":
			lv = Level.ERROR;
			break;
		case "TRACE":
			lv = Level.TRACE;
			break;
		default:
			lv = Level.ALL;
			break;
		}
		root.setLevel(lv);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		mapper.execute(new UriKey(req.getMethod(), req.getRequestURI()), new HttpImpl(req, resp));
	}

}