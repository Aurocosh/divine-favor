package aurocosh.divinefavor.common.potions.common

import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.presences.*

object ModBlessings {
    lateinit var chilling_presence: ModPotion
    lateinit var energetic_presence: ModPotion
    lateinit var furious_presence: ModPotion
    lateinit var industrious_presence: ModPotion
    lateinit var manipulative_presence: ModPotion
    lateinit var predatory_presence: ModPotion
    lateinit var scorching_presence: ModPotion
    lateinit var towering_presence: ModPotion
    lateinit var warping_presence: ModPotion

    fun preInit() {
        chilling_presence = PotionChillingPresence()
        energetic_presence = PotionEnergeticPresence()
        furious_presence = PotionFuriousPresence()
        industrious_presence = PotionIndustriousPresence()
        manipulative_presence = PotionManipulativePresence()
        predatory_presence = PotionPredatoryPresence()
        scorching_presence = PotionScorchingPresence()
        towering_presence = PotionToweringPresence()
        warping_presence = PotionWarpingPresence()
    }

    fun init() {
        PotionToweringPresence.init()
    }
}
