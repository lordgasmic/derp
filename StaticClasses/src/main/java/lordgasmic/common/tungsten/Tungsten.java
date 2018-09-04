package lordgasmic.common.tungsten;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lordgasmic.common.tungsten.RowMapper.Row;

public class Tungsten {
	
	private Tungsten() { };

	public static <T> List<T> getDataList(ResultSet rset, RowMapper rowList, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        List<T> list = new ArrayList<T>();
        
        while (rset.next()) {
        	T c = clazz.newInstance();
        	
        	for (Row r : rowList.getRows()) {
        		String fieldName = r.getField();
        		String column = r.getColumnName();
        		Class<?> type = r.getType();
				
				// find the set method
				String mName = "set" + fieldName;
				Method[] methods = clazz.getMethods();
				for(Method m : methods) {
					if (m.getName().equalsIgnoreCase(mName)){
						
						try {
							m.invoke(c, rset.getObject(column, type));
						} 
						catch (SQLException e) {
							//if the column doesnt exist, then skip it and go to the next
							String[] msg = e.getMessage().split(" ");
							
							if (msg[0].equals("Column") && msg[2].equals("not") && msg[3].equals("found."))
								continue;
							
							throw e;
						}
						
						break;
					}
				}
        	}
        	
        	list.add(c);
        }
        
        return list;
    }

}
