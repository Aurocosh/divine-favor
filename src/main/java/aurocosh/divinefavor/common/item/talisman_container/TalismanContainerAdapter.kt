package aurocosh.divinefavor.common.item.talisman_container

import aurocosh.divinefavor.common.item.talisman_container.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireDataHandler
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object TalismanContainerAdapter {
    fun isItemValid(item: Item): Boolean {
        return item is ItemGrimoire || item is ItemSpellBow
    }

    fun getTalismanContainer(stack: ItemStack): ITalismanContainer? {
        return GrimoireDataHandler.get(stack) ?: SpellBowDataHandler.get(stack)
    }

    fun selectSlot(playerSlot: Int, talismanSlot: Int) {
        MessageSyncTalismanContainerSlot(playerSlot, talismanSlot).send()
    }
}
