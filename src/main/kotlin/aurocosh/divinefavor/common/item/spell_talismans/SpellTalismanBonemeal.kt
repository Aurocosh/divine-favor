package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemDye
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBonemeal(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        if (!bonemeal(context.player, context.pos))
            bonemeal(context.player, context.pos.down())
    }

    fun bonemeal(player: EntityPlayer, pos: BlockPos): Boolean {
        val success = ItemDye.applyBonemeal(ItemStack(Items.DYE, 1, 15), player.entityWorld, pos)
        if (success)
            player.entityWorld.playEvent(2005, pos, 0)
        return success
    }
}
