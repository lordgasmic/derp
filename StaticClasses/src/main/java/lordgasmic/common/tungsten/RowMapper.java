package lordgasmic.common.tungsten;

import java.util.ArrayList;
import java.util.List;

public class RowMapper {

	class Row {
		
		private String field;
		private String columnName;
		private Class<?> type;

		public Row(String field, String columnName, Class<?> type) {
			this.field = field;
			this.columnName = columnName;
			this.type = type;
		}
		
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public Class<?> getType() {
			return type;
		}
		public void setType(Class<?> type) {
			this.type = type;
		}
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
	}

	private List<Row> rows;
	
	public RowMapper() {
		rows = new ArrayList<RowMapper.Row>();
	}
	
	public void add(String field, String column, Class<?> type) {
		Row row = new Row(field, column, type);
		rows.add(row);
	}
	
	public List<Row> getRows() {
		return rows;
	}
	
}
