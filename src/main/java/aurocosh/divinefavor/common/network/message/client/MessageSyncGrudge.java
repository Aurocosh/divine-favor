package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.grudge.GrudgeData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncGrudge extends NetworkWrappedClientMessage {
    public String mobTypeId;

    public MessageSyncGrudge() {
    }

    public MessageSyncGrudge(ResourceLocation mobTypeId) {
        this.mobTypeId = mobTypeId.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        GrudgeData grudgeData = PlayerData.get(player).getGrudgeData();
        grudgeData.setMobTypeId(new ResourceLocation(mobTypeId));
    }
}
