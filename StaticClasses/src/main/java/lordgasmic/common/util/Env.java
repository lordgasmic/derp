package lordgasmic.common.util;

import java.util.Iterator;
import java.util.Map;

public class Env {

	public static String getEnv(String env){
		Map<String, String> en = System.getenv();
		
		Iterator<Map.Entry<String,String>> it = en.entrySet().iterator();
		
		while (it.hasNext()){
			Map.Entry<String, String> var = it.next();

			if (var.getKey().equalsIgnoreCase(env)){
				return var.getValue();
			}
		}
		
		return null;
	}
}
