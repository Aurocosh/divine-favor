package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource

class PotionGills : ModPotionToggle("potion_gills", 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase !is EntityPlayer)
            return

        val gillsData = livingBase.divinePlayerData.gillsData
        if (livingBase.isInWater()) {
            gillsData.resetTime()
            livingBase.setAir(300)
            return
        }
        if (!gillsData.tick())
            return

        livingBase.attackEntityFrom(DamageSource.DROWN, 4f)
        gillsData.delay()
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
