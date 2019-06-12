package aurocosh.divinefavor.common.entity.projectile

import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.lib.extensions.attackEntityNoTimer
import aurocosh.divinefavor.common.util.UtilMob
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World

open class EntityIceArrow : EntityArrow {
    constructor(worldIn: World) : super(worldIn)

    constructor(worldIn: World, x: Double, y: Double, z: Double) : super(worldIn, x, y, z)

    constructor(worldIn: World, shooter: EntityLivingBase) : super(worldIn, shooter)

    init {
        damage = ConfigItem.iceArrow.damage.toDouble()
    }

    override fun getArrowStack(): ItemStack {
        return ItemStack(ModItems.ice_arrow)
    }

    override fun arrowHit(living: EntityLivingBase) {
        super.arrowHit(living)
        if (UtilMob.isMobHellish(living))
            living.attackEntityNoTimer(DamageSource.MAGIC, ConfigItem.iceArrow.damageHellishExtra)
    }

    override fun onHit(raytraceResultIn: RayTraceResult) {
        super.onHit(raytraceResultIn)
        if (UtilRandom.rollDiceFloat(ConfigItem.iceArrow.breakProbability))
            setDead()
    }
}