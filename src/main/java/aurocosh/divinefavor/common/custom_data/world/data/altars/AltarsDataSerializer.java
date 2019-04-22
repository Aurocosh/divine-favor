package aurocosh.divinefavor.common.custom_data.world.data.altars;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.*;

// Handles the actual read/write of the nbt.
public class AltarsDataSerializer implements INbtSerializer<AltarsData> {
    private static String TAG_ALTAR_POSITIONS = "AltarPositions";

    @Override
    public void serialize(NBTTagCompound nbt, AltarsData instance) {
        NBTTagCompound altarNbt = new NBTTagCompound();

        Map<Integer, Set<BlockPos>> positions = instance.getAllPositions();
        for (Map.Entry<Integer, Set<BlockPos>> entry : positions.entrySet()) {
            int spiritId = entry.getKey();
            List<BlockPos> altarLocations = new ArrayList<>(entry.getValue());
            ModSpirit spirit = ModMappers.spirits.get(spiritId);
            int[] posArray = UtilBlockPos.serialize(altarLocations);
            String spiritName = spirit.getRegistryName().toString();
            altarNbt.setIntArray(spiritName, posArray);
        }
        nbt.setTag(TAG_ALTAR_POSITIONS, altarNbt);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, AltarsData instance) {
        if(!nbt.hasKey(TAG_ALTAR_POSITIONS))
            return;
        NBTTagCompound altarNbt = nbt.getCompoundTag(TAG_ALTAR_POSITIONS);
        List<ModSpirit> spirits = ModMappers.spirits.getValues();
        Map<Integer, Set<BlockPos>> positions = new HashMap<>();
        for (ModSpirit spirit : spirits) {
            String spiritName = spirit.getRegistryName().toString();
            if(!altarNbt.hasKey(spiritName))
                return;
            int[] posArray = altarNbt.getIntArray(spiritName);
            List<BlockPos> altarLocations = UtilBlockPos.deserialize(posArray);
            positions.put(spirit.getId(), new HashSet<>(altarLocations));
        }
        instance.setAllPositions(positions);
    }
}
