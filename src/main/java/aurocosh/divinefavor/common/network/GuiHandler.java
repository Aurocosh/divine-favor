package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.client.gui.GuiFastFurnace;
import aurocosh.divinefavor.client.gui.GuiIronMedium;
import aurocosh.divinefavor.client.gui.GuiRitualPouch;
import aurocosh.divinefavor.client.gui.GuiTalismanContainer;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.block.fast_furnace.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.medium.ContainerMedium;
import aurocosh.divinefavor.common.constants.LibGuiIDs;
import aurocosh.divinefavor.common.container.ContainerTalisman;
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer;
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
		switch(ID) {
		case LibGuiIDs.IRON_MEDIUM:
			return new ContainerMedium(player, (TileMedium) world.getTileEntity(new BlockPos(x, y, z)));
		case LibGuiIDs.FAST_FURNACE:
			return new ContainerFastFurnace(player, (TileFastFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        case LibGuiIDs.TALISMAN:
            return new ContainerTalisman(player, player.getHeldItem(EnumHand.MAIN_HAND));
        case LibGuiIDs.RITUAL_POUCH:
            ItemStack pouch = player.getHeldItemMainhand();
            IItemHandler handler = pouch.getCapability( CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null );
            return new RitualBagContainer(player, handler);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case LibGuiIDs.IRON_MEDIUM:
			return new GuiIronMedium(player, (TileMedium) world.getTileEntity(new BlockPos(x, y, z)));
		case LibGuiIDs.FAST_FURNACE:
			return new GuiFastFurnace(player, (TileFastFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        case LibGuiIDs.TALISMAN:
            return new GuiTalismanContainer(player, player.getHeldItem(EnumHand.MAIN_HAND));
        case LibGuiIDs.RITUAL_POUCH:
            ItemStack pouch = player.getHeldItemMainhand();
            return new GuiRitualPouch(player, pouch);
        }
		return null;
	}
}
