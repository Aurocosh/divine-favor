package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeData;
import aurocosh.divinefavor.common.network.message.client.MessageSyncGrudge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionGrudge extends ModPotionToggle {
    public static float EXTRA_DAMAGE = 6;

    public PotionGrudge() {
        super("grudge", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity entity = source.getTrueSource();
        if (!(entity instanceof EntityPlayer))
            return;
        Entity entityMob = event.getEntity();
        if (!(entityMob instanceof IMob))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!player.isPotionActive(ModPotions.grudge))
            return;

        GrudgeData grudgeData = PlayerData.get(player).getGrudgeData();
        if(!grudgeData.hasGrudge((IMob) entityMob))
            return;
        event.setAmount(event.getAmount() + EXTRA_DAMAGE);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;
        GrudgeData grudgeData = PlayerData.get(player).getGrudgeData();
        DamageSource source = event.getSource();
        Entity attacker = source.getTrueSource();
        if (attacker instanceof IMob)
            grudgeData.setGrudge((IMob) attacker);
        else
            grudgeData.setMobTypeId(-1);
        new MessageSyncGrudge(grudgeData.getMobTypeId()).sendTo(player);
    }

    @Override
    public void renderCustomInvText(int x, int y, PotionEffect effect, Minecraft mc) {
        String potionName = I18n.format(getName());
        mc.fontRenderer.drawStringWithShadow(potionName, (float) (x + 10 + 18), (float) (y + 6), 16777215);

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        GrudgeData grudgeData = PlayerData.get(player).getGrudgeData();
        String mobName = I18n.format(grudgeData.getMobName());
        mc.fontRenderer.drawStringWithShadow(mobName, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
