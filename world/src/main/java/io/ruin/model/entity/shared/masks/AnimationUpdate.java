package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

public class AnimationUpdate extends UpdateMask {

    public int id = -2, delay;

    public void set(int id, int delay) {
        if (this.id == -1) // trying out something here - animation cancels should always have priority
            return;
        this.id = id;
        this.delay = delay;
    }

    @Override
    public void reset() {
        id = -2;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return id != -2;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        if (playerUpdate) {
            out.addShortA(id);
            out.addByteA(id < 0 ? 0 : delay);
        } else {
            out.addLEShort(id);
            out.addByte(id < 0 ? 0 : delay);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 16 : 128;
    }

}
