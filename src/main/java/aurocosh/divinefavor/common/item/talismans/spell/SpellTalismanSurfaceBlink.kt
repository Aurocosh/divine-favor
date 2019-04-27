package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanSurfaceBlink(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    public override fun validate(context: TalismanContext): Boolean {
        val target = getBlinkTarget(context)
        return canWarp(target, context)
    }

    override fun performActionServer(context: TalismanContext) {
        val target = getBlinkTarget(context)
        UtilEntity.teleport(context.player, target)
    }

    private fun getBlinkTarget(context: TalismanContext): BlockPos? {
        if (context.pos == null)
            return null
        if (context.facing == null)
            return null
        val target = context.pos.offset(context.facing)
        if (context.facing == EnumFacing.DOWN)
            return target.down()
        return if (context.facing == EnumFacing.UP) target else target.down()
    }

    private fun canWarp(target: BlockPos?, context: TalismanContext): Boolean {
        if (target == null)
            return false
        if (!context.world.isAirBlock(target))
            return false
        if (!context.world.isAirBlock(target.up()))
            return false
        val blinkDistance = ConfigSpells.surfaceBlink.distance
        return context.player.getDistanceSq(target) < blinkDistance * blinkDistance
    }
}
