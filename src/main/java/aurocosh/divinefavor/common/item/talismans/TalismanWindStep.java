package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class TalismanWindStep extends ItemTalisman {
    private static float ADDED_VELOCITY_Y = 1.2f;
    private static float ADDED_VELOCITY_XZ = 3;
    private static final int USES = 10;

    public TalismanWindStep() {
        super("wind_step", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        addVelocity(context.player);
    }

    @Override
    protected void performActionClient(TalismanContext context) {
        addVelocity(context.player);
    }

    private void addVelocity(EntityPlayer player){
        Vec3d lookVec = player.getLookVec();
        player.motionX += lookVec.x * ADDED_VELOCITY_XZ;
        player.motionY += lookVec.y * ADDED_VELOCITY_Y;
        player.motionZ += lookVec.z * ADDED_VELOCITY_XZ;
    }
}
