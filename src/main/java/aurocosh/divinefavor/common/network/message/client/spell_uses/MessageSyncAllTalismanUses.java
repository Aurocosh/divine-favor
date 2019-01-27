package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.FavorValue;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.FavorData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncAllTalismanUses extends NetworkWrappedClientMessage {
    private static String TAG_FAVOR_VALUES = "FavorValues";

    public NBTTagCompound tag;

    public MessageSyncAllTalismanUses() {
    }

    public MessageSyncAllTalismanUses(FavorData data) {
        tag = getNbtTagCompound(data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        FavorData data = PlayerData.get(player).getFavorData();
        setDataFromNBT(data, tag);
    }

    public static NBTTagCompound getNbtTagCompound(FavorData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        FavorValue[] favorValues = instance.getFavorValues();
        int[] serializedFavors = new int[favorValues.length * 4];
        int i = 0;
        for (int j = 0; j < favorValues.length; j++) {
            FavorValue uses = favorValues[j];
            serializedFavors[i++] = j;
            serializedFavors[i++] = uses.getFavor();
            serializedFavors[i++] = uses.getMinLimit();
            serializedFavors[i++] = uses.getMaxLimit();
        }
        tag.setIntArray(TAG_FAVOR_VALUES, serializedFavors);
        return tag;
    }

    public static void setDataFromNBT(FavorData instance, NBTTagCompound nbt) {
        int[] serializedMap = nbt.getIntArray(TAG_FAVOR_VALUES);
        FavorValue[] favorValues = new FavorValue[serializedMap.length/4];
        int i = 0;
        while (i < serializedMap.length) {
            int talismanId = serializedMap[i++];
            int spellUses = serializedMap[i++];
            int minSpellUses = serializedMap[i++];
            int maxSpellUses = serializedMap[i++];
            favorValues[talismanId] = new FavorValue(spellUses, minSpellUses, maxSpellUses);
        }
        instance.setFavorValues(favorValues);
    }
}