package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.monster.AbstractSkeleton
import net.minecraft.entity.monster.EntityWitch
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionArmorOfPacifist : ModPotionToggle("armor_of_pacifist", 0x7FB8A4) {
    companion object {

        @SubscribeEvent
        @JvmStatic
        fun onEntityDamaged(event: LivingDamageEvent) {
            protectPlayer(event)
            preventAttackFromPlayer(event)
        }

        private fun protectPlayer(event: LivingDamageEvent) {
            val entity = event.entity as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.armor_of_pacifist))
                return
            val source = event.source
            if (source === DamageSource.CACTUS || canProtectFromEntity(source))
                event.isCanceled = true
        }

        private fun canProtectFromEntity(source: DamageSource): Boolean {
            if (source !is EntityDamageSource)
                return false
            val attacker = source.getTrueSource()
            if (attacker is AbstractSkeleton)
                return false
            return if (attacker is EntityWitch) false else attacker is IMob && attacker !is EntityDragon
        }

        private fun preventAttackFromPlayer(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.armor_of_pacifist))
                return
            event.isCanceled = true
        }
    }
}
