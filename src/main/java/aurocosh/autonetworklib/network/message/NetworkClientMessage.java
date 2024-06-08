package aurocosh.autonetworklib.network.message;

import aurocosh.divinefavor.DivineFavor;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class NetworkClientMessage extends NetworkAutoMessage {
    @Override
    @SideOnly(Side.CLIENT)
    public final IMessage handleMessage(MessageContext context) {
        DivineFavor.proxy.addScheduledTaskClient(this::handleSafe);
        return null;
    }

    @SideOnly(Side.CLIENT)
    protected abstract void handleSafe();
}
