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

import java.util.HashMap;
import java.util.Map;

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
        Map<Integer, TalismanUses> dataMap = instance.getAllUses();
        int[] serializedMap = new int[dataMap.size() * 3];
        int i = 0;
        for (Map.Entry<Integer, TalismanUses> entry : dataMap.entrySet()) {
            serializedMap[i++] = entry.getKey();

            TalismanUses usesData = entry.getValue();
            serializedMap[i++] = usesData.getMaxUses();
            serializedMap[i++] = usesData.getUses();
        }
        tag.setIntArray(TAG_SPELL_USES, serializedMap);
        return tag;
    }

    public static void setDataFromNBT(TalismanUsesData instance, NBTTagCompound nbt) {
        Map<Integer, TalismanUses> dataMap = new HashMap<>();
        int[] serializedMap = nbt.getIntArray(TAG_SPELL_USES);
        int i = 0;
        while (i < serializedMap.length) {
            int talismanId = serializedMap[i++];
            int maxSpellUses = serializedMap[i++];
            int spellUses = serializedMap[i++];
            dataMap.put(talismanId, new TalismanUses(maxSpellUses, spellUses));
        }
        instance.setAllUses(dataMap);
    }
}