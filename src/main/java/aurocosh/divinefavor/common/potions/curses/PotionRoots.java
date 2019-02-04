package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionRoots extends ModPotion {
    private final static float SLOWNESS_FORCE = 4;
    private static final float TOLERANCE = 0.97f;
    private static final Vec3d DOWN_VECTOR = new Vec3d(0,-1,0);


    public PotionRoots() {
        super("roots", true, 0x7FB8A4);
        setIsCurse(true);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "817e7bc4-8ad5-4323-9131-aa71236a1b83", -SLOWNESS_FORCE, 2);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;
        if(isLookingDown(livingBase))
            livingBase.removePotionEffect(ModCurses.roots);
    }

    private boolean isLookingDown(EntityLivingBase livingBase) {
        return DOWN_VECTOR.dotProduct(livingBase.getLookVec()) >= TOLERANCE;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
