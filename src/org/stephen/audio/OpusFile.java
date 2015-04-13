package org.stephen.audio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

import de.jarnbjo.ogg.FileStream;
import de.jarnbjo.ogg.PhysicalOggStream;

/**
 * Represents an Opus encoded audio file to be received.
 * @author Stephen Andrews
 */
public class OpusFile implements Serializable {
	
	/**
	 * The generated serial version UID.
	 */
	private static final long serialVersionUID = -456951659694684503L;

	/**
	 * The access mode.
	 */
	private final String READ_ONLY = "rw";
	
	private RandomAccessFile raf;
	
	/**
	 * Subclass of {@link PhysicalOggStream} for reading Opus files.
	 */
	private FileStream oggFile;
	
	/**
	 * Constructs an {@link OpusFile}.
	 * @param soundFile The raw Opus file.
	 */
	public OpusFile(File soundFile) {
		try {
			this.raf = new RandomAccessFile(soundFile, READ_ONLY);
			this.oggFile = new FileStream(raf);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public RandomAccessFile getRaf() {
		return raf;
	}
	
	/**
	 * Gets the ogg file.
	 * @return The ogg file.
	 */
	public FileStream getOggFile() {
		return oggFile;
	}
}
