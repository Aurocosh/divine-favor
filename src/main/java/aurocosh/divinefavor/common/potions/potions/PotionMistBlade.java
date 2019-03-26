package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilMob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class PotionMistBlade extends ModPotionToggle {
    private static int FRAMES_TO_INIT_FOG = 5;
    private static int intitFrames = FRAMES_TO_INIT_FOG;

    public PotionMistBlade() {
        super("mist_blade", true, 0x7FB8A4);
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
        if (!player.isPotionActive(ModPotions.mist_blade))
            return;
        event.setAmount(event.getAmount() + ConfigSpells.mistBlade.extraDamage);

        if (!(UtilMob.isMobRanged(event.getEntity())))
            return;
        event.setAmount(event.getAmount() + ConfigSpells.mistBlade.extraRangedDamage);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onFogColors(EntityViewRenderEvent.FogColors event) {
        if (!isMistBladeActive(event))
            return;
        event.setRed(0.7F);
        event.setGreen(0.7F);
        event.setBlue(0.98F);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (isMistBladeActive(event)) {
            if (intitFrames-- <= 0) {
                event.setDensity(0.85F);
                GlStateManager.setFogStart(ConfigSpells.mistBlade.fogStart);
                GlStateManager.setFogEnd(ConfigSpells.mistBlade.fogEnd);
                event.setCanceled(true);
            }
        }
        else
            intitFrames = FRAMES_TO_INIT_FOG;
    }

    private static boolean isMistBladeActive(EntityViewRenderEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return false;
        EntityPlayer player = (EntityPlayer) entity;
        return player.isPotionActive(ModPotions.mist_blade);
    }
}
