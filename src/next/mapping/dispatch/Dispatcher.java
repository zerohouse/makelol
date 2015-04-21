package next.mapping.dispatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.maker.PackageCreator;
import next.mapping.http.HttpImpl;
import next.setting.Setting;

//@MultipartConfig(location = "webapp/uploads", maxFileSize = 1024 * 1024 * 10, fileSizeThreshold = 1024 * 1024, maxRequestSize = 1024 * 1024 * 20)
public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;
	private Mapper mapper;

	@Override
	public void init() throws ServletException {
		mapper = new Mapper();
		databseSetting();
	}

	private void databseSetting() {
		boolean create = Boolean.parseBoolean(Setting.getString("database", "createTablesOnServerStart"));
		boolean reset = Boolean.parseBoolean(Setting.getString("database", "resetTablesOnServerStart"));
		if (!(create || reset))
			return;
		PackageCreator.createTable(reset, Setting.getString("database", "modelPath"));
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		mapper.execute(new UriKey(req.getMethod(), req.getRequestURI()), new HttpImpl(req, resp));
	}

}