package next.mapping.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.model.User;
import next.database.DAO;

import org.junit.Test;

public class MethodHolderTest {

	public void test2(String a, DAO dao, String s) {
		User user = new User();
		user.setNickName("abc");
		dao.insert(user);
		dao.commitAndClose();
		System.out.println("a");
	}

	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		MethodHolder m = new MethodHolder(this, this.getClass().getMethod("test2", String.class, DAO.class, String.class));
		m.execute(null, new DAO());
	}

	@Test
	public void test2() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = this.getClass().getMethod("test2", DAO.class, String.class);
		Object[] ab = new Object[] { null, null };
		ab[0] = new DAO();
		ab[1] = "";

		m.invoke(this, ab);
	}
}
