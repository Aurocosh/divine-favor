package aurocosh.divinefavor.common.player_data.spell_count;

import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Handles the actual read/write of the nbt.
public class SpellUsesStorage implements Capability.IStorage<ISpellUsesHandler> {
    public static NBTTagCompound getNbtTagCompound(ISpellUsesHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        Map<Integer, SpellUsesData> dataMap = instance.getAllSpellUses();
        Map<Integer, ItemTalisman> talismanMap = ModMappers.talismans.getIdMap();
        for (Map.Entry<Integer, SpellUsesData> entry : dataMap.entrySet()) {
            SpellUsesData usesData = entry.getValue();
            int[] spellUses = new int[]{usesData.getMaxSpellUses(), usesData.getSpellUses()};
            ItemTalisman talisman = talismanMap.get(entry.getKey());
            tag.setIntArray(talisman.getName(), spellUses);
        }
        return tag;
    }

    public static void setDataFromNBT(ISpellUsesHandler instance, NBTTagCompound nbt) {
        Collection<ItemTalisman> talismans = ModMappers.talismans.getIdMap().values();
        Map<Integer, SpellUsesData> dataMap = new HashMap<>();
        for (ItemTalisman talisman : talismans) {
            if(!nbt.hasKey(talisman.getName()))
                continue;
            int[] spellUses = nbt.getIntArray(talisman.getName());
            dataMap.put(talisman.getId(), new SpellUsesData(spellUses[0],spellUses[1]));
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
