package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.ITalismanUsesHandler;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesData;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class MessageSyncAllTalismanUses extends NetworkWrappedClientMessage {
    private static String TAG_SPELL_USES = "SpellUses";

    public NBTTagCompound cmp;

    public MessageSyncAllTalismanUses() {
    }

    public MessageSyncAllTalismanUses(ITalismanUsesHandler usesHandler) {
        cmp = getNbtTagCompound(usesHandler);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ITalismanUsesHandler usesHandler = player.getCapability(TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        setDataFromNBT(usesHandler, cmp);
    }

    public static NBTTagCompound getNbtTagCompound(ITalismanUsesHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        Map<Integer, TalismanUsesData> dataMap = instance.getAllUses();
        int[] serializedMap = new int[dataMap.size() * 3];
        int i = 0;
        for (Map.Entry<Integer, TalismanUsesData> entry : dataMap.entrySet()) {
            serializedMap[i++] = entry.getKey();

            TalismanUsesData usesData = entry.getValue();
            serializedMap[i++] = usesData.getMaxSpellUses();
            serializedMap[i++] = usesData.getSpellUses();
        }
        tag.setIntArray(TAG_SPELL_USES, serializedMap);
        return tag;
    }

    public static void setDataFromNBT(ITalismanUsesHandler instance, NBTTagCompound nbt) {
        Map<Integer, TalismanUsesData> dataMap = new HashMap<>();
        int[] serializedMap = nbt.getIntArray(TAG_SPELL_USES);
        int i = 0;
        while (i < serializedMap.length) {
            int talismanId = serializedMap[i++];
            int maxSpellUses = serializedMap[i++];
            int spellUses = serializedMap[i++];
            dataMap.put(talismanId, new TalismanUsesData(maxSpellUses, spellUses));
        }
        instance.setAllUses(dataMap);
    }
}