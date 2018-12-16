package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.client.gui.*;
import aurocosh.divinefavor.common.block.fast_furnace.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.ContainerMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.item.grimoire.GrimoireContainer;
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer;
import aurocosh.divinefavor.common.item.talismans.ContainerTalisman;
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
            case ConstGuiIDs.GRIMOIRE: {
                ItemStack grimoire = player.getHeldItemMainhand();
                IItemHandler handler = grimoire.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                return new GrimoireContainer(player, handler);
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
            case ConstGuiIDs.GRIMOIRE: {
                ItemStack pouch = player.getHeldItemMainhand();
                return new GuiGrimoire(player, pouch);
            }
        }
        return null;
    }
}
