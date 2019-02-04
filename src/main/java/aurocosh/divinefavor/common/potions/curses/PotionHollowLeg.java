package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PotionHollowLeg extends ModPotion {
    public static final float EXAUSTION_VALUE = 2f;
    public static final int EXAUSTION_RATE = UtilTick.secondsToTicks(1);
    private static final TickCounter COUNTER = new TickCounter(EXAUSTION_RATE);

    public PotionHollowLeg() {
        super("hollow_leg", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if(!(livingBase instanceof EntityPlayer))
            livingBase.removePotionEffect(ModCurses.hollow_leg);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;
        if(!COUNTER.isFinished())
            return;
        if(!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        player.getFoodStats().addExhaustion(20);
//        player.getFoodStats().addStats(1, 0.1F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onItemUse(LivingEntityUseItemEvent.Finish event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(!entity.isPotionActive(ModCurses.hollow_leg))
            return;
        ItemStack stack = event.getResultStack();
        if(stack.getItem() != Items.APPLE)
            return;
        EntityPlayer player = (EntityPlayer) entity;
        player.removePotionEffect(ModCurses.hollow_leg);
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            COUNTER.tick();
    }
}
