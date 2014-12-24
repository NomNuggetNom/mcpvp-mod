package us.mcpvpmod.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import us.mcpvpmod.Main;

/**
 * A collection of {@link DataEntry}s that can be saved and loaded to disk.
 * Information is saved to and read from the {@link #DATA_FILE}. <br><br>
 * Information can be loaded from an existing file simply by creating a DataSet
 * with the file and calling {@link #load()}. <br><br>
 * Information is saved to the file when {@link #save()} is called. The existing
 * file is replaced and every entry is written to the file.
 */
public class DataSet {
	
	/** The category to nest these values under. */
	private String category;
	/** All entries in this data set. */
	private ArrayList<DataEntry> entries = new ArrayList<DataEntry>();
	/** The location to save and load the data from. It is assumed this already exists. */
	private final File DATA_FILE;
	
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
	public DataSet(File dataFile, String category) {
		this.DATA_FILE	= dataFile;
		this.category	= category;
	}
	
	/**
	 * Add a DataEntry to the array of entries. This will be saved
	 * to disk when {@link #save()} is called.
	 * @param entry The entry to add.
	 * @param save Whether or not to immediately {@link #save()} the
	 * DataSet.
	 */
	public void add(DataEntry entry, boolean save) {
		this.entries.add(entry);
		if (save) save();
	}
	
	/**
	 * Remove a DataEntry from the array of entries. This will no longer
	 * be saved to disk when {@link #save()} is called.
	 * @param entry The entry to remove.
	 * @param save Whether or not to immediately {@link #save()} the
	 * DataSet.
	 */
	public void remove(DataEntry entry, boolean save) {
		this.entries.remove(entry);
		if (save) save();
	}
	
	/**
	 * Returns whether or not {@link #entries} contains the given DataEntry.
	 * @param entry The entry to check.
	 * @return Whether or not {@link #entries} contains the given DataEntry.
	 */
	public boolean contains(DataEntry entry) {
		return this.entries.contains(entry);
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The key of the DataEntry.
	 */
	private String extractKey(String line) {
		return line.replaceAll(DataEntry.RE, "$1");
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The String code of the DataEntry.
	 */
	private String extractValCode(String line) {
		return line.replaceAll(DataEntry.RE, "$2");
	}
	
	/**
	 * @param line A String representation of a DataEntry.
	 * @return The String representation of the value of the DataEntry.
	 */
	private String extractVal(String line) {
		return (line.replaceAll(DataEntry.RE, "$3"));
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
	 * Writes every {@link DataEntry} in {@link #entries} to the
	 * {@link #DATA_FILE}. The file is cleared before writing so that no
	 * "ghost entries" that have been removed from {@link #entries} are saved.
	 */
	public void save() {
		try {
			if (this.category != null) {
				FileUtils.write(DATA_FILE, "> " + this.category + " >" + System.getProperty("line.separator"), "UTF-8", false);
				FileUtils.writeLines(DATA_FILE, "UTF-8", entries, true);
			}

		} catch (Exception e) {
			Main.w("Error occured when saving DataSet %s", this);
			e.printStackTrace();
		}
	}

	/**
	 * Reads the {@link #DATA_FILE} and attempts to populate {@link #entries}
	 * with every found DataEntry that was saved to the file.
	 */
	public void load() {
		InputStreamReader in = null;
		try {
			
			// Again utilize UTF-8 to ensure compatibility.
			in = new InputStreamReader(new FileInputStream(DATA_FILE), "UTF-8");
			// Form a pattern matcher that will cycle through every saved DataEntry.
			Matcher matcher = Pattern.compile(DataEntry.RE).matcher(IOUtils.toString(in));
			// Create a new DataEntry and add it to the entries array.
			while (matcher.find())
				add(new DataEntry(extractKey(matcher.group()), getVal(matcher.group())), false);
			
		} catch (FileNotFoundException e) {
			// THIS SHOULD NOT HAPPEN.
			Main.w("Force creating DataSet DATA_FILE: %s", this.DATA_FILE);
			DATA_FILE.mkdirs();
			
		} catch (Exception e) {
			Main.w("Error occured when loading DataSet %s", this);
			e.printStackTrace();
			
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "DataSet [entries=" + entries + ", DATA_FILE=" + DATA_FILE + "]";
	}
	
	public ArrayList<DataEntry> getEntries() {
		return this.entries;
	}
	
	public String getCategory() {
		return this.category;
	}
}
