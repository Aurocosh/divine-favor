package aurocosh.divinefavor.common.player_data.spell_count;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.HashMap;
import java.util.Map;

// Handles the actual read/write of the nbt.
public class SpellUsesStorage implements Capability.IStorage<ISpellUsesHandler> {
    private static String TAG_SPELL_USES = "SpellUses";

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

    @Override
    public NBTBase writeNBT(Capability<ISpellUsesHandler> capability, ISpellUsesHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT(Capability<ISpellUsesHandler> capability, ISpellUsesHandler instance, EnumFacing side, NBTBase nbt) {
        setDataFromNBT(instance, (NBTTagCompound) nbt);
    }
}
