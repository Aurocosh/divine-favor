package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanLifeStealArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, 0.0, options, arrowType) {

    override fun init(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        val compound = spellArrow.talismanDataServer
        compound.setBoolean(TAG_STOLE_LIFE, false)
    }

    override fun performActionClient(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos, sideHit: EnumFacing): Boolean {
        if (target == null)
            return true
        spellArrow.setEntityIgnoreDelay(ENTITY_IGNORE_DELAY)
        return false
    }

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos, sideHit: EnumFacing): Boolean {
        if (target == null)
            return true
        target.attackEntityFrom(DamageSource.causePlayerDamage(shooter as EntityPlayer), ConfigArrow.lifeStealArrow.damage)
        spellArrow.setEntityIgnoreDelay(ENTITY_IGNORE_DELAY)
        val compound = spellArrow.talismanDataServer
        compound.setBoolean(TAG_STOLE_LIFE, true)
        return false
    }

    override fun onCollideWithPlayer(spellArrow: EntitySpellArrow, player: EntityPlayer): Boolean {
        val compound = spellArrow.talismanDataServer
        val stoleLife = compound.getBoolean(TAG_STOLE_LIFE)
        if (stoleLife)
            player.heal(ConfigArrow.lifeStealArrow.heal)
        return super.onCollideWithPlayer(spellArrow, player)
    }

    companion object {
        private val TAG_STOLE_LIFE = "StoleLife"
        private val ENTITY_IGNORE_DELAY = UtilTick.minutesToTicks(1f)
    }
}
