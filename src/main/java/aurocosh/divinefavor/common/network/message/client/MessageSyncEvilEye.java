package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye.EvilEyeData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncEvilEye extends NetworkWrappedClientMessage {
    public int severity;

    public MessageSyncEvilEye() {
    }

    public MessageSyncEvilEye(int severity) {
        this.severity = severity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        EvilEyeData evilEyeData = PlayerData.get(player).getEvilEyeData();
        evilEyeData.setSeverity(severity);
    }
}
