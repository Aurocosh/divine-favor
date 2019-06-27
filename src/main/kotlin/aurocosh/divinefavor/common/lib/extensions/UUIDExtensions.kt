package aurocosh.divinefavor.common.lib.extensions

import java.util.*

fun UUID.isValid() = !this.isInvalid()
fun UUID.isInvalid() = this.leastSignificantBits + this.mostSignificantBits == 0L
