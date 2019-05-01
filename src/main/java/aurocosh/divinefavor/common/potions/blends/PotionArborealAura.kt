package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
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

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionArborealAura : ModPotion("arboreal_aura", true, 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divineCustomData.arborealAuraData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onBlockBroken(event: BlockEvent.BreakEvent) {
            val player = event.player
            if (!player.isPotionActive(ModBlendEffects.arboreal_aura))
                return
            val state = event.state
            if (state.material !== Material.WOOD)
                return
            if (!ModSpirits.timber.isActive)
                return
            val auraData = player.divineCustomData.arborealAuraData
            if (auraData.count()) {
                player.removePotionEffect(ModBlendEffects.arboreal_aura)
                player.addPotionEffect(ModEffect(ModBlessings.towering_presence, ConfigPresence.toweringPresence.duration))
            }
        }
    }
}
