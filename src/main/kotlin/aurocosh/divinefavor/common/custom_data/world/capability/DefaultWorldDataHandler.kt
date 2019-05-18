package aurocosh.divinefavor.common.custom_data.world.capability

import aurocosh.divinefavor.common.custom_data.world.data.altars.AltarsData

// The default implementation of the capability. Holds all the logic.
class DefaultWorldDataHandler : IWorldDataHandler {
    override val altarsData: AltarsData = AltarsData()
}
