package aurocosh.divinefavor.common.network

import aurocosh.divinefavor.client.gui.blocks.GuiBathHeater
import aurocosh.divinefavor.client.gui.blocks.medium.GuiIronMediumNoStone
import aurocosh.divinefavor.client.gui.blocks.medium.GuiIronMediumWithStone
import aurocosh.divinefavor.client.gui.blocks.soulbound_lectern.GuiSoulboundLecternActive
import aurocosh.divinefavor.client.gui.blocks.soulbound_lectern.GuiSoulboundLecternEmpty
import aurocosh.divinefavor.client.gui.blocks.soulbound_lectern.GuiSoulboundLecternWithShard
import aurocosh.divinefavor.client.gui.items.*
import aurocosh.divinefavor.client.gui.talisman.GuiTalismanProperties
import aurocosh.divinefavor.common.block.bath_heater.ContainerBathHeater
import aurocosh.divinefavor.common.block.bath_heater.TileBathHeater
import aurocosh.divinefavor.common.block.medium.ContainerMediumNoStone
import aurocosh.divinefavor.common.block.medium.ContainerMediumWithStone
import aurocosh.divinefavor.common.block.medium.TileMedium
import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternActive
import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternEmpty
import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternWithShard
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.compressed_item_drop.CompressedItemsDropContainer
import aurocosh.divinefavor.common.item.contract_binder.ContractBinderContainer
import aurocosh.divinefavor.common.item.gem_pouch.GemPouchContainer
import aurocosh.divinefavor.common.item.gem_pouch.ItemGemPouch
import aurocosh.divinefavor.common.item.memory_drop.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_drop.MemoryDropContainer
import aurocosh.divinefavor.common.item.memory_pouch.ItemMemoryPouch
import aurocosh.divinefavor.common.item.memory_pouch.MemoryPouchNormalContainer
import aurocosh.divinefavor.common.item.memory_pouch.MemoryPouchTemplateContainer
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.GrimoireContainer
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.SpellBladeContainer
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler.CAPABILITY_SPELL_BLADE
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.SpellBowContainer
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.ItemSpellPick
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.SpellPickContainer
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability.SpellPickDataHandler.CAPABILITY_SPELL_PICK
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

