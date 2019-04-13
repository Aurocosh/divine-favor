package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionYummySmell extends ModPotion {
    private static final LoopedCounter TICK_COUNTER = new LoopedCounter(ConfigArrow.yummySmell.effectRate);

    public PotionYummySmell() {
        super("yummy_smell", false, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (!TICK_COUNTER.isFinished())
            return;
        List<EntityCreature> creatures = UtilEntity.getNearbyEntities(EntityCreature.class, livingBase.world, livingBase.getPositionVector(), ConfigArrow.yummySmell.radius, input -> true);
        for (EntityCreature creature : creatures)
            creature.setAttackTarget(livingBase);
        if (creatures.size() >= ConfigArrow.yummySmell.cureCount)
            livingBase.removePotionEffect(ModCurses.yummy_smell);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            TICK_COUNTER.tick();
    }
}
