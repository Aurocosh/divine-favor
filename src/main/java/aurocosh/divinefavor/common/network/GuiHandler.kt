package aurocosh.divinefavor.common.network

import aurocosh.divinefavor.client.gui.blocks.GuiBathHeater
import aurocosh.divinefavor.client.gui.blocks.medium.GuiIronMediumNoStone
import aurocosh.divinefavor.client.gui.blocks.medium.GuiIronMediumWithStone
import aurocosh.divinefavor.client.gui.blocks.soulbound_lectern.GuiSoulboundLecternActive
import aurocosh.divinefavor.client.gui.blocks.soulbound_lectern.GuiSoulboundLecternEmpty
import aurocosh.divinefavor.client.gui.blocks.soulbound_lectern.GuiSoulboundLecternWithShard
import aurocosh.divinefavor.client.gui.items.GuiContractBinder
import aurocosh.divinefavor.client.gui.items.GuiGrimoire
import aurocosh.divinefavor.client.gui.items.GuiRitualPouch
import aurocosh.divinefavor.client.gui.items.GuiSpellBow
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
import aurocosh.divinefavor.common.item.contract_binder.ContractBinderContainer
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer
import aurocosh.divinefavor.common.item.talisman_container.grimoire.GrimoireContainer
import aurocosh.divinefavor.common.item.talisman_container.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireDataHandler
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.SpellBowContainer
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.items.CapabilityItemHandler

class GuiHandler : IGuiHandler {

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        when (ID) {
            ConstGuiIDs.IRON_MEDIUM_NO_STONE -> return ContainerMediumNoStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium?)!!)
            ConstGuiIDs.IRON_MEDIUM_WITH_STONE -> return ContainerMediumWithStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium?)!!)
            ConstGuiIDs.RITUAL_POUCH -> {
                val pouch = player.heldItemMainhand
                val handler = pouch.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                return RitualBagContainer(player, handler!!)
            }
            ConstGuiIDs.CONTRACT_BINDER -> {
                val pouch = player.heldItemMainhand
                val handler = pouch.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                return ContractBinderContainer(player, handler!!)
            }
            ConstGuiIDs.GRIMOIRE -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemGrimoire }
                val stack = player.getHeldItem(hand)
                val handler = stack.getCapability(GrimoireDataHandler.CAPABILITY_GRIMOIRE!!, null)
                return GrimoireContainer(player, handler!!, hand!!)
            }
            ConstGuiIDs.SPELL_BOW -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellBow }
                val stack = player.getHeldItem(hand)
                val handler = stack.getCapability(SpellBowDataHandler.CAPABILITY_SPELL_BOW!!, null)
                return SpellBowContainer(player, handler!!, hand!!)
            }
            ConstGuiIDs.SOULBOUND_LECTERN_ACTIVE -> return ContainerSoulboundLecternActive(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern?)!!)
            ConstGuiIDs.SOULBOUND_LECTERN_WITH_SHARD -> return ContainerSoulboundLecternWithShard(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern?)!!)
            ConstGuiIDs.SOULBOUND_LECTERN_EMPTY -> return ContainerSoulboundLecternEmpty(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern?)!!)
            ConstGuiIDs.BATH_HEATER -> return ContainerBathHeater(player, world.getTileEntity(BlockPos(x, y, z)) as TileBathHeater?)
        }
        return null
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        when (ID) {
            ConstGuiIDs.IRON_MEDIUM_NO_STONE -> return GuiIronMediumNoStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium?)!!)
            ConstGuiIDs.IRON_MEDIUM_WITH_STONE -> return GuiIronMediumWithStone(player, (world.getTileEntity(BlockPos(x, y, z)) as TileMedium?)!!)
            ConstGuiIDs.RITUAL_POUCH -> {
                val pouch = player.heldItemMainhand
                return GuiRitualPouch(player, pouch)
            }
            ConstGuiIDs.CONTRACT_BINDER -> {
                val pouch = player.heldItemMainhand
                return GuiContractBinder(player, pouch)
            }
            ConstGuiIDs.GRIMOIRE -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemGrimoire }
                val stack = player.getHeldItem(hand)
                return GuiGrimoire(player, stack, hand!!)
            }
            ConstGuiIDs.SPELL_BOW -> {
                val hand = UtilPlayer.getHand { h -> player.getHeldItem(h).item is ItemSpellBow }
                val stack = player.getHeldItem(hand)
                return GuiSpellBow(player, stack, hand!!)
            }
            ConstGuiIDs.SOULBOUND_LECTERN_ACTIVE -> return GuiSoulboundLecternActive(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern?)!!)
            ConstGuiIDs.SOULBOUND_LECTERN_WITH_SHARD -> return GuiSoulboundLecternWithShard(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern?)!!)
            ConstGuiIDs.SOULBOUND_LECTERN_EMPTY -> return GuiSoulboundLecternEmpty(player, (world.getTileEntity(BlockPos(x, y, z)) as TileSoulboundLectern?)!!)
            ConstGuiIDs.BATH_HEATER -> return GuiBathHeater(player, (world.getTileEntity(BlockPos(x, y, z)) as TileBathHeater?)!!)
        }
        return null
    }
}
