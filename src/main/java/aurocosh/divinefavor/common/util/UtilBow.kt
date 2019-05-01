package aurocosh.divinefavor.common.util

object UtilBow {
    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    fun getArrowVelocity(charge: Int): Float {
        var velocity = charge.toFloat() / 20f
        velocity = (velocity * velocity + velocity * 2f) / 3f

        if (velocity > 1)
            velocity = 1f
        return velocity
    }
}
