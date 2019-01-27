package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncFavorValue extends NetworkWrappedClientMessage {

    public int favorId;
    public int value;

    public MessageSyncFavorValue() {
    }

    public MessageSyncFavorValue(ModFavor favor, FavorData favorData) {
        this.favorId = favor.getId();
        this.value = favorData.get(favor).getValue();
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        FavorData favorData = PlayerData.get(player).getFavorData();
        favorData.get(favorId).setValue(value);
    }
}
