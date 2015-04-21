package next.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import next.setting.jobject.JMap;

import com.google.gson.stream.JsonReader;

public class Setting {

	static JMap node;

	static {
		String path = JMap.class.getResource("/").getPath();
		try {
			JsonReader reader = new JsonReader(new FileReader(path + "Setting.json"));
			node = new JMap(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String... keys) {
		return node.get(keys).toString();
	}
	
	public static Object get(String... keys) {
		return node.get(keys);
	}

}
