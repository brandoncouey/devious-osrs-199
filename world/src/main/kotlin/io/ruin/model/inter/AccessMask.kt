package io.ruin.model.inter

/**
 * @author Jire
 */
interface AccessMask {

    val mask: Int

    infix fun and(accessMask: AccessMask): AccessMask = object : AccessMask {
        override val mask = this@AccessMask.mask and accessMask.mask
    }

    infix fun or(accessMask: AccessMask): AccessMask = object : AccessMask {
        override val mask = this@AccessMask.mask or accessMask.mask
    }

    infix fun xor(accessMask: AccessMask): AccessMask = object : AccessMask {
        override val mask = this@AccessMask.mask xor accessMask.mask
    }

}