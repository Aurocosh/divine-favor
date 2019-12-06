package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilExperience
import aurocosh.divinefavor.common.util.UtilItemStack
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import java.util.*
import kotlin.math.min


class SpellTalismanCrystallizeExperience(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun getFinalFavorCost(context: CastContext): Int {
        val dropCount = getDropCount(context.player)
        return dropCount * favorCost
    }

    private fun getDropCount(player: EntityPlayer): Int {
        if (!player.isSneaking)
            return 1

        val dropsAvailableExperience = UtilExperience.getPlayerXP(player) / ConfigSpell.crystallizeExperience.experiencePerCast
        val spiritData = player.divinePlayerData.spiritData
        val dropsAvailableFavor = if (spiritData.isFavorInfinite(spiritId)) Int.MAX_VALUE else spiritData.getFavor(spiritId) / favorCost
        return min(dropsAvailableExperience, dropsAvailableFavor)
    }

    override fun validate(context: CastContext): Boolean {
        val player = context.player
        return player.capabilities.isCreativeMode || UtilExperience.getPlayerXP(player) >= ConfigSpell.crystallizeExperience.experiencePerCast
    }

    override fun performActionServer(context: CastContext) {
        val player = context.player
        val dropCount = getDropCount(context.player)
        UtilExperience.removePlayerXP(player, ConfigSpell.crystallizeExperience.experiencePerCast * dropCount)

        val stack = ItemStack(ModItems.experience_drop)
        val stacks = UtilItemStack.splitStack(stack, dropCount)

        for (dropStack in stacks)
            UtilPlayer.addStackToInventoryOrDrop(player, dropStack)
    }
}
