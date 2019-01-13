package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionSpiderMight extends ModPotion {
    public static final double CLIMB_SPEED = 0.288D;

    public PotionSpiderMight() {
        super("spider_might", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null)
            return;
        if (!entity.isPotionActive(ModPotions.spider_might))
            return;
        if (!entity.collidedHorizontally)
            return;

        if (entity.isSneaking())
            entity.motionY = 0.0D;
        else if (entity.moveForward > 0.0F && entity.motionY < CLIMB_SPEED)
            entity.motionY = CLIMB_SPEED;
        if(!entity.world.isRemote)
            entity.fallDistance = 0;
    }
}
