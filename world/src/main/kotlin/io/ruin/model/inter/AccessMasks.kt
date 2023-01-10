package io.ruin.model.inter

/**
 * @author Jire
 */
enum class AccessMasks(override val mask: Int) : AccessMask {

    None(0),
    Continue(1 shl 0),
    ClickOp1(1 shl 1),
    ClickOp2(1 shl 2),
    ClickOp3(1 shl 3),
    ClickOp4(1 shl 4),
    ClickOp5(1 shl 5),
    ClickOp6(1 shl 6),
    ClickOp7(1 shl 7),
    ClickOp8(1 shl 8),
    ClickOp9(1 shl 9),
    ClickOp10(1 shl 10),
    UseOnGroundItem(1 shl 11),
    UseOnNpc(1 shl 12),
    UseOnObject(1 shl 13),
    UseOnPlayer(1 shl 14),
    UseOnInventory(1 shl 15),
    UseOnComponent(1 shl 16),
    DragDepth1(1 shl 17),
    DragDepth2(2 shl 17),
    DragDepth3(3 shl 17),
    DragDepth4(4 shl 17),
    DragDepth5(5 shl 17),
    DragDepth6(6 shl 17),
    DragDepth7(7 shl 17),
    DragTargetable(1 shl 20),
    ComponentTargetable(1 shl 21);

    companion object {

        @JvmStatic
        fun determine(mask: Int) = values().filter { mask and it.mask != 0 }

        @JvmStatic
        @JvmOverloads
        fun determineString(
            mask: Int,
            prefix: String = AccessMasks::class.simpleName!! + '.',
            separator: String = ", "
        ) =
            determine(mask).joinToString(separator) { "$prefix${it.name}" }

        @JvmStatic
        fun combine(vararg masks: Int) = masks.sum()

        @JvmStatic
        fun combine(vararg masks: AccessMask) = masks.sumOf { it.mask }

    }

}