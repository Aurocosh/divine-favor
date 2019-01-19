package aurocosh.divinefavor.common.player_data.talisman_uses;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Handles the actual read/write of the nbt.
public class TalismanUsesStorage implements Capability.IStorage<ITalismanUsesHandler> {
    public static NBTTagCompound getNbtTagCompound(ITalismanUsesHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        Map<Integer, TalismanUsesData> dataMap = instance.getAllUses();
        Map<Integer, ItemTalisman> talismanMap = ModMappers.talismans.getIdMap();
        for (Map.Entry<Integer, TalismanUsesData> entry : dataMap.entrySet()) {
            TalismanUsesData usesData = entry.getValue();
            int[] spellUses = new int[]{usesData.getMaxSpellUses(), usesData.getSpellUses()};
            ItemTalisman talisman = talismanMap.get(entry.getKey());
            tag.setIntArray(talisman.getName(), spellUses);
        }
        return tag;
    }

    public static void setDataFromNBT(ITalismanUsesHandler instance, NBTTagCompound nbt) {
        Collection<ItemTalisman> talismans = ModMappers.talismans.getIdMap().values();
        Map<Integer, TalismanUsesData> dataMap = new HashMap<>();
        for (ItemTalisman talisman : talismans) {
            if(!nbt.hasKey(talisman.getName()))
                continue;
            int[] spellUses = nbt.getIntArray(talisman.getName());
            dataMap.put(talisman.getId(), new TalismanUsesData(spellUses[0],spellUses[1]));
        }
        instance.setAllUses(dataMap);
    }

    @Override
    public NBTBase writeNBT(Capability<ITalismanUsesHandler> capability, ITalismanUsesHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT(Capability<ITalismanUsesHandler> capability, ITalismanUsesHandler instance, EnumFacing side, NBTBase nbt) {
        setDataFromNBT(instance, (NBTTagCompound) nbt);
    }
}
