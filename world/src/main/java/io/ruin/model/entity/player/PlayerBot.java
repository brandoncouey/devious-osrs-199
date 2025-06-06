package io.ruin.model.entity.player;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.ruin.Server;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.ListUtils;
import io.ruin.model.World;
import io.ruin.model.map.Position;

import java.net.SocketAddress;
import java.util.function.Consumer;

public class PlayerBot {

    public static void create(Position position, Consumer<Player> botConsumer) {
        Server.worker.execute(() -> create0(position, botConsumer));
    }

    private static void create0(Position position, Consumer<Player> botConsumer) {
        Player bot = new Player() {
            @Override
            public void checkLogout() {
                if (logoutStage == -1) {
                    setOnline(false);
                    finish();
                    World.players.remove(getIndex());
                }
            }
        };
        int index = World.players.add(bot, 1);

        LoginInfo info = new LoginInfo(new EmptyChannel(), "BOT" + index, "password", "", "", null, 0, false, 0, World.id, new int[4]);
        info.update(-1, info.name, "", ListUtils.toList(PlayerGroup.REGISTERED.id), 0);

        bot.setIndex(index);
        bot.init(info);
        bot.getPosition().copy(position);
        bot.lastRegion = bot.getPosition().getRegion();
        bot.newPlayer = false;
        bot.start();
        bot.setOnline(true);
        botConsumer.accept(bot);
    }

    private static final class EmptyChannel implements Channel {
        @Override
        public ChannelId id() {
            return null;
        }

        @Override
        public EventLoop eventLoop() {
            return null;
        }

        @Override
        public Channel parent() {
            return null;
        }

        @Override
        public ChannelConfig config() {
            return null;
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public boolean isRegistered() {
            return false;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public ChannelMetadata metadata() {
            return null;
        }

        @Override
        public SocketAddress localAddress() {
            return null;
        }

        @Override
        public SocketAddress remoteAddress() {
            return new SocketAddress() {
            };
        }

        @Override
        public ChannelFuture closeFuture() {
            return null;
        }

        @Override
        public boolean isWritable() {
            return false;
        }

        @Override
        public long bytesBeforeUnwritable() {
            return 0;
        }

        @Override
        public long bytesBeforeWritable() {
            return 0;
        }

        @Override
        public Unsafe unsafe() {
            return null;
        }

        @Override
        public ChannelPipeline pipeline() {
            return null;
        }

        @Override
        public ByteBufAllocator alloc() {
            return null;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1) {
            return null;
        }

        @Override
        public ChannelFuture disconnect() {
            return null;
        }

        @Override
        public ChannelFuture close() {
            return null;
        }

        @Override
        public ChannelFuture deregister() {
            return null;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture disconnect(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture close(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture deregister(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public Channel read() {
            return null;
        }

        @Override
        public ChannelFuture write(Object o) {
            return null;
        }

        @Override
        public ChannelFuture write(Object o, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public Channel flush() {
            return null;
        }

        @Override
        public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture writeAndFlush(Object o) {
            return null;
        }

        @Override
        public ChannelPromise newPromise() {
            return null;
        }

        @Override
        public ChannelProgressivePromise newProgressivePromise() {
            return null;
        }

        @Override
        public ChannelFuture newSucceededFuture() {
            return null;
        }

        @Override
        public ChannelFuture newFailedFuture(Throwable throwable) {
            return null;
        }

        @Override
        public ChannelPromise voidPromise() {
            return null;
        }

        @Override
        public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
            return null;
        }

        @Override
        public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
            return false;
        }

        @Override
        public int compareTo(Channel o) {
            return 0;
        }
    }

}
