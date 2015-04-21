package next.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.setting.LoggerUtil;

import org.slf4j.Logger;

public class TransactionSupport implements ConnectManager {

	private static final Logger logger = LoggerUtil.getLogger(ConnectManager.class);

	private Connection connection;

	private void connect() {
		if (connection != null)
			return;
		connection = ConnectionPool.getConnection(false);
	}

	@Override
	public PreparedStatement getPSTMT(String sql, Object[] parameters) {
		connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.length; j++) {
					pstmt.setObject(j + 1, parameters[j]);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.debug(pstmt.toString());
		return pstmt;
	}

	@Override
	public void close() {
		commit();
		if (connection == null)
			return;
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void commit() {
		connect();
		try {
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
