package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFury
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionFocusedFury : ModPotion("focused_fury", 0x7FB8A4) {

    @SideOnly(Side.CLIENT)
    override fun renderCustomInvText(x: Int, y: Int, effect: PotionEffect, mc: Minecraft) {
        val potionName = I18n.format(name)
        mc.fontRenderer.drawStringWithShadow(potionName, (x + 10 + 18).toFloat(), (y + 6).toFloat(), 16777215)

        val player = DivineFavor.proxy.clientPlayer
        val furyData = player.divinePlayerData.focusedFuryData
        val duration = Potion.getPotionDurationString(effect, 1.0f)
        val mobName = I18n.format(furyData.mobName)
        mc.fontRenderer.drawStringWithShadow("$duration $mobName", (x + 10 + 18).toFloat(), (y + 6 + 10).toFloat(), 8355711)
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        if (livingBase !is EntityPlayer)
            return

        val furyData = livingBase.divinePlayerData.focusedFuryData
        furyData.reset()
        MessageSyncFury(furyData.mobTypeId).sendTo(livingBase)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityPlayer ?: return
            val entityMob = event.entity
            if (entityMob !is IMob)
                return
            if (!entity.isPotionActive(ModPotions.focused_fury))
                return

            val furyData = entity.divinePlayerData.focusedFuryData
            if (!furyData.hasFury())
                return
            if (furyData.hasFury(entityMob))
                event.amount = event.amount + ConfigSpell.focusedFury.extraDamage
            else
                event.isCanceled = true
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onMobDeath(event: LivingDeathEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val attacker = source.trueSource as? EntityPlayer ?: return

            if (!attacker.isPotionActive(ModPotions.focused_fury))
                return
            val mob = event.entity
            if (mob !is IMob)
                return

            val furyData = attacker.divinePlayerData.focusedFuryData
            if (furyData.hasFury())
                return
            furyData.setFury(mob)
            MessageSyncFury(furyData.mobTypeId).sendTo(attacker)
        }
    }
}
