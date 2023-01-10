package io.ruin.model.entity.shared.masks;

import io.ruin.Server;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.shared.UpdateMask;

import java.util.ArrayList;

public class HitsUpdate extends UpdateMask {

    private final ArrayList<Splat> splats = new ArrayList<>(4);

    public int hpBarType;

    private int hpBarRatio;

    public int curHp, maxHp;

    public long removeAt;

    private boolean forceSend = false;

    public void add(Hit hit, int curHp, int maxHp) {
        if (splats.size() < 6)
            splats.add(new Splat(hit.type.activeID, hit.damage, 0));
        this.hpBarRatio = toRatio(hpBarType, curHp, maxHp);
        this.curHp = curHp;
        this.maxHp = maxHp;
        this.removeAt = Server.getEnd(10);
    }


    /**
     * For showing HP bar without any hits
     */
    public void forceSend(int curHp, int maxHp) {
        hpBarRatio = toRatio(hpBarType, curHp, maxHp);
        forceSend = true;
    }

    @Override
    public void reset() {
        splats.clear();
        forceSend = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return forceSend || !splats.isEmpty();
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        if (playerUpdate)
            out.addByteA(splats.size());
        else
            out.addByteS(splats.size());
        for (Splat splat : splats) {
            out.addSmart(splat.type);
            out.addSmart(splat.damage);
            out.addSmart(splat.delay);
        }

        if (playerUpdate)
            out.addByte(1); //hp bar count
        else
            out.addByteA(1); //hp bar count
        out.addSmart(hpBarType);
        int timespan = 0;
        out.addSmart(timespan);
        if (timespan != 0x7FFF) {
            out.addSmart(0); //hp bar delay
            if (playerUpdate)
                out.addByteA(hpBarRatio);
            else
                out.addByteC(hpBarRatio);
            if (timespan > 0) {
                int destinationFillAmount = hpBarRatio;
                if (playerUpdate)
                    out.addByteS(destinationFillAmount);
                else
                    out.addByteA(destinationFillAmount);
            }
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 1 : 2;
    }

    private static final class Splat {

        private final int type;
        private final int damage;

        private final int delay;

        public Splat(int type, int damage, int delay) {
            this.type = type;
            this.damage = damage;
            this.delay = delay;
        }

    }

    /**
     * Ratio calc
     */
    private static final int[][] RATIO_DATA = {
            {30, 30},       //0
            {100, 100},     //1
            {120, 120},     //2
            {160, 160},     //3
            {120, 120},     //4
            {60, 60},       //5
            {100, 100},     //6
            {100, 100},     //7
            {120, 120},     //8
    };

    private static int toRatio(int type, int min, int max) {
        int ratio = (Math.min(min, max) * RATIO_DATA[type][0]) / max;
        if (ratio == 0 && min > 0)
            ratio = 1;
        return ratio;
    }

}