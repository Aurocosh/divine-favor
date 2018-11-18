package aurocosh.divinefavor.common.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class EntityStoneball extends EntityThrowable
{
    private EntityLivingBase thrower;

    public EntityStoneball(World worldIn) {
        super(worldIn);
    }

    public EntityStoneball(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        thrower = throwerIn;
    }

    @SideOnly(Side.CLIENT)
    public EntityStoneball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public static void registerFixesThrowable(DataFixer p_189663_0_) {
        EntityThrowable.registerFixesThrowable(p_189663_0_, "ThrownStoneball");
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        EntityLivingBase entitylivingbase = getThrower();

        if (result.entityHit != null) {
            int i = 1;

//            if (result.entityHit instanceof EntityBlaze)
//                i = 3;
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = result.getBlockPos();
            IBlockState state = this.world.getBlockState(blockpos);
            float hardness = state.getBlockHardness(this.world,blockpos);
            if(hardness < 0.5f)
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState());

                setDead();
                return;
        }

        if (!this.world.isRemote) {
            //this.world.setEntityState(this, (byte)3);
            setDead();
        }
    }
}