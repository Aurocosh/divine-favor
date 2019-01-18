package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class SpellWindStep extends ModSpell {
    private static float ADDED_VELOCITY_Y = 1.2f;
    private static float ADDED_VELOCITY_XZ = 3;

    @Override
    protected void performActionServer(SpellContext context) {
        addVelocity(context.player);
    }

    @Override
    protected void performActionClient(SpellContext context) {
        addVelocity(context.player);
    }

    private void addVelocity(EntityPlayer player){
        Vec3d lookVec = player.getLookVec();
        player.motionX += lookVec.x * ADDED_VELOCITY_XZ;
        player.motionY += lookVec.y * ADDED_VELOCITY_Y;
        player.motionZ += lookVec.z * ADDED_VELOCITY_XZ;
    }
}
