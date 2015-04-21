package next.database;

import java.sql.PreparedStatement;

public interface ConnectManager {
	
	void close();

	PreparedStatement getPSTMT(String sql, Object[] parameters);

}
