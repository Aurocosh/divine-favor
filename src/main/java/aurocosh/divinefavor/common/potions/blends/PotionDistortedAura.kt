package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionDistortedAura : ModPotion("distorted_aura", true, 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase !is EntityPlayer)
            return
        val auraData = PlayerData.get(livingBase).distortedAuraData
        auraData.reset()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase !is EntityPlayer)
            return
        if (!COUNTER.isFinished)
            return
        if (!ModSpirits.endererer.isActive)
            return
        val headStack = livingBase.inventory.armorInventory[3]
        if (headStack.item !is ItemBlockEnderPumpkin)
            return

        val pos = livingBase.positionVector
        val radius = ConfigAura.distortedAura.endermanRadius
        val boundingBox = UtilCoordinates.getBoundingBox(pos, radius.toDouble())
        val predicate = { e: EntityEnderman? -> e?.attackingEntity !== livingBase }
        val endermanList = livingBase.world.getEntitiesWithinAABB<EntityEnderman>(EntityEnderman::class.java, boundingBox, predicate)
        if (endermanList.isEmpty())
            return

        val auraData = PlayerData.get(livingBase).distortedAuraData
        if (auraData.tryLuck()) {
            livingBase.removePotionEffect(ModBlendEffects.distorted_aura)
            livingBase.addPotionEffect(ModEffect(ModBlessings.warping_presence, ConfigPresence.warpingPresence.duration))
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        val CHECK_RATE = UtilTick.secondsToTicks(1f)
        private val COUNTER = LoopedCounter(CHECK_RATE)

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                COUNTER.tick()
        }
    }
}
