package aurocosh.divinefavor.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;
import java.util.Random;

@Mod.EventBusSubscriber
public class UtilBlockClick {
    private static int MAX_BLOCKS_REMEBERED = 8;
    private static LinkedList<BlockPos> clickedBlocks = new LinkedList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if(world.isRemote)
            return;

        BlockPos pos = event.getPos();
        if(clickedBlocks.contains(pos))
            return;
        clickedBlocks.add(pos);
        if(clickedBlocks.size() > MAX_BLOCKS_REMEBERED)
            clickedBlocks.remove();
    }

    public static boolean wasBlockLeftClicked(BlockPos pos) {
        return clickedBlocks.contains(pos);
    }
}