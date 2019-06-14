package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanWarp(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blinkDistance: TalismanPropertyInt = registerIntProperty("blink_distance", ConfigSpell.warp.maxDistance)

    override fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost * blinkDistance.getValue(itemStack);
    }

    override fun performActionServer(context: TalismanContext) {
        val pos = context.player.getPositionEyes(0f)
        val look = context.player.lookVec
        val distance = blinkDistance.getValue(context.stack)
        val target = BlockPos(pos.add(look.scale(distance.toDouble())))
        UtilEntity.teleport(context.player, target.down())
    }
}
