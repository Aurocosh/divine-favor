package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler.PlayerData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.arl.network.NetworkMessage;

public class MessageDataSync extends NetworkMessage {

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
            data.lastAvailablePsi = data.availablePsi;
            data.readFromNBT(cmp);
            DivineFavor.proxy.savePersistency();
        });

        return null;
    }

}