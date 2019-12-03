package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionWildSprint : ModPotion("wild_sprint", 0x7FB8A4) {
    private val SPEED_MODIFIER = 0.2f
    private val WATER_RUNNING_SPEED = 0.25f
    private val WATER_RUNNING_SPEED_SQ = WATER_RUNNING_SPEED * WATER_RUNNING_SPEED
    private val canWalkOnBlock:(World,BlockPos)->(Boolean) = { world, pos -> world.getBlockState(pos).material == Material.WATER }

    init {
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "e9c4efd6-98fc-4273-ae05-571f4fd18628", SPEED_MODIFIER.toDouble(), 2)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.motionX * livingBase.motionX + livingBase.motionZ * livingBase.motionZ >= WATER_RUNNING_SPEED_SQ)
            tickLiquidWalk(livingBase, canWalkOnBlock)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
