package io.ruin.api.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class NettyServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static NettyServer start(String name, int port, Function<ChannelPipeline, ? extends ChannelHandler> channelHandlerFunction) {
        NettyServer server = new NettyServer();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(server.bossGroup, server.workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(@NotNull SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                ChannelHandler decoder = channelHandlerFunction.apply(pipeline);
                if (decoder == null)
                    throw new IllegalArgumentException("Must have a non-null (valid) decoder");
                pipeline.addLast("decoder", decoder);
               // pipeline.addLast("exception_handler", new ExceptionHandler());
            }
        });
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.bind(port);
        ServerWrapper.println(name + " is now listening on " + port);
        return server;
    }

}