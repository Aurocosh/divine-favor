package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionMineralAura : ModPotion("mineral_aura", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.mineralAuraData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        @JvmStatic
        fun onBlockBroken(event: BlockEvent.BreakEvent) {
            val player = event.player
            if (!player.isPotionActive(ModBlendEffects.mineral_aura))
                return
            val state = event.state
            if (state.material !== Material.ROCK)
                return
            if (!ModSpirits.romol.isActive)
                return
            if (player.divinePlayerData.mineralAuraData.count()) {
                player.removePotionEffect(ModBlendEffects.mineral_aura)
                player.addPotionEffect(ModEffect(ModBlessings.industrious_presence, ConfigPresence.industriousPresence.duration))
            }
        }
    }
}
