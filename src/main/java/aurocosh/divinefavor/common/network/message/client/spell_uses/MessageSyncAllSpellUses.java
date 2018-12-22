package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.player_data.spell_count.ISpellUsesHandler;
import aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler;
import aurocosh.divinefavor.common.player_data.spell_count.SpellUsesStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncAllSpellUses extends NetworkWrappedClientMessage {
    public NBTTagCompound cmp;

    public MessageSyncAllSpellUses() {
    }

    public MessageSyncAllSpellUses(ISpellUsesHandler usesHandler) {
        cmp = SpellUsesStorage.getNbtTagCompound(usesHandler);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ISpellUsesHandler usesHandler = player.getCapability(SpellUsesDataHandler.CAPABILITY_SPELL_USES, null);
        assert usesHandler != null;
        SpellUsesStorage.setDataFromNBT(usesHandler, cmp);
    }
}