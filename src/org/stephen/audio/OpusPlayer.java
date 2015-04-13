package org.stephen.audio;

import java.nio.ByteBuffer;
import java.util.Collection;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import org.jitsi.impl.neomedia.codec.audio.opus.Opus;

import de.jarnbjo.ogg.FileStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.ogg.PhysicalOggStream;

/**
 * Plays opus audio files. Code example provided by Justin,
 * <url>http://stackoverflow.com/users/2272617/justin</url> on
 * Stack Overflow.
 * @author Stephen Andrews
 */
public class OpusPlayer {

	/**
	 * The input bitrate.
	 */
	private final int INPUT_BITRATE = 48000;
	
	/**
	 * The output bitrate.
	 */
	private final int OUTPUT_BITRATE = 48000;
	
	/**
	 * The state of the opus file returned by libjitsi.
	 */
	private long opusState;
	
	/**
	 * The size of the decoder buffer.
	 */
	private final int BUFFER_SIZE = 1024 * 1024;
	
	/**
	 * The {@link ByteBuffer} for decoding the opus file.
	 */
	private ByteBuffer decoder;
	
	/**
	 * The sample size in bits.
	 */
	private final int SAMPLE_SIZE = 16;
	
	/**
	 * The number of channels.
	 */
	private final int MONO = 1;
	
	/**
	 * Whether or not the file is signed.
	 */
	private final boolean IS_SIGNED = true;
	
	/**
	 * Whether or not the sample data is stored in big endian byte order.
	 */
	private final boolean BIG_ENDIAN = false;
	
	/**
	 * The {@link AudioFormat} instance.
	 */
	private AudioFormat audioFormat;
	
	/**
	 * Subclass of {@link PhysicalOggStream} for reading opus files.
	 */
	private FileStream oggFile;
	
	/**
	 * Constructs an {@link OpusPlayer}.
	 * @param oggFile Subclass of {@link PhysicalOggStream} for reading opus files.
	 */
	public OpusPlayer(FileStream oggFile) {
		decoder = ByteBuffer.allocate(BUFFER_SIZE);
		audioFormat = new AudioFormat(OUTPUT_BITRATE, SAMPLE_SIZE, MONO, IS_SIGNED, BIG_ENDIAN);
		opusState = Opus.decoder_create(INPUT_BITRATE, MONO);
		this.oggFile = oggFile;
	}
	
	/**
	 * Decodes the opus file.
	 */
	private byte[] decode(byte[] packetData) {
        int frameSize = Opus.decoder_get_nb_samples(opusState, packetData, 0, packetData.length);
        int decodedSamples = Opus.decode(opusState, packetData, 0, packetData.length, decoder.array(), 0, frameSize, 0);
        if (decodedSamples < 0) {
            System.out.println("Decode error: " + decodedSamples);
            decoder.clear();
            return null;
        }
        decoder.position(decodedSamples * 2); // 2 bytes per sample
        decoder.flip();

        byte[] decodedData = new byte[decoder.remaining()];
        decoder.get(decodedData);
        decoder.flip();
        return decodedData;
    }
	
	/**
	 * Plays the opus file.
	 */
	public void play() {
		int totalDecodedBytes = 0;
        try {
            SourceDataLine speaker = AudioSystem.getSourceDataLine(audioFormat);
            speaker.open();
            speaker.start();
            for (LogicalOggStream stream : (Collection<LogicalOggStream>) oggFile.getLogicalStreams()) {
                byte[] nextPacket = stream.getNextOggPacket();
                while (nextPacket != null) {
                    byte[] decodedData = decode(nextPacket);
                    if(decodedData != null) {
                        // Write packet to SourceDataLine
                        speaker.write(decodedData, 0, decodedData.length);
                        totalDecodedBytes += decodedData.length;
                    }
                    nextPacket = stream.getNextOggPacket();
                }
            }
            speaker.drain();
            speaker.close();
            System.out.println(String.format("Decoded to %d bytes", totalDecodedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
