package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class SpellTalismanBladeOfGrass(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val radius = ConfigSpells.bladeOfGrass.radius
        val player = context.player
        val origin = player.position
        val axis = AxisAlignedBB((origin.x - radius).toDouble(), (origin.y - radius).toDouble(), (origin.z - radius).toDouble(), (origin.x + radius).toDouble(), (origin.y + radius).toDouble(), (origin.z + radius).toDouble())
        val list = context.world.getEntitiesWithinAABB(Entity::class.java, axis) { e: Entity? -> e != null && isValid(e, player, origin) }

        for (entity in list) {
            val positions = ArrayList<BlockPos>()
            val pos = entity.position.down()
            positions.add(pos)
            positions.add(pos.east())
            positions.add(pos.west())
            positions.add(pos.north())
            positions.add(pos.south())

            if (consumeGrass(positions, context.player, context.world)) {
                val base = entity as EntityLivingBase
                base.addPotionEffect(PotionEffect(MobEffects.SLOWNESS, ConfigSpells.bladeOfGrass.slownessTime, ConfigSpells.bladeOfGrass.slownessLevel))
                base.attackEntityFrom(DamageSource.causePlayerDamage(player), ConfigSpells.bladeOfGrass.damage)
            }
        }
    }

    private fun isValid(e: Entity, player: EntityPlayer, origin: BlockPos): Boolean {
        if (e !is EntityLivingBase)
            return false
        if (e === player)
            return false
        return if (!isInRadius(origin, e)) false else !e.isPotionActive(MobEffects.SLOWNESS)
    }

    private fun isInRadius(origin: BlockPos, entity: Entity): Boolean {
        val entityVec = entity.position
        return origin.distanceSq(entityVec) < RADIUS_SQ
    }

    private fun consumeGrass(positions: List<BlockPos>, player: EntityPlayer, world: World): Boolean {
        for (pos in positions) {
            val state = world.getBlockState(pos)
            if (state.material !== Material.GRASS)
                return false
            if (!UtilBlock.canBreakBlock(player, world, pos, false))
                return false
        }
        for (pos in positions)
            world.setBlockState(pos, Blocks.DIRT.defaultState)
        return true
    }

    companion object {

        private val RADIUS_SQ = ConfigSpells.bladeOfGrass.radius * ConfigSpells.bladeOfGrass.radius
    }
}
