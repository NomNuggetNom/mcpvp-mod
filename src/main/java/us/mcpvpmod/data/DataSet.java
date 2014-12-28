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
	 * Initializes a {@link DataEntry} with information extracted from the given
	 * string and adds it to {@link #entries}.
	 * @param line The line to extract information from.
	 */
	public void load(String line) {
		addEntry(new DataEntry(extractKey(line), getVal(line)));
	}
	
	@Override
	public String toString() {
		return "DataSet [name=" + name + ", parent=" + parent + "]";
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
