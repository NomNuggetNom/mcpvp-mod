package us.mcpvpmod.data;

import java.util.ArrayList;

/**
 * Basically a category of data: A collection of {@link DataEntry}s that can be
 * saved and loaded to disk. Not used independently: instead utilized in a {@link DataFile}.
 */
public class DataSet {

	/** The name of the data set, saved to disk. Essentially a category name. */
	private String name;
	/** The parent data set. */
	private DataSet parent;
	/** All children of this data set. Added when a DataSet is created and this is given as the parent. */
	private ArrayList<DataSet> children = new ArrayList<DataSet>();
	/** All entries in this data set. */
	private ArrayList<DataEntry> entries = new ArrayList<DataEntry>();
	
	/**
	 * A collection of {@link DataEntry}s that can be saved and loaded to disk.
	 * Information is saved to and read from the {@link #DATA_FILE}.
	 * @param dataFile The DataFile to read and write information to and from.
	 * Information can be loaded from this file using {@link #load()}, which populates
	 * {@link #entries}, and saved using {@link #save()}, which writes {@link #entries}.
	 * It is assumed this file already exists.
	 * @param category The name of the category to save this DataSet under. If null,
	 * no category will be used.
	 */
	public DataSet(DataSet parent, String name) {
		this.parent = parent;
		this.name = name;
		if (parent != null) parent.addChild(this);
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The key of the DataEntry.
	 */
	private String extractKey(String line) {
		return line.replaceAll("\\s*" + DataEntry.RE_ENTRY, "$1");
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The String code of the DataEntry.
	 */
	private String extractValCode(String line) {
		return line.replaceAll("\\s*" + DataEntry.RE_ENTRY, "$2");
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The String representation of the value of the DataEntry.
	 */
	private String extractVal(String line) {
		return (line.replaceAll("\\s*" + DataEntry.RE_ENTRY, "$3"));
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The DataEntryType of the DataEntry.
	 */
	private DataEntryType getDataType(String line) {
		String valCode = extractValCode(line);
		for (DataEntryType entryType : DataEntryType.values())
			if (valCode.equals(entryType.symbol))
				return entryType;
		return null;
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The stored Object in the DataEntry.
	 */
	private Object getVal(String line) {
		return getDataType(line).parse(extractVal(line));
	}
	
	
	/**
	 * Add a DataEntry to the array of entries. This will be saved
	 * to disk when {@link #save()} is called.
	 * @param entry The entry to add.
	 * @param save Whether or not to immediately {@link #save()} the
	 * DataSet.
	 */
	public void addEntry(DataEntry entry) {
		this.entries.add(entry);
	}
	
	/**
	 * Remove a DataEntry from the array of entries. This will no longer
	 * be saved to disk when {@link #save()} is called.
	 * @param entry The entry to remove.
	 * @param save Whether or not to immediately {@link #save()} the
	 * DataSet.
	 */
	public void removeEntry(DataEntry entry, boolean save) {
		this.entries.remove(entry);
	}
	
	/**
	 * Returns whether or not {@link #entries} contains the given DataEntry.
	 * @param entry The entry to check.
	 * @return Whether or not {@link #entries} contains the given DataEntry.
	 */
	public boolean containsEntry(DataEntry entry) {
		return this.entries.contains(entry);
	}
	
	/**
	 * Retrieves a DataEntry from {@link #entries}.
	 * @param key The key of the entry to retrieve.
	 * @return A DataEntry in this DataSet with the given key.
	 */
	public DataEntry getEntry(String key) {
		for (DataEntry entry : this.getEntries())
			if (entry.getKey().equals(key))
				return entry;
		return null;
	}
	
	/**
	 * Initializes a {@link DataEntry} with information extracted from the given
	 * string and adds it to {@link #entries}.
	 * @param line The line to extract information from.
	 */
	public void load(String line) {
		addEntry(new DataEntry(extractKey(line), getVal(line)));
	}
	
	/**
	 * @param clazz The class to filter by.
	 * @return A filtered ArrayList of DataEntries where each DataEntry
	 * saves a value of the given class.
	 */
	private ArrayList<DataEntry> filterClass(Class clazz) {
		ArrayList<DataEntry> filtered = new ArrayList<DataEntry>();
		for (DataEntry entry : this.getEntries())
			if (entry.getClazz().equals(clazz))
				filtered.add(entry);
		return filtered;
	}
	
	/**
	 * Used to retrieve a stored byte value from this DataSet.
	 * @param key The key of the byte value to retrieve.
	 * @return The value of the stored byte, if an entry with the
	 * given key exists and is a byte value. Otherwise, null is returned.
	 */
	public Byte getByte(String key) {
		for (DataEntry entry : this.filterClass(Byte.class))
			if (entry.getKey().equals(key))
				return (Byte) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored short value from this DataSet.
	 * @param key The key of the short value to retrieve.
	 * @return The value of the stored short, if an entry with the
	 * given key exists and is a short value. Otherwise, null is returned.
	 */
	public Short getShort(String key) {
		for (DataEntry entry : this.filterClass(Short.class))
			if (entry.getKey().equals(key))
				return (Short) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored integer value from this DataSet.
	 * @param key The key of the integer value to retrieve.
	 * @return The value of the stored integer, if an entry with the
	 * given key exists and is a integer value. Otherwise, null is returned.
	 */
	public Integer getInteger(String key) {
		for (DataEntry entry : this.filterClass(Integer.class))
			if (entry.getKey().equals(key))
				return (Integer) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored long value from this DataSet.
	 * @param key The key of the long value to retrieve.
	 * @return The value of the stored long, if an entry with the
	 * given key exists and is a long value. Otherwise, null is returned.
	 */
	public Long getLong(String key) {
		for (DataEntry entry : this.filterClass(Long.class))
			if (entry.getKey().equals(key))
				return (Long) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored float value from this DataSet.
	 * @param key The key of the float value to retrieve.
	 * @return The value of the stored float, if an entry with the
	 * given key exists and is a float value. Otherwise, null is returned.
	 */
	public Float getFloat(String key) {
		for (DataEntry entry : this.filterClass(Float.class))
			if (entry.getKey().equals(key))
				return (Float) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored double value from this DataSet.
	 * @param key The key of the double value to retrieve.
	 * @return The value of the stored double, if an entry with the
	 * given key exists and is a double value. Otherwise, null is returned.
	 */
	public Double getDouble(String key) {
		for (DataEntry entry : this.filterClass(Double.class))
			if (entry.getKey().equals(key))
				return (Double) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored boolean value from this DataSet.
	 * @param key The key of the boolean value to retrieve.
	 * @return The value of the stored boolean, if an entry with the
	 * given key exists and is a boolean value. Otherwise, null is returned.
	 */
	public Boolean getBoolean(String key) {
		for (DataEntry entry : this.filterClass(Boolean.class))
			if (entry.getKey().equals(key))
				return (Boolean) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored character value from this DataSet.
	 * @param key The key of the character value to retrieve.
	 * @return The value of the stored character, if an entry with the
	 * given key exists and is a character value. Otherwise, null is returned.
	 */
	public Character getCharacter(String key) {
		for (DataEntry entry : this.filterClass(Character.class))
			if (entry.getKey().equals(key))
				return (Character) entry.getVal();
		return null;
	}
	
	/**
	 * Used to retrieve a stored string value from this DataSet.
	 * @param key The key of the string value to retrieve.
	 * @return The value of the stored string, if an entry with the
	 * given key exists and is a string value. Otherwise, null is returned.
	 */
	public String getString(String key) {
		for (DataEntry entry : this.filterClass(String.class))
			if (entry.getKey().equals(key))
				return (String) entry.getVal();
		return null;
	}

	
	@Override
	public String toString() {
		return "DataSet [name=" + name + ", parent=" + parent + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof DataSet && ((DataSet)o).name.equals(this.name);
	}

	public ArrayList<DataEntry> getEntries() {
		return this.entries;
	}
	
	public void setParent(DataSet parent) {
		this.parent = parent;
	}
	
	public void addChild(DataSet child) {
		this.children.add(child);
	}
	
	public DataSet getParent() {
		return this.parent;
	}
	
	public ArrayList<DataSet> getChildren() {
		return this.children;
	}
	
	public boolean hasChildren() {
		return this.children != null;
	}
	
	public String getName() {
		return this.name;
	}
}
