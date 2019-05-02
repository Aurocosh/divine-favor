package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionHollowLeg : ModPotion("hollow_leg", true, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase !is EntityPlayer)
            livingBase.removePotionEffect(ModCurses.hollow_leg)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (!COUNTER.isFinished)
            return
        if (livingBase !is EntityPlayer)
            return
        livingBase.foodStats.addExhaustion(ConfigArrow.hollowLeg.exaustionValue)
        //        player.getFoodStats().addStats(1, 0.1F);
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val COUNTER = LoopedCounter(ConfigArrow.hollowLeg.exaustionRate)

        @SubscribeEvent
        fun onItemUse(event: LivingEntityUseItemEvent.Finish) {
            val entity = event.entityLiving
            if (!entity.isPotionActive(ModCurses.hollow_leg))
                return
            val stack = event.resultStack
            if (stack.item !== Items.APPLE)
                return
            val player = entity as EntityPlayer
            player.removePotionEffect(ModCurses.hollow_leg)
        }

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                COUNTER.tick()
        }
    }
}
