package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider

fun <T> ICapabilityProvider.capNull(capability: Capability<T>): T? {
    return this.getCapability(capability, null)
}

fun <T> ICapabilityProvider.capNull(capability: Capability<T>, facing: EnumFacing): T? {
    return this.getCapability(capability, facing)
}

fun <T> ICapabilityProvider.cap(capability: Capability<T>): T {
    return this.getCapability(capability, null)!!
}

fun <T> ICapabilityProvider.cap(capability: Capability<T>, facing: EnumFacing): T {
    return this.getCapability(capability, facing)!!
}
