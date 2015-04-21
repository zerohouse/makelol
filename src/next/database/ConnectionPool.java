package next.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;

import next.setting.LoggerUtil;
import next.setting.Setting;
import next.setting.jobject.JMap;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionPool {

	private static final Logger logger = LoggerUtil.getLogger(ConnectionPool.class);
	
	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	private static BoneCP pool;

	public static Connection getConnection(boolean autocommit) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			if(!autocommit)
				connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void shutdown() {
		pool.shutdown();
	}

	static {
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BoneCPConfig config = new BoneCPConfig();
			Map<Object, Object> consetting = ((JMap) Setting.get("database", "connectionPool(boneCp)")).getChilds();
			Method[] methods = BoneCPConfig.class.getDeclaredMethods();
			for(int i =0; i< methods.length; i++){
				if(!methods[i].getName().substring(0, 3).equals("set"))
					continue;
				if(methods[i].getParameterCount()!=1)
					continue;
				Object setting = consetting.get(subString(methods[i].getName()));
				if(setting==null)
					continue;
				try {
					logger.info(String.format("JDBC %s(%s)",methods[i].getName(), setting.toString()));
					methods[i].invoke(config, setting);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			pool = new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Object subString(String method) {
		String start = method.substring(3, 4).toLowerCase();
		return start + method.substring(4, method.length());
	}
}
