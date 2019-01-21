package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.living.limp_leg.ILimpLegHandler;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static aurocosh.divinefavor.common.custom_data.living.limp_leg.LimpLegDataHandler.CAPABILITY_LIMP_LEG;

@Mod.EventBusSubscriber
public class PotionLimpLeg extends ModPotion {
    public PotionLimpLeg() {
        super("limp_leg", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        ILimpLegHandler handler = livingBase.getCapability(CAPABILITY_LIMP_LEG, null);
        assert handler != null;
        handler.resetCureTimer();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;
        if(livingBase.isSneaking()) {
            ILimpLegHandler handler = livingBase.getCapability(CAPABILITY_LIMP_LEG, null);
            assert handler != null;
            if(handler.cureTick())
                livingBase.removePotionEffect(ModCurses.limp_leg);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(!entity.isPotionActive(ModCurses.limp_leg))
            return;
        entity.motionY = 0;
        if (entity.isSprinting())
        {
            float f = entity.rotationYaw * 0.017453292F;
            entity.motionX += (double)(MathHelper.sin(f) * 0.2F);
            entity.motionZ -= (double)(MathHelper.cos(f) * 0.2F);
        }
        entity.isAirBorne = false;
    }
}
