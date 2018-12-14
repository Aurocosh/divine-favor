package aurocosh.divinefavor.common.network.base;

import aurocosh.divinefavor.DivineFavor;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class NetworkClientMessage extends NetworkAutoMessage{
    public NetworkClientMessage() { }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleMessage(MessageContext context) {
        DivineFavor.proxy.addScheduledTaskClient(this::handle);
        return null;
    }

    @SideOnly(Side.CLIENT)
    protected abstract void handle();
}
