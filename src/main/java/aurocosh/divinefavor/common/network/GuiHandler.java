package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.client.gui.GuiFastFurnace;
import aurocosh.divinefavor.client.gui.GuiIronMedium;
import aurocosh.divinefavor.client.gui.GuiTalismanContainer;
import aurocosh.divinefavor.common.block.tile.TileFastFurnace;
import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.block.tile.container.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.tile.container.ContainerIronMedium;
import aurocosh.divinefavor.common.constants.LibGuiIDs;
import aurocosh.divinefavor.common.container.ContainerTalisman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case LibGuiIDs.IRON_MEDIUM:
			return new ContainerIronMedium(player, (TileIronMedium) world.getTileEntity(new BlockPos(x, y, z)));
		case LibGuiIDs.FAST_FURNACE:
			return new ContainerFastFurnace(player, (TileFastFurnace) world.getTileEntity(new BlockPos(x, y, z)));
            case LibGuiIDs.TALISMAN:
                return new ContainerTalisman(player, player.getHeldItem(EnumHand.MAIN_HAND));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case LibGuiIDs.IRON_MEDIUM:
			return new GuiIronMedium(player, (TileIronMedium) world.getTileEntity(new BlockPos(x, y, z)));
		case LibGuiIDs.FAST_FURNACE:
			return new GuiFastFurnace(player, (TileFastFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        case LibGuiIDs.TALISMAN:
            return new GuiTalismanContainer(player, player.getHeldItem(EnumHand.MAIN_HAND));
		}

		return null;
	}
}
