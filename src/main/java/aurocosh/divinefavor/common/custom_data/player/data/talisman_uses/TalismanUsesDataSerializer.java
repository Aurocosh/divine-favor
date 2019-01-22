package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

// Handles the actual read/write of the nbt.
public class TalismanUsesDataSerializer implements INbtSerializer<TalismanUsesData> {
    public static final String TAG_TALISMAN_USES = "TalismanUses";

    public static NBTTagCompound getNbtTagCompound(TalismanUsesData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        TalismanUses[] talismanUses = instance.getAllUses();
        List<ItemTalisman> talismans = ModMappers.talismans.getValues();
        for (int i = 0; i < talismanUses.length; i++) {
            TalismanUses uses = talismanUses[i];
            int[] spellUses = new int[]{uses.getMaxUses(), uses.getUses()};
            ItemTalisman talisman = talismans.get(i);
            tag.setIntArray(talisman.getName(), spellUses);
        }
        return tag;
    }

    public static void setDataFromNBT(TalismanUsesData instance, NBTTagCompound nbt) {
        List<ItemTalisman> talismans = ModMappers.talismans.getValues();
        TalismanUses[] talismanUses = instance.getAllUses();
        for (ItemTalisman talisman : talismans) {
            if (!nbt.hasKey(talisman.getName()))
                continue;
            int[] spellUses = nbt.getIntArray(talisman.getName());
            talismanUses[talisman.getId()] = new TalismanUses(spellUses[0], spellUses[1]);
        }
        instance.setAllUses(talismanUses);
    }

    @Override
    public void serialize(NBTTagCompound nbt, TalismanUsesData instance) {
        NBTTagCompound usesTag = getNbtTagCompound(instance);
        nbt.setTag(TAG_TALISMAN_USES, usesTag);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, TalismanUsesData instance) {
        if (!nbt.hasKey(TAG_TALISMAN_USES))
            return;
        NBTTagCompound usesTag = nbt.getCompoundTag(TAG_TALISMAN_USES);
        setDataFromNBT(instance, usesTag);
    }
}
