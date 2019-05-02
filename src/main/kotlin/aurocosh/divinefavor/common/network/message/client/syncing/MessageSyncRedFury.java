package aurocosh.divinefavor.common.network.message.client.syncing;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury.RedFuryData;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncRedFury extends WrappedClientMessage {
    public Vec3d vector;

    public MessageSyncRedFury() {
    }

    public MessageSyncRedFury(Vec3d vector) {
        this.vector = vector;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        RedFuryData redFuryData = PlayerData.INSTANCE.get(player).getRedFuryData();
        redFuryData.setVector(vector);
    }
}
