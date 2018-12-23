package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.player_data.spell_count.ISpellUsesHandler;
import aurocosh.divinefavor.common.player_data.spell_count.SpellUsesData;
import aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class MessageSyncAllSpellUses extends NetworkWrappedClientMessage {
    private static String TAG_SPELL_USES = "SpellUses";

    public NBTTagCompound cmp;

    public MessageSyncAllSpellUses() {
    }

    public MessageSyncAllSpellUses(ISpellUsesHandler usesHandler) {
        cmp = getNbtTagCompound(usesHandler);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ISpellUsesHandler usesHandler = player.getCapability(SpellUsesDataHandler.CAPABILITY_SPELL_USES, null);
        assert usesHandler != null;
        setDataFromNBT(usesHandler, cmp);
    }

    public static NBTTagCompound getNbtTagCompound(ISpellUsesHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        Map<Integer, SpellUsesData> dataMap = instance.getAllSpellUses();
        int[] serializedMap = new int[dataMap.size() * 3];
        int i = 0;
        for (Map.Entry<Integer, SpellUsesData> entry : dataMap.entrySet()) {
            serializedMap[i++] = entry.getKey();

            SpellUsesData usesData = entry.getValue();
            serializedMap[i++] = usesData.getMaxSpellUses();
            serializedMap[i++] = usesData.getSpellUses();
        }
        tag.setIntArray(TAG_SPELL_USES, serializedMap);
        return tag;
    }

    public static void setDataFromNBT(ISpellUsesHandler instance, NBTTagCompound nbt) {
        Map<Integer, SpellUsesData> dataMap = new HashMap<>();
        int[] serializedMap = nbt.getIntArray(TAG_SPELL_USES);
        int i = 0;
        while (i < serializedMap.length) {
            int talismanId = serializedMap[i++];
            int maxSpellUses = serializedMap[i++];
            int spellUses = serializedMap[i++];
            dataMap.put(talismanId, new SpellUsesData(maxSpellUses, spellUses));
        }
        instance.setAllSpellUses(dataMap);
    }
}