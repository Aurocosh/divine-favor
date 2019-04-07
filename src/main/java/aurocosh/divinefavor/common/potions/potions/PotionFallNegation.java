package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.network.message.client.MessageSyncPotionCharge;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionFallNegation extends ModPotionCharge {

    public PotionFallNegation() {
        super("fall_negation", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLand(LivingFallEvent event) {
        Entity entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if (!player.isPotionActive(ModPotions.fall_negation))
            return;
        if (player.world.isRemote)
            return;
        if (!(event.getDistance() > 2))
            return;

        event.setDamageMultiplier(0);
        entity.fallDistance = 0;
        //event.setCanceled(true);

        ModEffectCharge effectCharge = (ModEffectCharge) player.getActivePotionEffect(ModPotions.fall_negation);
        assert effectCharge != null;
        int charges = effectCharge.consumeCharge();
        new MessageSyncPotionCharge(ModPotions.fall_negation,charges).sendTo(player);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
