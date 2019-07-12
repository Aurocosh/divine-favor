package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent
import java.util.*

class SpellTalismanRedo(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun validate(context: TalismanContext): Boolean {
        return context.player.divinePlayerData.blockOperationsData.hasRedoActions()
    }

    override fun performActionServer(context: TalismanContext) {
        val (player, world) = context.get(playerField, worldField)
        val buildOperation = player.divinePlayerData.blockOperationsData.getRedoAction()
        buildOperation.perform(player, world)
    }

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        event.drops.removeIf(event.harvester::addItemStackToInventory)
    }
}
