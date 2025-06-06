package io.ruin.api.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.IPAddress;
import io.ruin.api.utils.ISAACCipher;
import io.ruin.api.utils.ServerWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class MessageDecoder<T> extends ByteToMessageDecoder {

    protected static final int VAR_BYTE = -1, VAR_SHORT = -2, VAR_INT = -4, UNHANDLED = Byte.MIN_VALUE;

    protected boolean mobile;

    private ISAACCipher cipher;

    private ConcurrentLinkedQueue<Message> messages;

    private int opcode, size;

    public MessageDecoder(ISAACCipher cipher, boolean queue) {
        this.cipher = cipher;
        this.messages = queue ? new ConcurrentLinkedQueue<>() : null;
        this.opcode = -1;
        this.size = -1;
    }

    protected void add(Message message) {
        messages.offer(message);
    }

    public boolean process(T t, int limit) {
        if (!messages.isEmpty()) {
            int count = 0;
            Message message;
            while ((message = messages.poll()) != null) {
                try {
                    handle(t, message.buffer, message.opcode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (limit > 0 && ++count > limit)
                    return false;
            }
        }
        return true;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        if (opcode == -1) {
            if (buffer.readableBytes() < 1)
                return;
            opcode = buffer.readUnsignedByte();
            if (cipher != null)
                opcode = (opcode - cipher.readKey()) & 0xff;
            size = getSize(opcode);
        }
        if (size == VAR_BYTE) {
            if (buffer.readableBytes() < 1)
                return;
            size = buffer.readUnsignedByte();
        } else if (size == VAR_SHORT) {
            if (buffer.readableBytes() < 2)
                return;
            size = buffer.readUnsignedShort();
        } else if (size == VAR_INT) {
            if (buffer.readableBytes() < 4)
                return;
            size = buffer.readInt();
        } else if (size == UNHANDLED) {
            byte[] data = new byte[buffer.readableBytes()];
            buffer.readBytes(data);
            ServerWrapper.logWarning("Unhandled packet size for opcode: " + opcode + " ip: " + IPAddress.get(ctx.channel()) + ", buff: " + Arrays.toString(data));
            ctx.close();
            return;
        }
        if (buffer.readableBytes() < size) {
            return;
        }
        try {
            byte[] payload = new byte[size];
            buffer.readBytes(payload);
            if (messages != null) {
                add(new Message(opcode, payload));
            } else {
                try {
                    handle((T) ctx.channel(), new InBuffer(payload), opcode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            opcode = size = -1;
        }
    }

    protected abstract void handle(T t, InBuffer in, int opcode);

    protected abstract int getSize(int opcode);

    public final int getMessageCount() {
        return messages.size();
    }

}