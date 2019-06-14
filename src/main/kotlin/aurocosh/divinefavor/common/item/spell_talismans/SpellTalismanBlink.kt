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

class SpellTalismanBlink(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blinkDistance: TalismanPropertyInt = registerIntProperty("blink_distance", ConfigSpell.blink.maxDistance)

    override fun getFavorCost(itemStack: ItemStack): Int {
        val distance = blinkDistance.getValue(itemStack)
        return ConfigSpell.blink.favorCost * distance;
    }

    public override fun validate(context: TalismanContext): Boolean {
        val world = context.world
        val target = getBlinkTarget(context)
        return world.isAirBlock(target) && world.isAirBlock(target.up())
    }

    override fun performActionServer(context: TalismanContext) {
        val target = getBlinkTarget(context)
        UtilEntity.teleport(context.player, target.down())
    }

    private fun getBlinkTarget(context: TalismanContext): BlockPos {
        val player = context.player
        val pos = player.getPositionEyes(0f)
        val look = player.lookVec
        val distance = blinkDistance.getValue(context.stack)
        return BlockPos(pos.add(look.scale(distance.toDouble())))
    }
}
