package aurocosh.divinefavor.common.custom_data.world.capability;

import aurocosh.divinefavor.common.custom_data.world.data.altars.AltarsData;

// The default implementation of the capability. Holds all the logic.
public class DefaultWorldDataHandler implements IWorldDataHandler {
    private final AltarsData altarsData;

    public DefaultWorldDataHandler() {
        altarsData = new AltarsData();
    }

    @Override
    public AltarsData getAltarData() {
        return altarsData;
    }
}
