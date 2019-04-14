package aurocosh.divinefavor.common.block.ethereal;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlockAir;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import aurocosh.divinefavor.common.particles.ParticleHandler;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.state_mappers.InvisibleStateMapper;
import aurocosh.divinefavor.common.state_mappers.common.ICustomStateMappedBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEtherealLight extends ModBlockAir implements ICustomStateMappedBlock {
    public BlockEtherealLight(String name, Material material, int lightLevel) {
        super(name, material);
        setTickRandomly(true);
        this.lightValue = lightLevel;
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        if (player.isPotionActive(ModPotions.prismatic_eyes))
            spawnParticles(worldIn, pos);
    }

    private void spawnParticles(World worldIn, BlockPos pos) {
        Random random = worldIn.rand;
        for (int i = 0; i < 6; ++i) {
            double d1 = (double) ((float) pos.getX() + random.nextFloat());
            double d2 = (double) ((float) pos.getY() + random.nextFloat());
            double d3 = (double) ((float) pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
                d2 = (double) pos.getY() + 0.0625D + 1.0D;

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
                d2 = (double) pos.getY() - 0.0625D;

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
                d3 = (double) pos.getZ() + 0.0625D + 1.0D;

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
                d3 = (double) pos.getZ() - 0.0625D;

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
                d1 = (double) pos.getX() + 0.0625D + 1.0D;

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
                d1 = (double) pos.getX() - 0.0625D;

            if (d1 < (double) pos.getX() || d1 > (double) (pos.getX() + 1) || d2 < 0.0D || d2 > (double) (pos.getY() + 1) || d3 < (double) pos.getZ() || d3 > (double) (pos.getZ() + 1))
                ParticleHandler.createParticle(ModParticleTypes.etherealLight, worldIn, d1, d2, d3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected ModItemBlock getItemBlock() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IStateMapper getCustomStateMapper() {
        return new InvisibleStateMapper();
    }
}