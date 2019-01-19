package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.client.gui.*;
import aurocosh.divinefavor.client.gui.blocks.GuiFastFurnace;
import aurocosh.divinefavor.client.gui.blocks.GuiIronMedium;
import aurocosh.divinefavor.client.gui.items.GuiContractBinder;
import aurocosh.divinefavor.client.gui.items.GuiGrimoire;
import aurocosh.divinefavor.client.gui.items.GuiRitualPouch;
import aurocosh.divinefavor.common.block.fast_furnace.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.ContainerMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.item.contract_binder.ContractBinderContainer;
import aurocosh.divinefavor.common.item.grimoire.GrimoireContainer;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer;
import aurocosh.divinefavor.common.item.talismans.base.ContainerTalisman;
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
                EnumHand hand = EnumHand.MAIN_HAND;
                ItemStack grimoire = player.getHeldItem(EnumHand.MAIN_HAND);
                if (grimoire.isEmpty() || !(grimoire.getItem() instanceof ItemGrimoire)) {
                    grimoire = player.getHeldItem(EnumHand.OFF_HAND);
                    hand = EnumHand.OFF_HAND;
                }
                IGrimoireHandler handler = grimoire.getCapability(GrimoireDataHandler.CAPABILITY_GRIMOIRE, null);
                return new GrimoireContainer(player, handler, hand);
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
                EnumHand hand = EnumHand.MAIN_HAND;
                ItemStack grimoire = player.getHeldItem(EnumHand.MAIN_HAND);
                if (grimoire.isEmpty() || !(grimoire.getItem() instanceof ItemGrimoire)) {
                    grimoire = player.getHeldItem(EnumHand.OFF_HAND);
                    hand = EnumHand.OFF_HAND;
                }
                return new GuiGrimoire(player, grimoire, hand);
            }
        }
        return null;
    }
}
