package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class PotionSkyfall extends ModPotion {
    private static final float upwardMotion = 0.3f;
    private static final float TOLERANCE = 0.97f;
    private static final Vec3d UP_VECTOR = new Vec3d(0,1,0);

    public PotionSkyfall() {
        super("skyfall", false, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        livingBase.motionY = upwardMotion;
        livingBase.onGround = false;
        livingBase.setAIMoveSpeed(0.1F);
        if(isLookingUp(livingBase))
            livingBase.removePotionEffect(ModCurses.skyfall);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    private boolean isLookingUp(EntityLivingBase livingBase) {
        return UP_VECTOR.dotProduct(livingBase.getLookVec()) >= TOLERANCE;
    }
}
