package aurocosh.divinefavor.common.entity.projectile

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.AbstractSkeleton
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.init.Blocks
import net.minecraft.util.DamageSource
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


class EntityStoneball : EntityThrowable {
    constructor(worldIn: World) : super(worldIn)

    constructor(worldIn: World, thrower: EntityLivingBase) : super(worldIn, thrower)

    @SideOnly(Side.CLIENT)
    constructor(worldIn: World, x: Double, y: Double, z: Double) : super(worldIn, x, y, z)

    override fun onImpact(result: RayTraceResult) {
        val thrower = getThrower()

        if (result.entityHit != null) {
            val isCritical = UtilRandom.rollDiceFloat(ConfigSpells.stoneballThrow.criticalChance)
            var damage = if (isCritical) ConfigSpells.stoneballThrow.criticalDamage else ConfigSpells.stoneballThrow.damage
            if (result.entityHit is AbstractSkeleton)
                damage += ConfigSpells.stoneballThrow.extraSkeletonDamage
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), damage)
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            val blockPos = result.blockPos
            val state = world.getBlockState(blockPos)
            val hardness = state.getBlockHardness(world, blockPos)
            if (hardness < 0.5f)
                world.setBlockState(blockPos, Blocks.AIR.defaultState)

            setDead()
            return
        }

        if (!world.isRemote) {
            //this.world.setEntityState(this, (byte)3);
            setDead()
        }
    }
}