package aurocosh.divinefavor.common.network.message.sever.petrification;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.living.petrification.IPetrificationHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.custom_data.living.petrification.PetrificationDataHandler.CAPABILITY_PETRIFICATION;

public class MessagePetrificationReset extends NetworkWrappedClientMessage {
    public MessagePetrificationReset() {
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IPetrificationHandler handler = player.getCapability(CAPABILITY_PETRIFICATION, null);
        assert handler != null;
        handler.resetCureTimer();
    }
}
