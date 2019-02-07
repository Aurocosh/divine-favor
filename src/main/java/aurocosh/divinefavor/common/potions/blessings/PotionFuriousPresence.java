package aurocosh.divinefavor.common.potions.blessings;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.furious.FuriousPresenceData;
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

@Mod.EventBusSubscriber
public class PotionFuriousPresence extends ModPotion {
    private final static float MULTIPLIER = 2.5f;

    public PotionFuriousPresence() {
        super("furious_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        FuriousPresenceData presenceData = PlayerData.get(player).getFuriousPresenceData();
        presenceData.reset();
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        if (isVictimHasBlessing(event) || isAttackerHasBlessing(event))
            event.setAmount(event.getAmount() * MULTIPLIER);
    }

    private static boolean isVictimHasBlessing(LivingDamageEvent event) {
        Entity target = event.getEntity();
        if (!(target instanceof EntityLivingBase))
            return false;
        EntityLivingBase livingBase = (EntityLivingBase) target;
        return livingBase.isPotionActive(ModBlessings.furious_presence);
    }

    private static boolean isAttackerHasBlessing(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return false;
        Entity entity = source.getTrueSource();
        if (!(entity instanceof EntityPlayer))
            return false;
        EntityPlayer player = (EntityPlayer) entity;
        return player.isPotionActive(ModBlessings.furious_presence);
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
        if (!player.isPotionActive(ModBlessings.furious_presence))
            return;
        Entity mob = event.getEntity();
        if (!(mob instanceof IMob))
            return;

        FuriousPresenceData furyData = PlayerData.get(player).getFuriousPresenceData();
        if (furyData.count()) {
            player.removePotionEffect(ModBlessings.furious_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_squarefury));
        }
    }
}
