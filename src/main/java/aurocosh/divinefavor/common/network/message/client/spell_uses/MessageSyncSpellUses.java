package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.player_data.spell_count.ISpellUsesHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler.CAPABILITY_SPELL_USES;

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
        ISpellUsesHandler usesHandler = player.getCapability(CAPABILITY_SPELL_USES, null);
        assert usesHandler != null;
        usesHandler.setSpellUses(talismanId, spellUses);
    }
}
