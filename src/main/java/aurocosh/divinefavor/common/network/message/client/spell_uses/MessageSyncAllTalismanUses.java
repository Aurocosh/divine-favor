package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.TalismanUses;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.TalismanUsesData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncAllTalismanUses extends NetworkWrappedClientMessage {
    private static String TAG_SPELL_USES = "SpellUses";

    public NBTTagCompound tag;

    public MessageSyncAllTalismanUses() {
    }

    public MessageSyncAllTalismanUses(TalismanUsesData data) {
        tag = getNbtTagCompound(data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        TalismanUsesData data = PlayerData.get(player).getTalismanUsesData();
        setDataFromNBT(data, tag);
    }

    public static NBTTagCompound getNbtTagCompound(TalismanUsesData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        TalismanUses[] talismanUses = instance.getAllUses();
        int[] serializedUses = new int[talismanUses.length * 3];
        int i = 0;
        for (int j = 0; j < talismanUses.length; j++) {
            TalismanUses uses = talismanUses[j];
            serializedUses[i++] = j;
            serializedUses[i++] = uses.getMaxUses();
            serializedUses[i++] = uses.getUses();
        }
        tag.setIntArray(TAG_SPELL_USES, serializedUses);
        return tag;
    }

    public static void setDataFromNBT(TalismanUsesData instance, NBTTagCompound nbt) {
        int[] serializedMap = nbt.getIntArray(TAG_SPELL_USES);
        TalismanUses[] talismanUses = new TalismanUses[serializedMap.length/3];
        int i = 0;
        while (i < serializedMap.length) {
            int talismanId = serializedMap[i++];
            int maxSpellUses = serializedMap[i++];
            int spellUses = serializedMap[i++];
            talismanUses[talismanId] = new TalismanUses(maxSpellUses, spellUses);
        }
        instance.setAllUses(talismanUses);
    }
}