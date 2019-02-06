package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.visceral_aura.VisceralAuraData;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionVisceralAura extends ModPotion {
    public PotionVisceralAura() {
        super("visceral_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        VisceralAuraData auraData = PlayerData.get(player).getVisceralAuraData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onMobDeath(LivingDeathEvent event) {
        if (!ModSpirits.squarefury.isActive())
            return;
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity attacker = source.getTrueSource();
        if (!(attacker instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) attacker;
        if (!player.isPotionActive(ModBlendEffects.visceral_aura))
            return;
        Entity mob = event.getEntity();
        if (!(mob instanceof IMob))
            return;

        VisceralAuraData furyData = PlayerData.get(player).getVisceralAuraData();
        if (furyData.tryLuck()) {
            player.removePotionEffect(ModBlendEffects.visceral_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.furious_presence, UtilTick.minutesToTicks(2)));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}