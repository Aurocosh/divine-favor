package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
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

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionConsumingFury extends ModPotion {
    private final static Map<EntityPlayer, Integer> killCounts = new HashMap<>();

    public PotionConsumingFury() {
        super("consuming_fury", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) livingBase;
        int killCount = killCounts.computeIfAbsent(player, player1 -> 0);
        killCounts.remove(player);
        if (killCount < ConfigSpells.consumingFury.mobsToKill)
            player.attackEntityFrom(DamageSource.MAGIC, ConfigSpells.consumingFury.punishmentDamage);
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
        if (!player.isPotionActive(ModPotions.consuming_fury))
            return;
        event.setAmount(event.getAmount() + ConfigSpells.consumingFury.extraDamage);
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
        if (!player.isPotionActive(ModPotions.consuming_fury))
            return;

        int killCount = killCounts.computeIfAbsent(player, k -> 0) + 1;
        killCounts.put(player, killCount);
        int killsLeft = ConfigSpells.consumingFury.mobsToKill - killCount;
        if (killsLeft > 0)
            UtilChat.addChatMessage(player, "Kills left: " + killCount);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
