package next.database.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import next.database.annotation.Exclude;
import next.database.annotation.OtherTable;
import next.database.annotation.Table;
import next.setting.Setting;

public class SqlSupports {

	private Map<Class<?>, KeyParams> keyParamsMap;
	private Map<Field, SqlField> sqlFieldMap;
	private Map<Class<?>, String> tableNameMap;

	private static SqlSupports sqlSupports = new SqlSupports();

	public static SqlSupports getInstance() {
		return sqlSupports;
	}
	
	private SqlSupports() {
		keyParamsMap = new HashMap<Class<?>, KeyParams>();
		sqlFieldMap = new HashMap<Field, SqlField>();
		tableNameMap = new HashMap<Class<?>, String>();

		Reflections ref = new Reflections(Setting.getString("database", "modelPath"), new SubTypesScanner(), new TypeAnnotationsScanner());
		ref.getTypesAnnotatedWith(Table.class).forEach(cLass -> {

			String tableName = cLass.getSimpleName();
			if (cLass.isAnnotationPresent(Table.class)) {
				Table table = cLass.getAnnotation(Table.class);
				if (!table.value().equals(""))
					tableName = table.value();
			}
			tableNameMap.put(cLass, tableName);

			// 필드 먼저 생성하고 넘겨줘야댐. KeyParams 생성할때 생성된 Sql필드 사용함.
				Field[] fields = cLass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].isAnnotationPresent(OtherTable.class)) {
						sqlFieldMap.put(fields[i], new SqlFieldOtherTable(fields[i]));
					} else {
						sqlFieldMap.put(fields[i], new SqlFieldNormal(tableName, fields[i]));
					}
				}
				keyParamsMap.put(cLass, new KeyParams(cLass, this, tableName));
			});

	}

	public KeyParams getKeyParams(Class<?> cLass) {
		return keyParamsMap.get(cLass);
	}

	public KeyParams getKeyParams(Object record) {
		return new KeyParams(record, this, tableNameMap.get(record.getClass()));
	}

	public SqlField getSqlField(Field field) {
		return sqlFieldMap.get(field);
	}

	public String getTableName(Class<?> cLass) {
		return keyParamsMap.get(cLass).getTableName();
	}

	public <T> T getObject(Class<T> cLass, Map<String, Object> record) {
		if (record == null)
			return null;
		T result = null;
		try {
			result = cLass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		setObject(result, record);
		return result;
	}

	public boolean setObject(Object record, Map<String, Object> recordMap) {
		if (recordMap == null)
			return false;
		Class<?> cLass = record.getClass();
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			Object obj = recordMap.get(getSqlField(fields[i]).getColumnName());
			if (obj == null)
				continue;
			if (obj.getClass().equals(Timestamp.class)) {
				obj = new Date(((Timestamp) obj).getTime());
			}
			fields[i].setAccessible(true);
			try {
				fields[i].set(record, obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}



}
