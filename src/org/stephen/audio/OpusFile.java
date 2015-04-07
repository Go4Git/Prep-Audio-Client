package org.stephen.audio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import de.jarnbjo.ogg.FileStream;
import de.jarnbjo.ogg.PhysicalOggStream;


/**
 * Represents an opus encoded audio file to be received.
 * @author Stephen Andrews
 */
public class OpusFile {
	
	/**
	 * The access mode.
	 */
	private final String READ_ONLY = "r";
	
	/**
	 * Subclass of {@link PhysicalOggStream} for reading opus files.
	 */
	private FileStream oggFile;
	
	/**
	 * Constructs an {@link OpusFile}.
	 * @param soundFile The raw opus file.
	 */
	public OpusFile(File soundFile) {
		try {
			this.oggFile = new FileStream(new RandomAccessFile(soundFile, READ_ONLY));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the ogg file.
	 * @return The ogg file.
	 */
	public FileStream getOggFile() {
		return oggFile;
	}
}
