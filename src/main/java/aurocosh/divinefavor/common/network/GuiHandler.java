/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [10/01/2016, 14:53:05 (GMT)]
 */
package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.client.gui.GuiFastFurnace;
import aurocosh.divinefavor.client.gui.GuiIronMedium;
import aurocosh.divinefavor.common.block.tile.TileFastFurnace;
import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.block.tile.container.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.tile.container.ContainerIronMedium;
import aurocosh.divinefavor.common.constants.LibGuiIDs;
import net.minecraft.entity.player.EntityPlayer;
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
		}

		return null;
	}


}
