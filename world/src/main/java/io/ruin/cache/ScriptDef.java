package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.io.PrintStream;
import java.util.HashMap;

public class ScriptDef {

    public static ScriptDef[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(12);
        LOADED = new ScriptDef[index.getLastArchiveId() + 1];
        for (int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(id, 0);
            if (data == null) {
                //  System.err.println("CS2 " + id + " has null data!");
                continue;
            }
            ScriptDef def = new ScriptDef();
            def.id = id;
            def.decode(data);
            LOADED[id] = def;
        }
    }

    public static ScriptDef get(int id) {
        if (id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    public void print(PrintStream ps) {
        ps.println("Script " + id + " Instructions:");
        for (int i = 0; i < instructions.length; i++) {
            int instructionId = instructions[i];
            String instructionName = INSTRUCTIONS.get(instructionId);
            if (instructionName == null)
                instructionName = "?" + instructionId;
            StringBuilder sb = new StringBuilder(instructionName);
            if (stringOperands[i] != null)
                sb.append("(s:\"").append(stringOperands[i]).append("\")");
            else
                sb.append("(i:").append(intOperands[i]).append(")");
            ps.println("[" + i + "] " + sb);
        }
    }

    /**
     * Separator
     */

    public int id;
    int localIntCount;
    public String[] stringOperands;
    public int[] instructions;
    int localStringCount;
    int intStackCount;
    int stringStackCount;
    public int[] intOperands;

    private void decode(byte[] data) {
        InBuffer buffer = new InBuffer(data);
        buffer.position(data.length - 2);
        int switchLength = buffer.readUnsignedShort();
        // 2 for switchLength + the switch data + 12 for the param/vars/stack data
        int endIdx = data.length - 2 - switchLength - 12;
        buffer.position(endIdx);
        int numOpcodes = buffer.readInt();
        localIntCount = buffer.readUnsignedShort();
        localStringCount = buffer.readUnsignedShort();
        intStackCount = buffer.readUnsignedShort();
        stringStackCount = buffer.readUnsignedShort();
        int numSwitches = buffer.readUnsignedByte();
        if (numSwitches > 0) {
            for (int i = 0; i < numSwitches; i++) {
                int count = buffer.readUnsignedShort();
                while (count-- > 0) {
                    buffer.readInt(); // key, int from stack is compared to this
                    buffer.readInt(); // pcOffset, pc jumps by this
                }
            }
        }
        buffer.position(0);
        buffer.readSafeString();
        instructions = new int[numOpcodes];
        intOperands = new int[numOpcodes];
        stringOperands = new String[numOpcodes];
        int i = 0;
        while (buffer.position() < endIdx) {
            int opcode = buffer.readUnsignedShort();
            if (opcode == 3)
                stringOperands[i] = buffer.readString();
            else if (opcode < 100 && opcode != 21 && opcode != 38 && opcode != 39)
                intOperands[i] = buffer.readInt();
            else
                intOperands[i] = buffer.readUnsignedByte();
            instructions[i++] = opcode;
        }
    }

    /**
     * Identified instructions
     */

    private static final HashMap<Integer, String> INSTRUCTIONS = new HashMap<Integer, String>() {
        {
            put(0, "push_int");
            put(1, "push_config");
            put(2, "pop_config");
            put(3, "push_string");
            put(6, "jump_relative");
            put(7, "jump_ne");
            put(8, "jump_eq");
            put(9, "jump_lt");
            put(10, "jump_gt");
            put(21, "return");
            put(25, "push_varbit");
            put(27, "set_varbit");
            put(31, "jump_lte");
            put(32, "jump_gte");
            put(33, "push_intvar");
            put(34, "pop_intvar");
            put(35, "push_stringvar");
            put(36, "pop_stringvar");
            put(37, "strcat");
            put(38, "popint");
            put(39, "popstring");
            put(40, "run_script");
            put(42, "push_script_int");
            put(43, "pop_script_int");
            put(47, "push_script_str");
            put(48, "pop_script_str");
            put(2003, "set_hidden");
            put(2112, "set_interface_string");
            put(2419, "set_keypress_script");
            put(2423, "set_open_script");
            put(2504, "push_hidden");
            put(3301, "get_container_item");
            put(3302, "get_container_amount");
            put(3313, "get_alt_container_item");
            put(3314, "get_alt_container_amount");
            put(3400, "get_enum_str");
            put(3408, "get_enum_val");
            put(4014, "bit_and");
            put(4010, "check_bit");
        }
    };

}
