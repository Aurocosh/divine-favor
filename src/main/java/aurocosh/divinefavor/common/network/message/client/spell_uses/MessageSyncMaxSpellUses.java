package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.ITalismanUsesHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

public class MessageSyncMaxSpellUses extends NetworkWrappedClientMessage {

    public int talismanId;
    public int maxSpellUses;

    public MessageSyncMaxSpellUses() {
    }

    public MessageSyncMaxSpellUses(int talismanId, int maxSpellUses) {
        this.talismanId = talismanId;
        this.maxSpellUses = maxSpellUses;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ITalismanUsesHandler usesHandler = player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        usesHandler.setMaxUses(talismanId, maxSpellUses);
    }
}
