package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
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
        val target = getBlinkTarget(context) ?: return
        UtilEntity.teleport(context.player, target)
    }

    private fun getBlinkTarget(context: TalismanContext): BlockPos? {
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
        val blinkDistance = ConfigSpell.surfaceBlink.distance
        return context.player.getDistanceSq(target) < blinkDistance * blinkDistance
    }
}