class GuiHandler : IGuiHandler {
    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        when (ID) {
            ConstGuiIDs.IRON_MEDIUM_NO_STONE -> return ContainerMediumNoStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium))
            ConstGuiIDs.IRON_MEDIUM_WITH_STONE -> return ContainerMediumWithStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium))
            ConstGuiIDs.RITUAL_POUCH -> {
                val (_, stack) = UtilPlayer.getHeldItem(player) { it is ItemRitualPouch } ?: return null
                return RitualBagContainer(player, stack)
            }
            ConstGuiIDs.COMPRESSED_ITEMS_DROP -> {
                return CompressedItemsDropContainer(player)
            }
            ConstGuiIDs.CONTRACT_BINDER -> {
                val pouch = player.heldItemMainhand
                val handler = pouch.cap(ITEM_HANDLER_CAPABILITY)
                return ContractBinderContainer(player, handler)
            }
            ConstGuiIDs.GRIMOIRE -> {
                val (hand, stack) = UtilPlayer.getHeldItem(player) { it is ItemGrimoire } ?: return null
                return GrimoireContainer(player, stack, hand)
            }
            ConstGuiIDs.GEM_POUCH -> {
                val (hand, stack) = UtilPlayer.getHeldItem(player) { it is ItemGemPouch } ?: return null
                return GemPouchContainer(player, stack, hand)
            }
            ConstGuiIDs.SPELL_BLADE -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellBlade } ?: return null
                val stack = player.getHeldItem(hand)
                val handler = stack.cap(CAPABILITY_SPELL_BLADE)
                return SpellBladeContainer(player, handler, hand)
            }
            ConstGuiIDs.SPELL_PICK -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellPick } ?: return null
                val stack = player.getHeldItem(hand)
                val handler = stack.cap(CAPABILITY_SPELL_PICK)
                return SpellPickContainer(player, handler, hand)
            }
            ConstGuiIDs.SPELL_BOW -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellBow } ?: return null
                val stack = player.getHeldItem(hand)
                val handler = stack.cap(CAPABILITY_SPELL_BOW)
                return SpellBowContainer(player, handler, hand)
            }
            ConstGuiIDs.MEMORY_POUCH -> {
                val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryPouch } ?: return null
                val stack = player.getHeldItem(hand)
                val handler = stack.cap(CAPABILITY_MEMORY_POUCH)
                return MemoryPouchNormalContainer(player, handler, hand)
            }
            ConstGuiIDs.MEMORY_POUCH_TEMPLATE -> {
                val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryPouch } ?: return null
                val stack = player.getHeldItem(hand)
                val handler = stack.cap(CAPABILITY_MEMORY_POUCH)
                return MemoryPouchTemplateContainer(player, handler, hand)
            }
            ConstGuiIDs.MEMORY_DROP -> return MemoryDropContainer()
            ConstGuiIDs.SOULBOUND_LECTERN_ACTIVE -> return ContainerSoulboundLecternActive(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern))
            ConstGuiIDs.SOULBOUND_LECTERN_WITH_SHARD -> return ContainerSoulboundLecternWithShard(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern))
            ConstGuiIDs.SOULBOUND_LECTERN_EMPTY -> return ContainerSoulboundLecternEmpty(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern))
            ConstGuiIDs.BATH_HEATER -> return ContainerBathHeater(player, world.getTileEntity(BlockPos(x, y, z)) as TileBathHeater)
        }
        return null
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        when (ID) {
            ConstGuiIDs.IRON_MEDIUM_NO_STONE -> return GuiIronMediumNoStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium))
            ConstGuiIDs.IRON_MEDIUM_WITH_STONE -> return GuiIronMediumWithStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium))
            ConstGuiIDs.RITUAL_POUCH -> {
                val (_, stack) = UtilPlayer.getHeldItem(player) { it is ItemRitualPouch } ?: return null
                return GuiRitualPouch(player, stack)
            }
            ConstGuiIDs.COMPRESSED_ITEMS_DROP -> {
                return GuiCompressedItemsDrop(player)
            }
            ConstGuiIDs.CONTRACT_BINDER -> {
                val pouch = player.heldItemMainhand
                return GuiContractBinder(player, pouch)
            }
            ConstGuiIDs.GRIMOIRE -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemGrimoire } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiGrimoire(player, stack, hand)
            }
            ConstGuiIDs.GEM_POUCH -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemGemPouch } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiGemPouch(player, stack, hand)
            }
            ConstGuiIDs.SPELL_BLADE -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellBlade } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiSpellBlade(player, stack, hand)
            }
            ConstGuiIDs.SPELL_PICK -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellPick } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiSpellPick(player, stack, hand)
            }
            ConstGuiIDs.SPELL_BOW -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellBow } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiSpellBow(player, stack, hand)
            }
            ConstGuiIDs.MEMORY_POUCH -> {
                val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryPouch } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiMemoryPouchNormal(player, stack, hand)
            }
            ConstGuiIDs.MEMORY_POUCH_TEMPLATE -> {
                val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryPouch } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiMemoryPouchTemplate(player, stack, hand)
            }
            ConstGuiIDs.MEMORY_DROP -> {
                val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryDrop } ?: return null
                val stack = player.getHeldItem(hand)
                return GuiMemoryDrop(player, stack, hand)
            }
            ConstGuiIDs.SOULBOUND_LECTERN_ACTIVE -> return GuiSoulboundLecternActive(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern))
            ConstGuiIDs.SOULBOUND_LECTERN_WITH_SHARD -> return GuiSoulboundLecternWithShard(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern))
            ConstGuiIDs.SOULBOUND_LECTERN_EMPTY -> return GuiSoulboundLecternEmpty(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern))
            ConstGuiIDs.BATH_HEATER -> return GuiBathHeater(player, (world.getTileEntity(BlockPos(x, y, z)) as TileBathHeater))

            ConstGuiIDs.TALISMAN_HUD -> {
                val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { it.item is ISelectedStackProvider }
                if (stack.isEmpty)
                    return null
                val container = stack.item as ISelectedStackProvider
                val selectedStack = container.getSelectedStack(stack)
                if (selectedStack.isEmpty || selectedStack.item !is ItemTalisman)
                    return null
                return GuiTalismanProperties(selectedStack, slotIndex)
            }
        }
        return null
    }
}
