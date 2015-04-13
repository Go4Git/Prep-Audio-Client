package org.stephen.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

import org.stephen.net.impl.OpusClientHandler;

/**
 * Establishes the connection between the client
 * and the server.
 * @author Stephen Andrews
 */
public class ClientConnector {

	/**
	 * Logger instance.
	 */
	private final static Logger logger = Logger.getLogger(ClientConnector.class.getName());
	
	/**
	 * The host to connect to.
	 */
	private final static String HOST = "localhost";
	
	/**
	 * The port.
	 */
	private final static int PORT = 1337;
	
	/**
	 * Registers a connection to the server.
	 * @throws Exception
	 */
	public void connect() throws Exception {
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {			
			Bootstrap bootstrap = new Bootstrap();
			
			bootstrap.group(workerGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new OpusClientHandler());
				}
				
			});
			
			// Start the client.
	        ChannelFuture f = bootstrap.connect(HOST, PORT).sync();
	
	        logger.info("Connection established");
	        
	        // Wait until the connection is closed.
	        f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
