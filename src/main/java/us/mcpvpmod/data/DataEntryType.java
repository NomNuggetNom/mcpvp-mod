package us.mcpvpmod.data;


/**
 * Established values for classes and the code that should be used to represent
 * them when they are written to disk. Aids in preserving value validity and
 * avoiding errors, and also provides a method to parse the value a
 * {@link DataEntry}'s saved value.<br>
 * <br>
 * 
 * All primitive data types are supported, in addition to Strings.
 */
public enum DataEntryType {
	BYTE	(Byte.class,		"byt"),
	SHORT	(Short.class,		"shr"),
	INTEGER	(Integer.class,		"int"),
	LONG	(Long.class,		"lng"),
	FLOAT	(Float.class,		"flt"),
	DOUBLE	(Double.class,		"dbl"),
	BOOLEAN	(Boolean.class,		"bln"),
	CHAR	(Character.class,	"chr"),
	STRING	(String.class,		"str");
	
	/** The Class that will be represented by the symbol. */
	public final Class<?> clazz;
	/** The symbol to represent the class. */
	public final String symbol;
	
	DataEntryType(Class<?> type, String symbol) {
		this.clazz = type;
		this.symbol = symbol;
	}
	
	/**
	 * Translates the saved String value of the entry and returns the
	 * equal Object value.
	 * @param string The String to extract a value from. It is assumed 
	 * this is a String representation of {@link DataEntry#val}.
	 * @return The extracted value from the String, identical to what
	 * was saved when the Entry was saved.
	 */
	public Object parse(String string) {
		switch(this) {
		case BYTE:		return Byte.valueOf(string);
		case SHORT:		return Short.valueOf(string);
		case INTEGER:	return Integer.valueOf(string);
		case LONG:		return Long.valueOf(string);
		case FLOAT:		return Float.valueOf(string);
		case DOUBLE:	return Double.valueOf(string);
		case BOOLEAN:	return Boolean.valueOf(string);
		case CHAR:		return Character.valueOf(string.charAt(0));
		case STRING:	return String.valueOf(string);
		default:		return null;
		}
	}
	
	/**
	 * Future-proof method for extensibility. Overrides the
	 * toString method of the entry type.
	 * @param obj The object to get a String representation for.
	 * @return A string representation of the entry to be saved.
	 */
	public String getAsString(Object obj) {
		switch(this) {
		case BYTE:		break;
		case SHORT:		break;
		case INTEGER:	break;
		case LONG:		break;
		case FLOAT:		break;
		case DOUBLE:	break;
		case BOOLEAN:	break;
		case CHAR:		break;
		case STRING:	break;
		}
		return obj.toString();
	}
}