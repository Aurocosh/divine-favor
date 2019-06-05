package aurocosh.divinefavor.common.custom_data.living.data.soul_theft

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound
import java.util.*

class SoulTheftDataSerializer : INbtSerializer<SoulTheftData> {

    override fun serialize(nbt: NBTTagCompound, instance: SoulTheftData) {
        val uuidList = instance.uuids
        val uuidStrings = ArrayList<String>(uuidList.size)
        for (uuid in uuidList)
            uuidStrings.add(uuid.toString())

        val uuidsSerialized = uuidStrings.joinToString(DELIMITER) // "foo and bar and baz"
        nbt.setString(TAG_SOUL_THIEF_UUIDS, uuidsSerialized)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: SoulTheftData) {
        val uuidList = nbt.getString(TAG_SOUL_THIEF_UUIDS)
                .split(DELIMITER)
                .asSequence()
                .filterNot(String::isEmpty)
                .map(UUID::fromString)
                .toList()
        instance.settUUIDS(uuidList)
    }

    companion object {
        private const val DELIMITER = ","
        private const val TAG_SOUL_THIEF_UUIDS = "SoulThiefUUIDS"
    }
}
