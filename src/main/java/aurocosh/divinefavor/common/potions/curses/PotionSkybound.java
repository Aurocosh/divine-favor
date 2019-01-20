package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class PotionSkybound extends ModPotion {
    private static final float upwardMotion = 0.3f;
    private static final float TOLERANCE = 0.97f;
    private static final Vec3d upVector = new Vec3d(0,1,0);

    public PotionSkybound() {
        super("skybound", false, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        livingBase.motionY = upwardMotion;
        livingBase.onGround = false;
        if(isLookingUp(livingBase))
            livingBase.removePotionEffect(ModCurses.skybound);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    private boolean isLookingUp(EntityLivingBase livingBase) {
        return upVector.dotProduct(livingBase.getLookVec()) >= TOLERANCE;
    }
}
