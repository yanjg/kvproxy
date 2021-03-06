package com.nfbank.tech.kvproxy.redis;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

/**
 * Redis server
 */
public class Main {
	private static Integer port = 8080;
	public static ApplicationContext factory;

	public static void main(String[] args) throws InterruptedException {
		DOMConfigurator.configureAndWatch("log4j.xml");
		factory = new ClassPathXmlApplicationContext("spring/application-redis.xml");
		// Only execute the command handler in a single thread
		final RedisCommandHandler commandHandler = new RedisCommandHandler(new SimpleRedisServer());

		// Configure the server.
		ServerBootstrap b = new ServerBootstrap();
		final DefaultEventExecutorGroup group = new DefaultEventExecutorGroup(1);
		try {
			b.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100).localAddress(port)
					.childOption(ChannelOption.TCP_NODELAY, true).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							// p.addLast(new ByteLoggingHandler(LogLevel.INFO));
//							p.addLast(new RedisCommandDecoder());
//							p.addLast(new RedisReplyEncoder());
							p.addLast(group, commandHandler);
						}
					});

			// Start the server.
			ChannelFuture f = b.bind().sync();

			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			group.shutdownGracefully();
		}
	}
}
