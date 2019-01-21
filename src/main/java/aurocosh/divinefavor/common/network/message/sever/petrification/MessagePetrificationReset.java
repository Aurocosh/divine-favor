package aurocosh.divinefavor.common.network.message.sever.petrification;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessagePetrificationReset extends NetworkWrappedClientMessage {
    public MessagePetrificationReset() {
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        PetrificationData petrification = LivingData.get(player).getPetrificationData();
        petrification.resetCureTimer();
    }
}
