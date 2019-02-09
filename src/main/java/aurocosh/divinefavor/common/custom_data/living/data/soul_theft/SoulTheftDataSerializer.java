package aurocosh.divinefavor.common.custom_data.living.data.soul_theft;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SoulTheftDataSerializer implements INbtSerializer<SoulTheftData> {
    private static final String DELIMITER = ",";
    private static final String TAG_SOUL_THIEF_UUIDS = "SoulThiefUUIDS";

    @Override
    public void serialize(NBTTagCompound nbt, SoulTheftData instance) {
        List<UUID> uuidList = instance.getUUIDS();
        List<String> uuidStrings = new ArrayList<>(uuidList.size());
        for (UUID uuid : uuidList)
            uuidStrings.add(uuid.toString());

        String uuidsSerialized = String.join(DELIMITER, uuidStrings); // "foo and bar and baz"
        nbt.setString(TAG_SOUL_THIEF_UUIDS, uuidsSerialized);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, SoulTheftData instance) {
        String uuidsSerialized = nbt.getString(TAG_SOUL_THIEF_UUIDS);
        List<String> uuidStrings = Arrays.asList(uuidsSerialized.split(DELIMITER));
        List<UUID> uuidList = new ArrayList<>(uuidStrings.size());
        for (String uuidString : uuidStrings)
            if (!uuidString.isEmpty())
                uuidList.add(UUID.fromString(uuidString));
        instance.settUUIDS(uuidList);
    }
}
