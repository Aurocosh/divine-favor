package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class PotionConsumingFury extends ModPotionTrigger {
    private static final int MOBS_TO_KILL = 3;
    private static final float DAMAGE_TO_DEAL = 2;
    public static final float EXTRA_DAMAGE = 16;

    private final static Map<EntityPlayer,Integer> killCounts = new HashMap<>();

    public PotionConsumingFury() {
        super("consuming_fury", true, 0x7FB8A4);
    }

    @Override
    public void trigger(EntityLivingBase player) {
        assert player instanceof EntityPlayer;
        int killCount = killCounts.computeIfAbsent((EntityPlayer) player, player1 -> 0);
        killCounts.remove(player);
        if(killCount < MOBS_TO_KILL)
            player.attackEntityFrom(DamageSource.MAGIC, DAMAGE_TO_DEAL);
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
        if(!player.isPotionActive(ModPotions.consuming_fury))
            return;
        event.setAmount(event.getAmount() + EXTRA_DAMAGE);
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity entity = source.getTrueSource();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!player.isPotionActive(ModPotions.consuming_fury))
            return;

        int killCount = killCounts.computeIfAbsent(player,k -> 0) + 1;
        killCounts.put(player,killCount);
        int killsLeft = MOBS_TO_KILL - killCount;
        if(killsLeft > 0)
            UtilChat.addChatMessage(player,"Kills left: " + killCount);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
