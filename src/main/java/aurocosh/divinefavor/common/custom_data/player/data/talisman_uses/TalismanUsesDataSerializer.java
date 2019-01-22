package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Handles the actual read/write of the nbt.
public class TalismanUsesDataSerializer implements INbtSerializer<TalismanUsesData> {
    public static final String TAG_TALISMAN_USES = "TalismanUses";

    public static NBTTagCompound getNbtTagCompound(TalismanUsesData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        Map<Integer, TalismanUses> dataMap = instance.getAllUses();
        Map<Integer, ItemTalisman> talismanMap = ModMappers.talismans.getIdMap();
        for (Map.Entry<Integer, TalismanUses> entry : dataMap.entrySet()) {
            TalismanUses usesData = entry.getValue();
            int[] spellUses = new int[]{usesData.getMaxUses(), usesData.getUses()};
            ItemTalisman talisman = talismanMap.get(entry.getKey());
            tag.setIntArray(talisman.getName(), spellUses);
        }
        return tag;
    }

    public static void setDataFromNBT(TalismanUsesData instance, NBTTagCompound nbt) {
        Collection<ItemTalisman> talismans = ModMappers.talismans.getIdMap().values();
        Map<Integer, TalismanUses> dataMap = new HashMap<>();
        for (ItemTalisman talisman : talismans) {
            if(!nbt.hasKey(talisman.getName()))
                continue;
            int[] spellUses = nbt.getIntArray(talisman.getName());
            dataMap.put(talisman.getId(), new TalismanUses(spellUses[0],spellUses[1]));
        }
        instance.setAllUses(dataMap);
    }

    @Override
    public void serialize(NBTTagCompound nbt, TalismanUsesData instance) {
        NBTTagCompound usesTag = getNbtTagCompound(instance);
        nbt.setTag(TAG_TALISMAN_USES, usesTag);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, TalismanUsesData instance) {
        if(!nbt.hasKey(TAG_TALISMAN_USES))
            return;
        NBTTagCompound usesTag = nbt.getCompoundTag(TAG_TALISMAN_USES);
        setDataFromNBT(instance, usesTag);
    }
}
