package io.ruin.network.incoming;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.PackageLoader;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.IdHolder;

import java.util.Arrays;

public interface Incoming {

    Incoming[] HANDLERS = new Incoming[256];
    int[] OPTIONS = new int[256];
    Incoming[] HANDLERS_MOBILE = new Incoming[256];
    int[] OPTIONS_MOBILE = new int[256];
    boolean[] IGNORED = new boolean[256];
    int[] SIZES = new int[256];
    int[] MOBILE_SIZES = new int[256];

    static void load() throws Exception {
        for (Class c : PackageLoader.load("io.ruin.network.incoming.desktop.handlers", Incoming.class)) {
            Incoming incoming = (Incoming) c.newInstance();
            IdHolder idHolder = (IdHolder) c.getAnnotation(IdHolder.class);
            if (idHolder == null) {
                /* handler is disabled, most likely for upgrading */
                continue;
            }
            int option = 1;
            for (int id : idHolder.ids()) {
                HANDLERS[id] = incoming;
                OPTIONS[id] = option++;
            }
        }
        for (Class c : PackageLoader.load("io.ruin.network.incoming.mobile.handlers", Incoming.class)) {
            Incoming incoming = (Incoming) c.newInstance();
            IdHolder idHolder = (IdHolder) c.getAnnotation(IdHolder.class);
            if (idHolder == null) {
                /* handler is disabled, most likely for upgrading */
                continue;
            }
            int option = 1;
            for (int id : idHolder.ids()) {
                HANDLERS_MOBILE[id] = incoming;
                OPTIONS_MOBILE[id] = option++;
            }
        }

        /**
         * Ignored
         */
        int[] ignored = {
/*                3,
                4,
                15,
                23,
                25,
                26,
                40,
                41*/
                82,
                86,
                61,
                64,
                7,
                17,
                1,
                58
        };
        for (int opcode : ignored)
            IGNORED[opcode] = true;
        /**
         * Sizes
         */
        Arrays.fill(SIZES, Byte.MIN_VALUE);

        SIZES[0] = -1;
        SIZES[1] = -2;
        SIZES[2] = 8;
        SIZES[3] = 15;
        SIZES[4] = 16;
        SIZES[5] = -1;
        SIZES[6] = 3;
        SIZES[7] = 4;
        SIZES[8] = 3;
        SIZES[9] = 7;
        SIZES[10] = 8;
        SIZES[11] = 3;
        SIZES[12] = 16;
        SIZES[13] = 8;
        SIZES[14] = -1;
        SIZES[15] = 16;
        SIZES[16] = 2;
        SIZES[17] = 6;
        SIZES[18] = 3;
        SIZES[19] = 7;
        SIZES[20] = 15;
        SIZES[21] = -1;
        SIZES[22] = 4;
        SIZES[23] = 8;
        SIZES[24] = 5;
        SIZES[25] = 4;
        SIZES[26] = -1;
        SIZES[27] = -1;
        SIZES[28] = 8;
        SIZES[29] = 7;
        SIZES[30] = 3;
        SIZES[31] = 8;
        SIZES[32] = -1;
        SIZES[33] = 6;
        SIZES[34] = 8;
        SIZES[35] = -1;
        SIZES[36] = 3;
        SIZES[37] = 7;
        SIZES[38] = 2;
        SIZES[39] = 8;
        SIZES[40] = 11;
        SIZES[41] = 7;
        SIZES[42] = 8;
        SIZES[43] = 15;
        SIZES[44] = 7;
        SIZES[45] = 3;
        SIZES[46] = 3;
        SIZES[47] = 8;
        SIZES[48] = 4;
        SIZES[49] = -1;
        SIZES[50] = 2;
        SIZES[51] = 8;
        SIZES[52] = 3;
        SIZES[53] = 11;
        SIZES[54] = 8;
        SIZES[55] = -1;
        SIZES[56] = 8;
        SIZES[57] = -1;
        SIZES[58] = 0;
        SIZES[59] = -1;
        SIZES[60] = 4;
        SIZES[61] = 1;
        SIZES[62] = 9;
        SIZES[63] = 9;
        SIZES[64] = 0;
        SIZES[65] = 8;
        SIZES[66] = -1;
        SIZES[67] = 0;
        SIZES[68] = 13;
        SIZES[69] = 7;
        SIZES[70] = 3;
        SIZES[71] = -2;
        SIZES[72] = 7;
        SIZES[73] = 0;
        SIZES[74] = -1;
        SIZES[75] = 14;
        SIZES[76] = 10;
        SIZES[77] = -1;
        SIZES[78] = 8;
        SIZES[79] = -1;
        SIZES[80] = 7;
        SIZES[81] = 2;
        SIZES[82] = -1;
        SIZES[83] = 8;
        SIZES[84] = 7;
        SIZES[85] = 2;
        SIZES[86] = -1;
        SIZES[87] = -1;
        SIZES[88] = 3;
        SIZES[89] = 0;
        SIZES[90] = 3;
        SIZES[91] = -2;
        SIZES[92] = 8;
        SIZES[93] = -1;
        SIZES[94] = -1;
        SIZES[95] = 3;
        SIZES[96] = 8;
        SIZES[97] = -1;
        SIZES[98] = 8;
        SIZES[99] = -1;
        SIZES[100] = 7;
        SIZES[101] = 3;
        SIZES[102] = 15;
        SIZES[103] = 11;
        SIZES[104] = 8;
        SIZES[105] = 11;
        MOBILE_SIZES[0] = 3;
        MOBILE_SIZES[1] = -1;
        MOBILE_SIZES[2] = -1;
        MOBILE_SIZES[3] = -1;
        MOBILE_SIZES[4] = 0;
        MOBILE_SIZES[5] = 7;
        MOBILE_SIZES[6] = -1;
        MOBILE_SIZES[7] = 8;
        MOBILE_SIZES[8] = -2;
        MOBILE_SIZES[9] = 8;
        MOBILE_SIZES[10] = 15;
        MOBILE_SIZES[11] = 7;
        MOBILE_SIZES[12] = 7;
        MOBILE_SIZES[13] = -1;
        MOBILE_SIZES[14] = 16;
        MOBILE_SIZES[15] = 7;
        MOBILE_SIZES[16] = 8;
        MOBILE_SIZES[17] = -1;
        MOBILE_SIZES[18] = 15;
        MOBILE_SIZES[19] = 8;
        MOBILE_SIZES[20] = 3;
        MOBILE_SIZES[21] = 2;
        MOBILE_SIZES[22] = 9;
        MOBILE_SIZES[23] = 8;
        MOBILE_SIZES[24] = 3;
        MOBILE_SIZES[25] = 8;
        MOBILE_SIZES[26] = 7;
        MOBILE_SIZES[27] = 8;
        MOBILE_SIZES[28] = -2;
        MOBILE_SIZES[29] = 3;
        MOBILE_SIZES[30] = 9;
        MOBILE_SIZES[31] = -1;
        MOBILE_SIZES[32] = -1;
        MOBILE_SIZES[33] = 2;
        MOBILE_SIZES[34] = 8;
        MOBILE_SIZES[35] = 8;
        MOBILE_SIZES[36] = 1;
        MOBILE_SIZES[37] = 11;
        MOBILE_SIZES[38] = 4;
        MOBILE_SIZES[39] = 3;
        MOBILE_SIZES[40] = 15;
        MOBILE_SIZES[41] = -1;
        MOBILE_SIZES[42] = 11;
        MOBILE_SIZES[43] = 7;
        MOBILE_SIZES[44] = 7;
        MOBILE_SIZES[45] = 7;
        MOBILE_SIZES[46] = 3;
        MOBILE_SIZES[47] = 3;
        MOBILE_SIZES[48] = 3;
        MOBILE_SIZES[49] = 11;
        MOBILE_SIZES[50] = -2;
        MOBILE_SIZES[51] = 4;
        MOBILE_SIZES[52] = 8;
        MOBILE_SIZES[53] = 16;
        MOBILE_SIZES[54] = 5;
        MOBILE_SIZES[55] = 8;
        MOBILE_SIZES[56] = 8;
        MOBILE_SIZES[57] = -1;
        MOBILE_SIZES[58] = -1;
        MOBILE_SIZES[59] = 7;
        MOBILE_SIZES[60] = 8;
        MOBILE_SIZES[61] = 8;
        MOBILE_SIZES[62] = 3;
        MOBILE_SIZES[63] = 8;
        MOBILE_SIZES[64] = 2;
        MOBILE_SIZES[65] = 14;
        MOBILE_SIZES[66] = 6;
        MOBILE_SIZES[67] = -1;
        MOBILE_SIZES[68] = 0;
        MOBILE_SIZES[69] = 2;
        MOBILE_SIZES[70] = -1;
        MOBILE_SIZES[71] = 16;
        MOBILE_SIZES[72] = 3;
        MOBILE_SIZES[73] = 0;
        MOBILE_SIZES[74] = 10;
        MOBILE_SIZES[75] = 3;
        MOBILE_SIZES[76] = 3;
        MOBILE_SIZES[77] = 15;
        MOBILE_SIZES[78] = -1;
        MOBILE_SIZES[79] = -1;
        MOBILE_SIZES[80] = 8;
        MOBILE_SIZES[81] = 8;
        MOBILE_SIZES[82] = 4;
        MOBILE_SIZES[83] = 8;
        MOBILE_SIZES[84] = -1;
        MOBILE_SIZES[85] = -1;
        MOBILE_SIZES[86] = 13;
        MOBILE_SIZES[87] = 2;
        MOBILE_SIZES[88] = -1;
        MOBILE_SIZES[89] = 11;
        MOBILE_SIZES[90] = -1;
        MOBILE_SIZES[91] = 0;
        MOBILE_SIZES[92] = -1;
        MOBILE_SIZES[93] = 1;
        MOBILE_SIZES[94] = 0;
        MOBILE_SIZES[95] = 4;
        MOBILE_SIZES[96] = -1;
        MOBILE_SIZES[97] = 3;
        MOBILE_SIZES[98] = 3;
        MOBILE_SIZES[99] = 7;
        MOBILE_SIZES[100] = 6;
        MOBILE_SIZES[101] = -1;
        MOBILE_SIZES[102] = -1;
        MOBILE_SIZES[103] = 8;
        MOBILE_SIZES[104] = 8;
        MOBILE_SIZES[105] = 7;

    }

    /**
     * Separator
     */

    void handle(Player player, InBuffer in, int opcode);

}
