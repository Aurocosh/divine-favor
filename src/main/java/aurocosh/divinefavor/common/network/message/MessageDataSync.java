package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkAutoMessage;
import aurocosh.divinefavor.common.player_data.favor.FavorStorage;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class MessageDataSync extends NetworkAutoMessage {

    public NBTTagCompound cmp;

    public MessageDataSync() {
    }

    public MessageDataSync(IFavorHandler favorHandler) {
        cmp = FavorStorage.getNbtTagCompound(favorHandler);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleMessage(MessageContext context) {
        DivineFavor.proxy.addScheduledTaskClient(this::handle);
        return null;
    }

    private void handle() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler != null)
            FavorStorage.setDataFromNBT(favorHandler, cmp);
    }
}