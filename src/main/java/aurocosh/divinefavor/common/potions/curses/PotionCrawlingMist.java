package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistData;
import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilTick;
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
    public static final int CURE_RATE = UtilTick.secondsToTicks(10);
    private static final int FRAMES_TO_INIT_FOG = 5;
    private static final float FOG_START = 20;
    private static final float FOG_END = 30;
    private static final int CURE_DISTANCE = 20;
    private static final int CURE_DISTANCE_SQ = CURE_DISTANCE * CURE_DISTANCE;
    private static final TickCounter CURE_COUNTER = new TickCounter(CURE_RATE);
    private static int intitFrames = FRAMES_TO_INIT_FOG;

    public PotionCrawlingMist() {
        super("crawling_mist", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if(!(livingBase instanceof EntityPlayer))
           livingBase.removePotionEffect(ModCurses.crawling_mist);
        else {
            EntityPlayer player = (EntityPlayer) livingBase;
            CrawlingMistData mistData = PlayerData.get(player).getCrawlingMistData();
            mistData.setMistOrigin(livingBase.getPosition());
        }
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;
        if (!CURE_COUNTER.isFinished())
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        CrawlingMistData mistData = PlayerData.get(player).getCrawlingMistData();
        BlockPos pos = mistData.getMistOrigin();
        Vector3i distance = new Vector3i(pos.subtract(livingBase.getPosition()));
        int distanceSq = distance.magnitudeSquare();
        if(distanceSq > CURE_DISTANCE_SQ)
            livingBase.removePotionEffect(ModCurses.crawling_mist);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onFogDensity(EntityViewRenderEvent.FogColors event) {
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
            if (intitFrames-- <= 0) {
                event.setDensity(0.85F);
                GlStateManager.setFogStart(FOG_START);
                GlStateManager.setFogEnd(FOG_END);
                event.setCanceled(true);
            }
        }
        else
            intitFrames = FRAMES_TO_INIT_FOG;
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
