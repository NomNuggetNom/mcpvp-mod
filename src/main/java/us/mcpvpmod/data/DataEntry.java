package us.mcpvpmod.data;


/**
 * Essentially a String representation of a key, pair value to store to disk.
 * Formatted as such:<br>
 * <code>keyName=typeCode:valAsString</code> <br>
 * <br>
 * The <code>keyName</code> is simply the given {@link #key}. The
 * <code>typeCode</code> is a 3 letter String representation of the given Object
 * {@link #val}. See {@link DataEntryType#values()}. If the Object's type has no
 * established character, the fall-back is <code>???</code>.
 */
public class DataEntry {

	/** The key of the entry. */
	String key;
	/** The value to store. Not directly saved: factored into the {@link #valString}. */
	Object val;
	/** The value String that will be saved, composed of 
	 * the value character and the string representation of the {@link #val}. */
	final String valString;
	
	/** The regular expression that all DataEntries must match. */
	static final String RE = "(.*)=(\\w{3}):(.*)";
	
	/**
	 * Essentially a String representation of a key, pair value to store to
	 * disk. Used for saving simple data types, like numbers or a string.
	 * @param key The key to reference the value by, such as in a HashMap.
	 * @param val The object that will be saved: automatically converted
	 *  to a String and formed into the {@link #valString}.
	 */
	public DataEntry(String key, Object val) {
		this.key = key;
		this.val = val;
		this.valString = this.valString();
	}

	/**
	 * @return The appropriate String code that should be used to represent this
	 * entry. Uses values in {@link DataEntryType}. Unknown classes will return
	 * <code>???</code>.
	 * @throws UnsupportedDataException If a data type is attempted to be converted
	 * that has no registered code.
	 */
	private String valCode() throws UnsupportedDataException {
		for (DataEntryType entryType : DataEntryType.values()) {
			if (entryType.clazz.equals(val.getClass()))
				return entryType.symbol;
		}
		throw new UnsupportedDataException(val.getClass().toString());
	}
	
	/**
	 * @return The appropriate string representation of the value argument in the
	 * key, pair storage system. Formatted as <code>typeCode:valAsString</code>
	 */
	private String valString() {
		try {
			return valCode() + ":" + this.val.toString();
		} catch (UnsupportedDataException e) {
			e.printStackTrace();
		}
		return "???:" + this.val.toString();
	}
	
	/**
	 * @return The String that is saved to the disk.
	 */
	@Override
	public String toString() {
		return key + "=" + valString;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof DataEntry && ((DataEntry)o).key.equals(this.key);
	}
	
	/**
	 * An exception stating that the data type that was attempted to be saved is
	 * not supported and will use a <code>???</code> code as it's representation.
	 */
	public static class UnsupportedDataException extends Exception {

		/**
		 * Throws an exception stating that the data type that was attempted
		 * to be saved is not supported and will use a <code>?</code> char as
		 * it's representation.
		 * @param string The class that wasn't allowed.
		 */
		public UnsupportedDataException(String string) {
			super(string + " is not supported.");
		}
		
	}
	
}
