package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBlink(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val isSafe: Boolean, private val blinkDistance: Double) : ItemSpellTalisman(name, spirit, favorCost, options) {

    public override fun validate(context: TalismanContext): Boolean {
        val world = context.world
        val target = getBlinkTarget(context.player)
        return !isSafe || world.isAirBlock(target) && world.isAirBlock(target.up())
    }

    override fun performActionServer(context: TalismanContext) {
        val target = getBlinkTarget(context.player)
        UtilEntity.teleport(context.player, target.down())
    }

    private fun getBlinkTarget(player: EntityPlayer): BlockPos {
        val pos = player.getPositionEyes(0f)
        val look = player.lookVec
        return BlockPos(pos.add(look.scale(blinkDistance)))
    }
}
