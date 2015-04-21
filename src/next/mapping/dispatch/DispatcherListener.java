package next.mapping.dispatch;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import next.setting.Setting;
import next.setting.jobject.JArray;

@WebListener
public class DispatcherListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		Object mapping = Setting.get("mapping");
		ServletRegistration.Dynamic dispatcher = sc.addServlet("Dispatcher", "next.mapping.dispatch.Dispatcher");
		dispatcher.setLoadOnStartup(1);
		if (mapping.getClass().equals(String.class)) {
			dispatcher.addMapping(mapping.toString());
			return;
		}

		if (mapping.getClass().equals(JArray.class)) {
			List<Object> array = ((JArray) mapping).getChilds();
			array.forEach(each -> {
				dispatcher.addMapping(each.toString());
			});
			return;
		}

		// FilterRegistration fr = sc.addFilter("CViewerFilter",
		// "org.apache.geronimo.samples.javaee6.cviewer.CviewerFilter");
		// fr.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),
		// true, "ClassViewer");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
