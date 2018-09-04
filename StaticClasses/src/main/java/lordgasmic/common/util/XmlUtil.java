package lordgasmic.common.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlUtil {
	
	private XmlUtil() {}

	public static void writeObject(Object o, File location) throws FileNotFoundException {
		XMLEncoder xml = new XMLEncoder(new FileOutputStream(location));
		xml.writeObject(o);
		xml.close();
	}
	
	public static <T> T readObject(File location, Class<T> clazz) throws FileNotFoundException {
		XMLDecoder xml = new XMLDecoder(new FileInputStream(location));
		Object object = xml.readObject();
		xml.close();
		
		return clazz.cast(object);
	}
}
