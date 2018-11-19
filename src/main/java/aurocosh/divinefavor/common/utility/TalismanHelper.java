package aurocosh.divinefavor.common.utility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.Random;

public class TalismanHelper {
    private static Random rand = new Random();

    public static void removeBlockWithDrops(EntityPlayer player, World world, ItemStack tool, BlockPos pos, boolean particles) {
        if(!world.isBlockLoaded(pos) || !world.isBlockModifiable(player, pos))
            return;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(!world.isRemote && block != null && !block.isAir(state, world, pos) && !(block instanceof BlockLiquid) && !(block instanceof IFluidBlock) && block.getPlayerRelativeBlockHardness(state, player, world, pos) > 0) {
            if(!ForgeHooks.canHarvestBlock(block, player, world, pos))
                return;

            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
            MinecraftForge.EVENT_BUS.post(event);
            if(!event.isCanceled()) {
                if(!player.capabilities.isCreativeMode) {
                    TileEntity tile = world.getTileEntity(pos);
                    IBlockState localState = world.getBlockState(pos);

                    if(block.removedByPlayer(state, world, pos, player, true)) {
                        block.onBlockDestroyedByPlayer(world, pos, state);
                        block.harvestBlock(world, player, pos, state, tile, tool);
                    }
                } else world.setBlockToAir(pos);
            }

            if(particles)
                world.playEvent(2001, pos, Block.getStateId(state));
        }
    }

    public static void setHeadingFromThrower(Entity entityThrower, Entity projectile, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        setThrowableHeading(projectile, (double)f, (double)f1, (double)f2, velocity, inaccuracy);
        projectile.motionX += entityThrower.motionX;
        projectile.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
            projectile.motionY += entityThrower.motionY;
    }

    public static void setThrowableHeading(Entity projectile, double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        projectile.motionX = x;
        projectile.motionY = y;
        projectile.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        projectile.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        projectile.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        projectile.prevRotationYaw = projectile.rotationYaw;
        projectile.prevRotationPitch = projectile.rotationPitch;
        //projectile.ticksInGround = 0;
    }
}