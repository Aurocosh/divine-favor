package aurocosh.divinefavor.common.potions.common

import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.blends.*

object ModBlendEffects {
    lateinit var arboreal_aura: ModPotion
    lateinit var calling_aura: ModPotion
    lateinit var charred_aura: ModPotion
    lateinit var distorted_aura: ModPotion
    lateinit var energetic_aura: ModPotion
    lateinit var frosty_aura: ModPotion
    lateinit var hunters_aura: ModPotion
    lateinit var mineral_aura: ModPotion
    lateinit var visceral_aura: ModPotion

    fun preInit() {
        arboreal_aura = PotionArborealAura()
        calling_aura = PotionCallingAura()
        charred_aura = PotionCharredAura()
        distorted_aura = PotionDistortedAura()
        energetic_aura = PotionEnergeticAura()
        frosty_aura = PotionFrostyAura()
        hunters_aura = PotionHuntersAura()
        mineral_aura = PotionMineralAura()
        visceral_aura = PotionVisceralAura()
    }
}
