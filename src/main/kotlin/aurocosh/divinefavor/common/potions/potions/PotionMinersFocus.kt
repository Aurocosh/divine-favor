package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.MobEffects
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionMinersFocus : ModPotion("miners_focus", 0x7FB8A4) {
    private val FATIGUE_DURATION = (60.0 * 20.0 * 0.2).toInt()
    private val FATIGUE_LEVEL = 3

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        livingBase.addPotionEffect(ModEffect(MobEffects.MINING_FATIGUE, FATIGUE_DURATION, FATIGUE_LEVEL).setIsCurse())
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
