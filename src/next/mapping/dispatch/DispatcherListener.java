package next.mapping.dispatch;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import next.setting.Setting;

@WebListener
public class DispatcherListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		ServletRegistration sr = sc.addServlet("Dispatcher", "next.mapping.dispatch.Dispatcher");
		String a = Setting.get("mapping");
		
		sr.addMapping(a);
		System.out.println(a + "maaping");
		// FilterRegistration fr = sc.addFilter("CViewerFilter",
		// "org.apache.geronimo.samples.javaee6.cviewer.CviewerFilter");
		// fr.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),
		// true, "ClassViewer");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
