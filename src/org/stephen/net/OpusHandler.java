package org.stephen.net;

import io.netty.channel.socket.SocketChannel;

public class OpusHandler {

	private SocketChannel channel;
	
	public OpusHandler(SocketChannel channel) {
		this.channel = channel;
	}
}
