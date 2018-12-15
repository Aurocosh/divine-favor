package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.network.message.client.MessageSyncPotionCharge;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionButcheringStrike extends ModPotionCharge {
    private static float EXTRA_DAMAGE = 30;
    private static int EXTRA_LOOTING = 5;

    public PotionButcheringStrike() {
        super("butchering_strike", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity entity = source.getTrueSource();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if (!player.isPotionActive(ModPotions.butchering_strike))
            return;
        Entity animal = event.getEntity();
        if (!(animal instanceof IAnimals))
            return;
        if (animal instanceof IMob)
            return;

        ModEffectCharge effectCharge = (ModEffectCharge) player.getActivePotionEffect(ModPotions.butchering_strike);
        assert effectCharge != null;
        int charges = effectCharge.consumeCharge();
        new MessageSyncPotionCharge(ModPotions.butchering_strike,charges).sendTo(player);

        event.setAmount(event.getAmount() + EXTRA_DAMAGE);
    }

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        DamageSource source = event.getDamageSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity entity = source.getTrueSource();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if (!player.isPotionActive(ModPotions.butchering_strike))
            return;
        Entity animal = event.getEntity();
        if (!(animal instanceof IAnimals))
            return;
        if (animal instanceof IMob)
            return;
        event.setLootingLevel(event.getLootingLevel() + EXTRA_LOOTING);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
