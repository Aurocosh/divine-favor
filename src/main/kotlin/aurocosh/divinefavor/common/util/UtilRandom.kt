package aurocosh.divinefavor.common.util

import net.minecraft.util.math.Vec3d
import java.util.*

object UtilRandom {
    var random = Random()

    fun rollDiceFloat(chance: Float): Boolean {
        val value = random.nextFloat()
        return value < chance
    }

    fun rollDice(chance: Float): Boolean {
        val value = random.nextFloat() * 100
        return value < chance
    }

    fun nextInt(min: Int, max: Int): Int {
        return random.nextInt(max - min + 1) + min
    }

    fun nextDouble(min: Double, max: Double): Double {
        return min + (max - min) * random.nextDouble()
    }

    fun nextFloat(min: Float, max: Float): Float {
        return min + (max - min) * random.nextFloat()
    }

    fun nextIntExclusive(min: Int, max: Int): Int {
        return random.nextInt(max - min) + min
    }

    fun getRandomIndex(list: List<*>): Int {
        return nextInt(0, list.size - 1)
    }

    fun <T> getRandom(list: List<T>): T {
        return list[getRandomIndex(list)]
    }

    fun <T> getRandomIndex(list: Array<T>): Int {
        return nextInt(0, list.size - 1)
    }

    fun <T> getRandom(list: Array<T>): T? {
        return if (list.isEmpty()) null else list[getRandomIndex(list)]
    }

    fun nextDirection(): Vec3d {
        val x = nextFloat(-1f, 1f)
        val y = nextFloat(-1f, 1f)
        val z = nextFloat(-1f, 1f)
        return UtilVec3d.normalize(Vec3d(x.toDouble(), y.toDouble(), z.toDouble()))
    }
}
