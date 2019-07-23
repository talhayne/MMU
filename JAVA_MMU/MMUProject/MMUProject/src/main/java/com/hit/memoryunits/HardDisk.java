package com.hit.memoryunits;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.logging.Level;

import com.hit.util.MMULogger;

import java.util.HashMap;

public class HardDisk {
	private Map<Long, Page<byte[]>> pages;
	private static final int _SIZE = 1000;
	private static String DEFAULT_FILE_NAME = "src/main/resources/harddisk/hd.txt";
	private MMULogger logger;
	private static final HardDisk instance = new HardDisk();

	@SuppressWarnings("unchecked")
	private HardDisk() {
		File hdFile = new File(DEFAULT_FILE_NAME);

		if (hdFile.exists()) {
			FileInputStream hdFileInputStream;
			BufferedInputStream hdBufferInputStream;
			ObjectInputStream hdObjectInputStream;

			try {
				hdFileInputStream = new FileInputStream(DEFAULT_FILE_NAME);
				hdBufferInputStream = new BufferedInputStream(hdFileInputStream);
				hdObjectInputStream = new ObjectInputStream(hdBufferInputStream);

				pages = (HashMap<Long, Page<byte[]>>) hdObjectInputStream.readObject();

				hdObjectInputStream.close();
				hdBufferInputStream.close();
				hdFileInputStream.close();

			} catch (FileNotFoundException e) {
				logger.writeError("The file Not Found or not exist", e, Level.SEVERE);
				e.printStackTrace();

			} catch (ClassNotFoundException e) {
				logger.writeError(
						"The application tries to load in a class through its string name using"
								+ " but no definition for the class with the specified name could be found.  ",
						e, Level.SEVERE);
				e.printStackTrace();
			} catch (IOException e) {
				logger.writeError(
						"This class is the general class of exceptions produced by failed or interrupted I/O operations.",
						e, Level.SEVERE);
				e.printStackTrace();
			}
		} else {
			pages = new HashMap<Long, Page<byte[]>>(_SIZE);

			for (long id = 1; id <= _SIZE; id++) {
				pages.put(id, new Page<byte[]>(id, new byte[] { (byte) id }));
			}

			try {
				SaveHDToFile();
			} catch (FileNotFoundException e) {
				logger.writeError("The file Not Found or not exist", e, Level.SEVERE);
				e.printStackTrace();
			} catch (IOException e) {
				logger.writeError(
						"This class is the general class of exceptions produced by failed or interrupted I/O operations.",
						e, Level.SEVERE);
				e.printStackTrace();
			}
		}

	}

	public static HardDisk getInstance() {
		return instance;
	}

	public Page<byte[]> pageFault(Long pageId) throws java.io.FileNotFoundException, java.io.IOException {

		return pages.get(pageId);
	}

	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId)
			throws FileNotFoundException, IOException {
		pages.put(moveToHdPage.getPageId(), moveToHdPage);
		SaveHDToFile();

		Page<byte[]> returnPage = pages.get(moveToRamId);
		return returnPage;
	}

	private void SaveHDToFile() throws FileNotFoundException, IOException {
		FileOutputStream hdfileStream = new FileOutputStream(DEFAULT_FILE_NAME);
		BufferedOutputStream bufferedHDStream = new BufferedOutputStream(hdfileStream);
		ObjectOutputStream objectHDStream = new ObjectOutputStream(bufferedHDStream);

		objectHDStream.writeObject(pages);
		objectHDStream.close();
		hdfileStream.close();
		bufferedHDStream.close();
	}

	public void addHdPages(Map<Long, Page<byte[]>> mapToAdd) {
		pages.putAll(mapToAdd);

		try {
			SaveHDToFile();
		} catch (FileNotFoundException e) {
			logger.writeError("The file Not Found or not exist", e, Level.SEVERE);
			e.printStackTrace();
		} catch (IOException i) {
			logger.writeError(
					"This class is the general class of exceptions produced by failed or interrupted I/O operations.",
					i, Level.SEVERE);
			i.printStackTrace();
		}
	}
}
