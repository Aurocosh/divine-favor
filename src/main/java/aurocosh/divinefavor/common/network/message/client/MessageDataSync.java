package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkClientMessage;
import aurocosh.divinefavor.common.player_data.favor.FavorStorage;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class MessageDataSync extends NetworkClientMessage {
    public NBTTagCompound cmp;

    public MessageDataSync() {
    }

    public MessageDataSync(IFavorHandler favorHandler) {
        cmp = FavorStorage.getNbtTagCompound(favorHandler);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;
        FavorStorage.setDataFromNBT(favorHandler, cmp);
    }
}