package aurocosh.divinefavor.common.block.ethereal;

import aurocosh.divinefavor.DivineFavor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEtherealFlash extends BlockEtherealLight {
    private final int despawnDelay;

    public BlockEtherealFlash(String name, int lightLevel,  int despawnDelay) {
        super(name, Material.ROCK, lightLevel);
        this.despawnDelay = despawnDelay;
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleUpdate(pos, this, despawnDelay);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        worldIn.setBlockToAir(pos);
    }
}