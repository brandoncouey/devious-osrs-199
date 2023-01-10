package io.ruin.update;

import io.netty.channel.Channel;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.protocol.Response;

public class HandshakeDecoder extends MessageDecoder<Channel> {

    public static int REVISION = 199;
    public static final int OPCODE = 15;
    public static final int SIZE = 4;

    private final FileStore fileStore;

    public HandshakeDecoder(FileStore fileStore) {
        super(null, false);
        this.fileStore = fileStore;
    }

    @Override
    protected void handle(Channel channel, InBuffer in, int opcode) {
        handle(fileStore, channel, in, opcode);
    }

    public static void handle(FileStore fileStore, Channel channel, InBuffer in, int opcode) {
        if (opcode == OPCODE) {
            int clientBuild = in.readInt();
            if (clientBuild == REVISION) {
                channel.writeAndFlush(new OutBuffer(REVISION).addByte(0).toBuffer(), channel.voidPromise());
                channel.pipeline().replace("decoder", "decoder", new Js5Decoder(fileStore));
                return;
            }
            Response.GAME_UPDATED.send(channel);
        } else {
            Response.BAD_SESSION_ID.send(channel);
            throw new IllegalArgumentException("Client " + channel.remoteAddress() + " sent invalid handshake opcode " + opcode);
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch (opcode) {
            case OPCODE: {
                return SIZE;
            }
        }
        return -3;
    }

}
