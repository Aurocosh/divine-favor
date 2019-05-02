package aurocosh.divinefavor.common.network.message.client.syncing;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashData;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncWindLeash extends WrappedClientMessage {
    public double x;
    public double z;

    public MessageSyncWindLeash() {
    }

    public MessageSyncWindLeash(Vec3d vec3d) {
        this.x = vec3d.x;
        this.z = vec3d.z;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        WindLeashData windLeash = LivingData.INSTANCE.get(player).getWindLeashData();
        windLeash.setVector(new Vec3d(x, 0, z));
    }
}
