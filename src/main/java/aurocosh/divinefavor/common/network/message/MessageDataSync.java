package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler.PlayerData;
import aurocosh.divinefavor.common.network.base.NetworkAutoMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDataSync extends NetworkAutoMessage {

    public NBTTagCompound cmp;

    public MessageDataSync() { }

    public MessageDataSync(PlayerData data) {
        cmp = new NBTTagCompound();
        data.writeToNBT(cmp);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleMessage(MessageContext context) {
        ClientTickHandler.scheduledActions.add(() -> {
            PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
            data.readFromNBT(cmp);
        });

        return null;
    }

}