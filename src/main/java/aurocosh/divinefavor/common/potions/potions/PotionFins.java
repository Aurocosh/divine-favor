package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionFins extends ModPotion {
    private static float SPEED_MODIFIER = 0.14f;

    public PotionFins() {
        super("fins", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null)
            return;
        if (!entity.isPotionActive(ModPotions.fins))
            return;
        if (!entity.isInWater())
            return;
        UtilEntity.addVelocity(entity, SPEED_MODIFIER);
    }
}
