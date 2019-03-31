package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.hunters.HuntersAuraData;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionHuntersAura extends ModPotion {
    public PotionHuntersAura() {
        super("hunters_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        HuntersAuraData auraData = PlayerData.get(player).getHuntersAuraData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onMobDeath(LivingDeathEvent event) {
        if (!ModSpirits.arbow.isActive())
            return;
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSourceIndirect))
            return;
        EntityDamageSourceIndirect sourceIndirect = (EntityDamageSourceIndirect) source;
        if(!sourceIndirect.damageType.equals("arrow"))
            return;
        Entity attacker = source.getTrueSource();
        if (!(attacker instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) attacker;
        if (!player.isPotionActive(ModBlendEffects.hunters_aura))
            return;
        Entity mob = event.getEntity();
        if (!(mob instanceof IMob))
            return;
        HuntersAuraData huntersAuraData = PlayerData.get(player).getHuntersAuraData();
        if (huntersAuraData.tryLuck()) {
            player.removePotionEffect(ModBlendEffects.hunters_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.predatory_presence, ConfigPresence.predatoryPresence.duration));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
