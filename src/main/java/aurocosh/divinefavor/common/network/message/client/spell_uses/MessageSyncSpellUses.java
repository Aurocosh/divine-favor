package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.player_data.talisman_uses.ITalismanUsesHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

public class MessageSyncSpellUses extends NetworkWrappedClientMessage {

    public int talismanId;
    public int spellUses;

    public MessageSyncSpellUses() {
    }

    public MessageSyncSpellUses(int talismanId, int spellUses) {
        this.talismanId = talismanId;
        this.spellUses = spellUses;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ITalismanUsesHandler usesHandler = player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        usesHandler.setUses(talismanId, spellUses);
    }
}
