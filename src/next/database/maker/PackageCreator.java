package next.database.maker;

import next.database.annotation.Table;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class PackageCreator {
	public static void createTable(boolean ifExistDrop, String packagePath) {
		Reflections ref = new Reflections(packagePath, new SubTypesScanner(), new TypeAnnotationsScanner());
		ref.getTypesAnnotatedWith(Table.class).forEach(cLass -> {
			TableMaker tm = new TableMaker(cLass);
			if (ifExistDrop)
				tm.dropTable();
			tm.createTable();
			tm.commitAndReturn();
		});
	}
}
