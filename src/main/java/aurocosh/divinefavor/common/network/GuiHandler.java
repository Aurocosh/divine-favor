package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.client.gui.*;
import aurocosh.divinefavor.client.gui.blocks.GuiFastFurnace;
import aurocosh.divinefavor.client.gui.blocks.GuiIronMedium;
import aurocosh.divinefavor.client.gui.items.GuiContractBinder;
import aurocosh.divinefavor.client.gui.items.GuiGrimoire;
import aurocosh.divinefavor.client.gui.items.GuiRitualPouch;
import aurocosh.divinefavor.client.gui.items.GuiSpellBow;
import aurocosh.divinefavor.common.block.fast_furnace.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.ContainerMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.item.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.item.contract_binder.ContractBinderContainer;
import aurocosh.divinefavor.common.item.grimoire.GrimoireContainer;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer;
import aurocosh.divinefavor.common.item.spell_bow.SpellBowContainer;
import aurocosh.divinefavor.common.item.spell_bow.capability.ISpellBowHandler;
import aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowDataHandler;
import aurocosh.divinefavor.common.item.talismans.spell.base.ContainerTalisman;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ConstGuiIDs.IRON_MEDIUM:
                return new ContainerMedium(player, (TileMedium) world.getTileEntity(new BlockPos(x, y, z)));
            case ConstGuiIDs.FAST_FURNACE:
                return new ContainerFastFurnace(player, (TileFastFurnace) world.getTileEntity(new BlockPos(x, y, z)));
            case ConstGuiIDs.TALISMAN:
                return new ContainerTalisman(player, player.getHeldItem(EnumHand.MAIN_HAND));
            case ConstGuiIDs.RITUAL_POUCH: {
                ItemStack pouch = player.getHeldItemMainhand();
                IItemHandler handler = pouch.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                return new RitualBagContainer(player, handler);
            }
            case ConstGuiIDs.CONTRACT_BINDER: {
                ItemStack pouch = player.getHeldItemMainhand();
                IItemHandler handler = pouch.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                return new ContractBinderContainer(player, handler);
            }
            case ConstGuiIDs.GRIMOIRE: {
                EnumHand hand = UtilPlayer.getHand(h -> player.getHeldItem(h).getItem() instanceof ItemGrimoire);
                ItemStack stack = player.getHeldItem(hand);
                IGrimoireHandler handler = stack.getCapability(GrimoireDataHandler.CAPABILITY_GRIMOIRE, null);
                return new GrimoireContainer(player, handler, hand);
            }
            case ConstGuiIDs.SPELL_BOW: {
                EnumHand hand = UtilPlayer.getHand(h -> player.getHeldItem(h).getItem() instanceof ItemSpellBow);
                ItemStack stack = player.getHeldItem(hand);
                ISpellBowHandler handler = stack.getCapability(SpellBowDataHandler.CAPABILITY_SPELL_BOW, null);
                return new SpellBowContainer(player, handler, hand);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ConstGuiIDs.IRON_MEDIUM:
                return new GuiIronMedium(player, (TileMedium) world.getTileEntity(new BlockPos(x, y, z)));
            case ConstGuiIDs.FAST_FURNACE:
                return new GuiFastFurnace(player, (TileFastFurnace) world.getTileEntity(new BlockPos(x, y, z)));
            case ConstGuiIDs.TALISMAN:
                return new GuiTalismanContainer(player, player.getHeldItem(EnumHand.MAIN_HAND));
            case ConstGuiIDs.RITUAL_POUCH: {
                ItemStack pouch = player.getHeldItemMainhand();
                return new GuiRitualPouch(player, pouch);
            }
            case ConstGuiIDs.CONTRACT_BINDER: {
                ItemStack pouch = player.getHeldItemMainhand();
                return new GuiContractBinder(player, pouch);
            }
            case ConstGuiIDs.GRIMOIRE: {
                EnumHand hand = UtilPlayer.getHand(h -> player.getHeldItem(h).getItem() instanceof ItemGrimoire);
                ItemStack stack = player.getHeldItem(hand);
                return new GuiGrimoire(player, stack, hand);
            }
            case ConstGuiIDs.SPELL_BOW: {
                EnumHand hand = UtilPlayer.getHand(h -> player.getHeldItem(h).getItem() instanceof ItemSpellBow);
                ItemStack stack = player.getHeldItem(hand);
                return new GuiSpellBow(player, stack, hand);
            }
        }
        return null;
    }
}
