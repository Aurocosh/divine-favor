package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist.CrawlingMistData;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class PotionCrawlingMist extends ModPotion {
    private static final int FRAMES_TO_INIT_FOG = 5;
    private static final int CURE_DISTANCE_SQ = ConfigArrow.crawlingMist.cureDistance * ConfigArrow.crawlingMist.cureDistance;
    private static final LoopedCounter CURE_COUNTER = new LoopedCounter(ConfigArrow.crawlingMist.cureRate);
    private static int initFrames = FRAMES_TO_INIT_FOG;

    public PotionCrawlingMist() {
        super("crawling_mist", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            livingBase.removePotionEffect(ModCurses.crawling_mist);
        else {
            EntityPlayer player = (EntityPlayer) livingBase;
            CrawlingMistData mistData = PlayerData.get(player).getCrawlingMistData();
            mistData.setMistOrigin(livingBase.getPosition());
        }
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (!CURE_COUNTER.isFinished())
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        CrawlingMistData mistData = PlayerData.get(player).getCrawlingMistData();
        BlockPos mistOrigin = mistData.getMistOrigin();
        double distanceSq = mistOrigin.distanceSq(livingBase.getPosition());
        if (distanceSq > CURE_DISTANCE_SQ)
            livingBase.removePotionEffect(ModCurses.crawling_mist);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onFogColor(EntityViewRenderEvent.FogColors event) {
        if (!isActive(event))
            return;
        event.setRed(0.7F);
        event.setGreen(0.7F);
        event.setBlue(0.98F);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (isActive(event)) {
            if (initFrames-- <= 0) {
                event.setDensity(0.85F);
                GlStateManager.setFogStart(ConfigArrow.crawlingMist.fogStart);
                GlStateManager.setFogEnd(ConfigArrow.crawlingMist.fogEnd);
                event.setCanceled(true);
            }
        }
        else
            initFrames = FRAMES_TO_INIT_FOG;
    }

    private static boolean isActive(EntityViewRenderEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return false;
        EntityPlayer player = (EntityPlayer) entity;
        return player.isPotionActive(ModCurses.crawling_mist);
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            CURE_COUNTER.tick();
    }
}
