package aurocosh.divinefavor.common.network.message.client.activity;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSpiritBecameInactive extends WrappedClientMessage {
    public int spiritId;

    public MessageSpiritBecameInactive() {
    }

    public MessageSpiritBecameInactive(int spiritId) {
        this.spiritId = spiritId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ModSpirit spirit = ModMappers.spirits.get(spiritId);
        String name = I18n.format(spirit.getNameTranslationKey());
        String message = I18n.format("divinefavor:spirit_became_inactive", name);
        player.sendMessage(new TextComponentString(message));
    }
}
