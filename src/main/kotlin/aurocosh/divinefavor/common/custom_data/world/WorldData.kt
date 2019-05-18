package aurocosh.divinefavor.common.custom_data.world

import aurocosh.divinefavor.common.custom_data.world.capability.IWorldDataHandler
import aurocosh.divinefavor.common.custom_data.world.capability.WorldDataDataHandler
import net.minecraft.world.World

import aurocosh.divinefavor.common.custom_data.world.capability.WorldDataDataHandler.Companion.CAPABILITY_WORLD_DATA_DIVINE

// The default implementation of the capability. Holds all the logic.
object WorldData {
    operator fun get(world: World): IWorldDataHandler? {
        return world.getCapability(CAPABILITY_WORLD_DATA_DIVINE!!, null)
    }
}
