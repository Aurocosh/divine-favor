package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.living.wind_leash.IWindLeashHandler;
import aurocosh.divinefavor.common.network.message.client.MessageSyncWindLeash;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import static aurocosh.divinefavor.common.custom_data.living.wind_leash.WindLeashDataHandler.CAPABILITY_WIND_LEASH;

public class PotionWindLeash extends ModPotion {
    private static final double MOTION_SPEED = 0.7f;
    private static final double PLAYER_MULTIPLIER = 1;
    private static final double TOLERANCE = 0.9f;

    public PotionWindLeash() {
        super("wind_leash", false, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);

        double x = UtilRandom.nextDouble(-1,1);
        double z = UtilRandom.nextDouble(-1,1);
        Vec3d vec3d = new Vec3d(x,0,z);

        IWindLeashHandler handler = livingBase.getCapability(CAPABILITY_WIND_LEASH, null);
        assert handler != null;
        Vec3d vector = vec3d.normalize().scale(MOTION_SPEED);
        if(livingBase instanceof EntityPlayer)
            vector = vector.scale(PLAYER_MULTIPLIER);
        handler.setVector(vector);

        if(livingBase instanceof EntityPlayer)
            new MessageSyncWindLeash(vector.x, vector.z).sendTo((EntityPlayer) livingBase);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        IWindLeashHandler handler = livingBase.getCapability(CAPABILITY_WIND_LEASH, null);
        assert handler != null;
        Vec3d vector = handler.getVector();

        livingBase.motionX = vector.x;
        livingBase.motionZ = vector.z;

        if(livingBase.world.isRemote)
            return;
        if(isLookingInDirection(livingBase, handler.getNormalizedVector()))
            livingBase.removePotionEffect(ModCurses.wind_leash);
    }

    private boolean isLookingInDirection(EntityLivingBase livingBase, Vec3d vec3d) {
        return vec3d.dotProduct(livingBase.getLookVec()) >= TOLERANCE;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
