package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
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
class PotionGrudge : ModPotionToggle("grudge", 0x7FB8A4) {

    @SideOnly(Side.CLIENT)
    override fun renderCustomInvText(x: Int, y: Int, effect: PotionEffect, mc: Minecraft) {
        val potionName = I18n.format(name)
        mc.fontRenderer.drawStringWithShadow(potionName, (x + 10 + 18).toFloat(), (y + 6).toFloat(), 16777215)

        val player = DivineFavor.proxy.clientPlayer
        val mobName = I18n.format(player.divinePlayerData.grudgeData.mobName)
        mc.fontRenderer.drawStringWithShadow(mobName, (x + 10 + 18).toFloat(), (y + 6 + 10).toFloat(), 8355711)
    }

    companion object {

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityPlayer ?: return
            val entityMob = event.entity
            if (entityMob !is IMob)
                return
            if (!entity.isPotionActive(ModPotions.grudge))
                return

            if (!entity.divinePlayerData.grudgeData.hasGrudge(entityMob))
                return
            event.amount = event.amount + ConfigSpell.grudge.extraDamage
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onPlayerDeath(event: LivingDeathEvent) {
            val entity = event.entity as? EntityPlayer ?: return

            val grudgeData = entity.divinePlayerData.grudgeData
            val attacker = event.source.trueSource
            if (attacker is IMob)
                grudgeData.setGrudge(attacker)
            else
                grudgeData.reset()
            MessageSyncGrudge(grudgeData.mobTypeId).sendTo(entity)
        }
    }
}
