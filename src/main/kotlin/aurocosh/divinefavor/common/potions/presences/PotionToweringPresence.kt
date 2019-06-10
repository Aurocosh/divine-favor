package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.lib.distributed_random.DistributedRandomList
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilRandom
import aurocosh.divinefavor.common.util.UtilSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class PotionToweringPresence : ModPotion("towering_presence", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.toweringPresenceData.reset()
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        if (livingBase is EntityPlayer)
            UtilSpirit.convertMarksToInvites(livingBase, ModSpirits.timber, ModCallingStones.calling_stone_timber)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase !is EntityPlayer)
            return
        if (!livingBase.divinePlayerData.toweringPresenceData.tick())
            return

        val curseTime = UtilRandom.nextInt(ConfigPresence.toweringPresence.minCurseTime, ConfigPresence.toweringPresence.maxCurseTime)
        val curse = possibleCurses.random ?: return
        livingBase.addPotionEffect(ModEffect(curse, curseTime).setIsCurse())
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val possibleCurses = DistributedRandomList<ModPotion>()

        fun init() {
            possibleCurses.add(ModCurses.armor_corrosion, 0.1)
            possibleCurses.add(ModCurses.crawling_mist, 0.1)
            possibleCurses.add(ModCurses.cripple, 0.1)
            possibleCurses.add(ModCurses.fiery_mark, 0.1)
            possibleCurses.add(ModCurses.fill_lungs, 0.1)
            possibleCurses.add(ModCurses.hollow_leg, 0.1)
            possibleCurses.add(ModCurses.limp_leg, 0.1)
            possibleCurses.add(ModCurses.petrification, 0.1)
            possibleCurses.add(ModCurses.roots, 0.1)
            possibleCurses.add(ModCurses.skyfall, 0.1)
            possibleCurses.add(ModCurses.suffocating_fumes, 0.1)
            possibleCurses.add(ModCurses.wind_leash, 0.1)
            possibleCurses.add(ModCurses.yummy_smell, 0.1)
        }
    }
}
