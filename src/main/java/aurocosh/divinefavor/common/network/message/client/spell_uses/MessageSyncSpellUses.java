package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.TalismanUsesData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncSpellUses extends NetworkWrappedClientMessage {

    public int talismanId;
    public int spellUses;

    public MessageSyncSpellUses() {
    }

    public MessageSyncSpellUses(int talismanId, TalismanUsesData usesData) {
        this.talismanId = talismanId;
        this.spellUses = usesData.getUses(talismanId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        TalismanUsesData usesData = PlayerData.get(player).getTalismanUsesData();
        usesData.setUses(talismanId, spellUses);
    }
}
