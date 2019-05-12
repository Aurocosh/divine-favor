package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionYummySmell : ModPotion("yummy_smell", 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (!TICK_COUNTER.isFinished)
            return

        val boundingBox = UtilCoordinates.getBoundingBox(livingBase.positionVector, ConfigArrow.yummySmell.radius.toDouble())
        val creatures = livingBase.world.getEntitiesWithinAABB(EntityCreature::class.java, boundingBox)
        for (creature in creatures)
            creature.attackTarget = livingBase
        if (creatures.size >= ConfigArrow.yummySmell.cureCount)
            livingBase.removePotionEffect(ModCurses.yummy_smell)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val TICK_COUNTER = LoopedCounter(ConfigArrow.yummySmell.effectRate)

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                TICK_COUNTER.tick()
        }
    }
}
