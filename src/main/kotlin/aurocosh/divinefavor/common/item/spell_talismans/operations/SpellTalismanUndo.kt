package aurocosh.divinefavor.common.item.spell_talismans.operations

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent
import java.util.*

class SpellTalismanUndo(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun validate(context: CastContext): Boolean {
        return context.player.divinePlayerData.blockOperationsData.hasUndoActions()
    }

    override fun performActionServer(context: CastContext) {
        val (player, world) = context.get(playerField, worldField)
        val undoOperation = player.divinePlayerData.blockOperationsData.getUndoAction()
        undoOperation.perform(player, world)
    }

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        val player = event.harvester
        for (itemStack in event.drops)
            UtilPlayer.addGooToContainers(player, itemStack)

        event.drops.removeIf(ItemStack::isEmpty)
        event.drops.removeIf(player::addItemStackToInventory)
    }
}
