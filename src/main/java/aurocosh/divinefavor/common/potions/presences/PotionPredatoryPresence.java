package aurocosh.divinefavor.common.potions.presences;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.predatory.PredatoryPresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionPredatoryPresence extends ModPotion {
    public PotionPredatoryPresence() {
        super("predatory_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        PredatoryPresenceData presenceData = PlayerData.get(player).getPredatoryPresenceData();
        presenceData.reset();
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        Entity target = event.getEntity();
        if (!(target instanceof EntityLivingBase))
            return;
        EntityLivingBase livingBase = (EntityLivingBase) target;
        if (livingBase.isPotionActive(ModBlessings.predatory_presence))
            livingBase.removePotionEffect(ModBlessings.predatory_presence);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onMobDeath(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity attacker = source.getTrueSource();
        if (!(attacker instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) attacker;
        if (!player.isPotionActive(ModBlessings.predatory_presence))
            return;
        Entity mob = event.getEntity();
        if (!(mob instanceof IMob))
            return;

        PredatoryPresenceData presenceData = PlayerData.get(player).getPredatoryPresenceData();
        if (presenceData.count()) {
            player.removePotionEffect(ModBlessings.predatory_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.INSTANCE.getCalling_stone_arbow()));
        }
    }
}
