package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.distributed_random.DistributedRandomList
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.block.Block
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.monster.EntityBlaze
import net.minecraft.entity.monster.EntityGhast
import net.minecraft.entity.monster.EntityPigZombie
import net.minecraft.entity.monster.EntityWitherSkeleton
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.lang.reflect.InvocationTargetException
import java.util.*

class SpellTalismanNetherSurge(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    private val possibleBlocks = DistributedRandomList<Block>()
    private val possibleEnemies = DistributedRandomList<Class<out EntityLiving>>()

    init {
        for ((key, value) in ConfigSpells.netherSurge.possibleBlocks) {
            val block = Block.getBlockFromName(key)
            if (block == null)
                DivineFavor.logger.error("Nether surge. Block not found:$key")
            else
                possibleBlocks.add(block, value)
        }

        possibleEnemies.add(EntityPigZombie::class.java, 0.65)
        possibleEnemies.add(EntityBlaze::class.java, 0.15)
        possibleEnemies.add(EntityWitherSkeleton::class.java, 0.1)
        possibleEnemies.add(EntityGhast::class.java, 0.1)
        possibleEnemies.add(EntityWither::class.java, 0.0001)
    }

    override fun performActionServer(context: TalismanContext) {
        val blocksSelect = UtilRandom.nextInt(ConfigSpells.netherSurge.minBlocksToReplace, ConfigSpells.netherSurge.maxBlocksToReplace)
        val blocksToReplace = UtilCoordinates.getRandomNeighbours(context.pos, blocksSelect, ConfigSpells.netherSurge.minNeighboursToAdd, ConfigSpells.netherSurge.maxNeighboursToAdd, CYCLE_LIMIT) { true }

        for (pos in blocksToReplace)
            UtilBlock.replaceBlock(context.player, context.world, pos, possibleBlocks.random!!)

        val mobsToSpawn = UtilRandom.nextInt(ConfigSpells.netherSurge.minEnemiesToSpawn, ConfigSpells.netherSurge.maxEnemiesToSpawn)
        for (i in 0 until mobsToSpawn)
            spawnNetherMob(context)
    }

    private fun spawnNetherMob(context: TalismanContext) {
        val spawnRadius = ConfigSpells.netherSurge.spawnRadius
        var spawnPos: BlockPos? = UtilCoordinates.getRandomNeighbour(context.pos, spawnRadius, 0, spawnRadius)
        if (spawnPos == null)
            return
        spawnPos = UtilCoordinates.findPlaceToStand(spawnPos, context.world, spawnRadius)
        if (spawnPos == null)
            return

        try {
            val clazz = possibleEnemies.random ?: return
            val constructor = clazz.getConstructor(World::class.java)
            val entityLiving = constructor.newInstance(context.world)
            entityLiving.setLocationAndAngles(spawnPos.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0f, 0.0f)
            context.world.spawnEntity(entityLiving)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val CYCLE_LIMIT = 5000
    }
}
