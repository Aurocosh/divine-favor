package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class SpellWindStep extends ModSpell {
    private static float ADDED_VELOCITY_Y = 1.2f;
    private static float ADDED_VELOCITY_XZ = 3;

    public SpellWindStep() {
        super("wind_step");
    }

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
