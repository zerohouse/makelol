package next.mapping.dispatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import next.database.DAO;
import next.mapping.exception.HandleException;
import next.mapping.http.Http;
import next.mapping.response.Response;

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

	public Response execute(Http http, DAO dao) {
		try {
			Object[] obj = method.getParameterTypes();
			List<Object> parameterArray = new ArrayList<Object>();
			for (int i = 0; i < obj.length; i++) {
				if (obj[i].equals(Http.class)) {
					parameterArray.add(http);
					continue;
				}
				if (obj[i].equals(DAO.class)) {
					parameterArray.add(dao);
					continue;
				}
				parameterArray.add(null);
			}
			Object returnValue = method.invoke(instance, parameterArray.toArray());
			return (Response) returnValue;
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
