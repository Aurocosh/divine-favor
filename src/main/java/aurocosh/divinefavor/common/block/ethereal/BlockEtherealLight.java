package aurocosh.divinefavor.common.block.ethereal;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlockAir;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.particles.particles.EtherealParticle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.state_mappers.InvisibleStateMapper;
import aurocosh.divinefavor.common.state_mappers.common.ICustomStateMappedBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

    @SideOnly(Side.CLIENT)
    private void spawnParticles(World worldIn, BlockPos pos) {
        Random random = worldIn.rand;
        for (int i = 0; i < 6; ++i) {
            double x = (double) ((float) pos.getX() + random.nextFloat());
            double y = (double) ((float) pos.getY() + random.nextFloat());
            double z = (double) ((float) pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
                y = (double) pos.getY() + 0.0625D + 1.0D;

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
                y = (double) pos.getY() - 0.0625D;

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
                z = (double) pos.getZ() + 0.0625D + 1.0D;

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
                z = (double) pos.getZ() - 0.0625D;

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
                x = (double) pos.getX() + 0.0625D + 1.0D;

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
                x = (double) pos.getX() - 0.0625D;

            if (x < (double) pos.getX() || x > (double) (pos.getX() + 1) || y < 0.0D || y > (double) (pos.getY() + 1) || z < (double) pos.getZ() || z > (double) (pos.getZ() + 1)) {
                Vec3d position = new Vec3d(x, y, z);
                ModParticles.normal.createParticle(position, () -> new EtherealParticle(worldIn, position, 1f, 1f));
            }
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