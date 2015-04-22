package next.setting;

public class TableOptions {
	private String datatype;
	private Boolean notnull;
	private Boolean hasDefaultValue;
	private Object defaultValue;

	public TableOptions(String datatype, Boolean notnull, Boolean hasDefaultValue, Object defaultValue) {
		this.datatype = datatype;
		this.notnull = notnull;
		this.hasDefaultValue = hasDefaultValue;
		this.defaultValue = defaultValue;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public Boolean getNotnull() {
		return notnull;
	}

	public void setNotnull(Boolean notnull) {
		this.notnull = notnull;
	}

	public Boolean getHasDefaultValue() {
		return hasDefaultValue;
	}

	public void setHasDefaultValue(Boolean hasDefaultValue) {
		this.hasDefaultValue = hasDefaultValue;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
