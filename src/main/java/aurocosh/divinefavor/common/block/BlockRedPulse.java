package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlockAir;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRedPulse extends ModBlockAir {
    private final int redLevel;
    private final int despawnDelay;

    public BlockRedPulse(String name, int redLevel, int lightLevel, int despawnDelay) {
        super(name, Material.ROCK, ConstMainTabOrder.OTHER_BLOCKS);
        this.redLevel = redLevel;
        lightValue = lightLevel;
        this.despawnDelay = despawnDelay;
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return redLevel;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleUpdate(pos, this, despawnDelay);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        worldIn.setBlockToAir(pos);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}