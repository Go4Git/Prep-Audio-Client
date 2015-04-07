package org.stephen.net.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

import org.stephen.model.Person;

/**
 * Initialized after the client establishes a connection to the server.
 * The client receives an object, reads it, modifies it, and sends it 
 * back to the server.
 * @author Stephen Andrews
 */
public class ObjectHandlerClient extends ChannelInboundHandlerAdapter {

	/**
	 * Logger instance.
	 */
	public final static Logger logger = Logger.getLogger(ObjectHandlerClient.class.getName());
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	logger.info("Client channel initialized!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	logger.info("Reading data sent from the server...");
    	
    	/* Get our dummy object send from the server */
    	Person dummy = (Person) msg;
    	
    	/* Verify our data came in */
    	if (dummy != null) {
    		System.out.println(dummy.toString());
    		dummy.setName("Phil");
    		dummy.setAge(8);
    		dummy.setIncome(0.00);
    	}
    	
    	/* Sending back modified object */
    	logger.info("Sending back modified dummy object...");
    	
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
