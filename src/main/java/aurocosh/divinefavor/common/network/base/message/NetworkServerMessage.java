package aurocosh.divinefavor.common.network.base.message;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class NetworkServerMessage extends NetworkAutoMessage {
    public NetworkServerMessage() {
    }

    @Override
    public final IMessage handleMessage(MessageContext context) {
        EntityPlayerMP serverPlayer = context.getServerHandler().player;
        serverPlayer.getServerWorld().addScheduledTask(() -> handleSafe(serverPlayer));
        return null;
    }

    protected abstract void handleSafe(EntityPlayerMP serverPlayer);
}
