package next.mapping.dispatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.DAO;
import next.mapping.annotation.parameters.JsonParameter;
import next.mapping.annotation.parameters.Parameter;
import next.mapping.annotation.parameters.SessionAttribute;
import next.mapping.exception.HandleException;
import next.mapping.http.Http;

public class MethodHolder {

	private Object instance;
	private Method method;

	public MethodHolder(Object instance, Method method) {
		this.method = method;
		this.instance = instance;
	}

	public Method getMethod() {
		return method;
	}

	public Object execute(Http http, DAO dao) {
		try {
			Class<?>[] types = method.getParameterTypes();
			java.lang.reflect.Parameter[] obj = method.getParameters();
			List<Object> parameterArray = new ArrayList<Object>();
			for (int i = 0; i < obj.length; i++) {
				if (types[i].equals(Http.class)) {
					parameterArray.add(http);
					continue;
				}
				if (types[i].equals(DAO.class)) {
					parameterArray.add(dao);
					continue;
				}
				if (types[i].equals(HttpServletRequest.class)) {
					parameterArray.add(http.getReq());
					continue;
				}
				if (types[i].equals(HttpServletResponse.class)) {
					parameterArray.add(http.getResp());
					continue;
				}
				if (obj[i].isAnnotationPresent(Parameter.class)) {
					String name = obj[i].getAnnotation(Parameter.class).value();
					parameterArray.add(http.getParameter(name));
					continue;
				}
				if (obj[i].isAnnotationPresent(JsonParameter.class)) {
					String name = obj[i].getAnnotation(JsonParameter.class).value();
					parameterArray.add(http.getJsonObject(Object.class, name));
					continue;
				}
				if (obj[i].isAnnotationPresent(SessionAttribute.class)) {
					String name = obj[i].getAnnotation(SessionAttribute.class).value();
					parameterArray.add(http.getSessionAttribute(Object.class, name));
					continue;
				}
				parameterArray.add(null);
			}
			Object returnValue = method.invoke(instance, parameterArray.toArray());
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			if (!e.getCause().getClass().getSuperclass().equals(HandleException.class)) {
				e.printStackTrace();
				return null;
			}
			HandleException e1 = (HandleException) e.getCause();
			e1.handle(http);
			return null;
		}
	}

	@Override
	public String toString() {
		return method.getName();
	}

	public boolean needDAO() {
		return method.getParameterCount() == 2;
	}
}
