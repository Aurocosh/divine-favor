package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorValue;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncFavor extends NetworkWrappedClientMessage {

    public int favorId;
    public int value;
    public int regen;
    public int minLimit;
    public int maxLimit;

    public MessageSyncFavor() {
    }

    public MessageSyncFavor(ModFavor favor, FavorData favorData) {
        this.favorId = favor.getId();
        FavorValue favorValue = favorData.get(favor);
        value = favorValue.getValue();
        regen = favorValue.getRegen();
        minLimit = favorValue.getMinLimit();
        maxLimit = favorValue.getMaxLimit();
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        FavorData favorData = PlayerData.get(player).getFavorData();
        FavorValue favorValue = favorData.get(favorId);
        favorValue.setMaxLimit(maxLimit);
        favorValue.setMinLimit(minLimit);
        favorValue.setRegen(regen);
        favorValue.setValue(value);
    }
}
