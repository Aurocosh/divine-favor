package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.living.LivingData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionCripple : ModPotion("cripple", true, 0x7FB8A4) {
    init {
        setIsCurse(true)
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "b171212d-7cd8-4a62-8665-b2258adf68b8", (-ConfigArrow.cripple.slownessForce).toDouble(), 2)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        val crippleData = LivingData.get(livingBase).crippleData
        crippleData.resetCureTimer()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase.isSprinting) {
            val crippleData = LivingData.get(livingBase).crippleData
            if (crippleData.cureTick())
                livingBase.removePotionEffect(ModCurses.cripple)
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
