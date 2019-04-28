package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.math.MathHelper

class IncrementalChanceCounter(private var startingChance: Float, private var chanceIncrease: Float) {
    private var chance: Float = 0.0f

    init {
        reset()
    }

    fun getChance(): Float {
        return chance
    }

    fun getChanceIncrease(): Float {
        return chanceIncrease
    }

    fun getStartingChance(): Float {
        return startingChance
    }

    fun setChance(chance: Float) {
        this.chance = MathHelper.clamp(chance, 0f, 1f)
    }

    fun setChanceIncrease(chanceIncrease: Float) {
        this.chanceIncrease = MathHelper.clamp(chanceIncrease, 0f, 1f)
    }

    fun setStartingChance(startingChance: Float) {
        this.startingChance = MathHelper.clamp(startingChance, 0f, 1f)
    }

    fun reset() {
        chance = startingChance
    }

    fun tryLuck(): Boolean {
        println(toString())
        if (UtilRandom.rollDiceFloat(chance))
            return true
        if (chance >= 1)
            return true
        chance += chanceIncrease
        return false
    }

    override fun toString(): String {
        return "IncrementalChanceCounter{" +
                "chance=" + chance +
                ", chanceIncrease=" + chanceIncrease +
                ", startingChance=" + startingChance +
                '}'.toString()
    }
}
