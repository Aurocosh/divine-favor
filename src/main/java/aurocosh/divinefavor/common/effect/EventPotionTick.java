package aurocosh.divinefavor.common.effect;

import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.common.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventPotionTick {
    @SubscribeEvent
    public static void onEntityUpdate(LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null)
            return;
        for (ModPotion effect : ModPotions.getPotions()) {
            if (effect != null && entity.isPotionActive(effect) && entity.getActivePotionEffect(effect) != null) {
                if (entity.getActivePotionEffect(effect).getDuration() == 0)
                    entity.removeActivePotionEffect(effect);
                else
                    effect.tick(entity);
            }
        }
    }
}
