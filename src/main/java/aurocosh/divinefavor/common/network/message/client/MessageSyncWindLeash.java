package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.living.wind_leash.IWindLeashHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.custom_data.living.wind_leash.WindLeashDataHandler.CAPABILITY_WIND_LEASH;

public class MessageSyncWindLeash extends NetworkWrappedClientMessage {
    public double x;
    public double z;

    public MessageSyncWindLeash() {
    }

    public MessageSyncWindLeash(double x, double z) {
        this.x = x;
        this.z = z;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IWindLeashHandler handler = player.getCapability(CAPABILITY_WIND_LEASH, null);
        assert handler != null;
        handler.setVector(new Vec3d(x, 0, z));
    }
}
