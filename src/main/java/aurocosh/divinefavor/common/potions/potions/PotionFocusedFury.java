package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.focused_fury.IFocusedFuryHandler;
import aurocosh.divinefavor.common.network.message.client.MessageSyncFury;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static aurocosh.divinefavor.common.custom_data.player.focused_fury.FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY;

@Mod.EventBusSubscriber
public class PotionFocusedFury extends ModPotion {
    public static float EXTRA_DAMAGE = 10;

    public PotionFocusedFury() {
        super("focused_fury", true, 0x7FB8A4);
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
        if (!player.isPotionActive(ModPotions.focused_fury))
            return;

        IFocusedFuryHandler furyHandler = player.getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert furyHandler != null;
        if(!furyHandler.hasFury())
            return;
        if (furyHandler.hasFury((IMob) entityMob))
            event.setAmount(event.getAmount() + EXTRA_DAMAGE);
        else
            event.setCanceled(true);
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
        if (!player.isPotionActive(ModPotions.focused_fury))
            return;
        Entity mob = event.getEntity();
        if (!(mob instanceof IMob))
            return;

        IFocusedFuryHandler furyHandler = player.getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert furyHandler != null;
        if (furyHandler.hasFury())
            return;
        furyHandler.setFury((IMob) mob);
        MessageSyncFury.sync(player, furyHandler.getMobTypeId());
    }

    @Override
    public void renderCustomInvText(int x, int y, PotionEffect effect, Minecraft mc) {
        String potionName = I18n.format(getName());
        mc.fontRenderer.drawStringWithShadow(potionName, (float) (x + 10 + 18), (float) (y + 6), 16777215);

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IFocusedFuryHandler furyHandler = player.getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert furyHandler != null;

        String duration = Potion.getPotionDurationString(effect, 1.0F);
        String mobName = I18n.format(furyHandler.getMobName());
        mc.fontRenderer.drawStringWithShadow(duration + " " + mobName, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        if(!(livingBase instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) livingBase;
        IFocusedFuryHandler furyHandler = player.getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert furyHandler != null;
        furyHandler.setMobTypeId(-1);
        MessageSyncFury.sync(player, furyHandler.getMobTypeId());
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
