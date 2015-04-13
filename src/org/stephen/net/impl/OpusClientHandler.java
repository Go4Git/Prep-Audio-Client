package org.stephen.net.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.stephen.audio.OpusFile;
import org.stephen.audio.OpusPlayer;

public class OpusClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("called");
		if (msg instanceof OpusFile) {
			OpusFile opusFile = (OpusFile) msg;
			new OpusPlayer(opusFile.getOggFile()).play();
		}
	}
}
