package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class EntityStoneball extends EntityThrowable {
    public EntityStoneball(World worldIn) {
        super(worldIn);
    }

    public EntityStoneball(World worldIn, EntityLivingBase thrower) {
        super(worldIn, thrower);
    }

    @SideOnly(Side.CLIENT)
    public EntityStoneball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        EntityLivingBase thrower = getThrower();

        if (result.entityHit != null) {
            boolean isCritical = UtilRandom.INSTANCE.rollDiceFloat(ConfigSpells.stoneballThrow.criticalChance);
            float damage = isCritical ? ConfigSpells.stoneballThrow.criticalDamage : ConfigSpells.stoneballThrow.damage;
            if (result.entityHit instanceof AbstractSkeleton)
                damage += ConfigSpells.stoneballThrow.extraSkeletonDamage;
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), damage);
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = result.getBlockPos();
            IBlockState state = world.getBlockState(blockpos);
            float hardness = state.getBlockHardness(world, blockpos);
            if (hardness < 0.5f)
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState());

            setDead();
            return;
        }

        if (!world.isRemote) {
            //this.world.setEntityState(this, (byte)3);
            setDead();
        }
    }
}