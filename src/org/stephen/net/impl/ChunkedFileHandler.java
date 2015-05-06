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
	 * The byte total.
	 */
	private int total = 0;
	
	/**
	 * Cache the incoming bytes.
	 */
	private ByteBuf incomingFile = Unpooled.buffer();
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Reading incoming data...");
		
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("Readable bytes: " + buf.readableBytes());
		total += buf.readableBytes();
		System.out.println("Total: " + total);
		incomingFile.writeBytes(buf);
		
		if (total == 2629170) {
			System.out.println("------------------------------------------------");
			System.out.println("-------------- Done Reading file ---------------");
			System.out.println("------------------------------------------------");

			System.out.println("Length of incoming file: " + incomingFile.array().length);
			System.out.println("Writing file...");
			FileOutputStream fos = new FileOutputStream("./data/new.opus");
			fos.write(incomingFile.array());
			fos.close();
			
			System.out.println("Done!");
			
			OpusFile oFile = new OpusFile(new File("./data/new.opus"));
			OpusPlayer oPlayer = new OpusPlayer(oFile.getOggFile());
			oPlayer.play();
		}
	}
}
