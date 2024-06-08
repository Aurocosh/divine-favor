package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionCallingAura : ModPotion("calling_aura", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.callingAuraData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        @JvmStatic
        fun onPlaceEvent(event: BlockEvent.PlaceEvent) {
            val player = event.player
            if (!player.isPotionActive(ModBlendEffects.calling_aura))
                return
            if (!ModSpirits.loon.isActive)
                return
            if (event.placedBlock.block !== Blocks.PUMPKIN)
                return
            val world = event.world
            if (ModMultiBlocks.snowman.match(world, event.pos) == null)
                return

            val auraData = player.divinePlayerData.callingAuraData
            if (auraData.tryLuck()) {
                player.removePotionEffect(ModBlendEffects.calling_aura)
                player.addPotionEffect(ModEffect(ModBlessings.manipulative_presence, ConfigPresence.manipulativePresence.duration))
            }
        }
    }
}
