package aurocosh.divinefavor.common.custom_data.player.data.spell.vengeful_blade

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class VengfulBladeDataSerializer : INbtSerializer<VengefulBladeData> {

    override fun serialize(nbt: NBTTagCompound, instance: VengefulBladeData) {
        instance.lastDamage = nbt.getFloat(TAG_LAST_DAMAGE)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: VengefulBladeData) {
        nbt.setFloat(TAG_LAST_DAMAGE, instance.lastDamage)
    }

    companion object {
        private const val TAG_LAST_DAMAGE = "LastDamageReceived"
    }
}
