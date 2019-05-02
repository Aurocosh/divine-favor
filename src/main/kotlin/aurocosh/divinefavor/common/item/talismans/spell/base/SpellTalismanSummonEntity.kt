package aurocosh.divinefavor.common.item.talismans.spell.base

import aurocosh.divinefavor.common.lib.extensions.multicatch
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLiving
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.lang.reflect.InvocationTargetException
import java.util.*

open class SpellTalismanSummonEntity<T : EntityLiving>(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val clazz: Class<out T>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        try {
            val world = context.world
            val spawnPos = getPosition(context)

            val constructor = clazz.getConstructor(World::class.java)
            val entityLiving = constructor.newInstance(world)
            entityLiving.setLocationAndAngles(spawnPos.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0f, 0.0f)

            val difficulty = entityLiving.world.getDifficultyForLocation(entityLiving.position)
            entityLiving.onInitialSpawn(difficulty, null)

            preProcessEntity(entityLiving, context)
            world.spawnEntity(entityLiving)
            postProcessEntity(entityLiving, context)
        } catch (e: Throwable) {
            e.multicatch(InvocationTargetException::class, NoSuchMethodException::class, InstantiationException::class, IllegalAccessException::class) {
                e.printStackTrace()
            }
        }
    }

    protected fun getPosition(context: TalismanContext): BlockPos {
        return context.pos.offset(context.facing)
    }

    protected fun preProcessEntity(entityLiving: T, context: TalismanContext) {}

    protected open fun postProcessEntity(entityLiving: T, context: TalismanContext) {}
}
