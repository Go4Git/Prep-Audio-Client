package org.stephen.net.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileOutputStream;

import org.stephen.audio.OpusFile;
import org.stephen.audio.OpusPlayer;

public class ChunkedFileHandler extends ChannelInboundHandlerAdapter {

	/**
	 * The incoming data.
	 */
	private int total = 0;
	
	private ByteBuf incomingFile = Unpooled.buffer();
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Reading incoming data...");
		
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("Readable bytes: " + buf.readableBytes());
		byte[] data = new byte[buf.readableBytes()];
		total += buf.readableBytes();
		System.out.println("Data:" + data.length);
		buf.readBytes(incomingFile);
		System.out.println("Total: " + total);
		
		if (total == 2629170) {
			System.out.println("------------------------------------------------");
			System.out.println("-------------- Done Reading file ---------------");
			System.out.println("------------------------------------------------");

			System.out.println(incomingFile.array().length);
			FileOutputStream fos = new FileOutputStream("./data/new.opus");
			fos.write(incomingFile.array());
			fos.close();
			
			System.out.println("Done!");
			
			OpusFile oFile = new OpusFile(new File("./data/new.opus"));
			OpusPlayer oPlayer = new OpusPlayer(oFile.getOggFile());
			oPlayer.play();
		}
		
		

		
		/*ByteBuffer nioBuffer = buf.nioBuffer();
		FileOutputStream fos = new FileOutputStream("./data/new.opus");
		FileChannel channel = fos.getChannel();
		
		while (nioBuffer.hasRemaining()) {
			channel.write(nioBuffer);
		}
		
		channel.close();
		fos.close();

		
		if (msg instanceof OpusFile) {
			OpusFile opusFile = (OpusFile) msg;
			new OpusPlayer(opusFile.getOggFile()).play();
		}*/
	}
}
