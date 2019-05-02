package aurocosh.divinefavor.common.custom_data.world;

import aurocosh.divinefavor.common.custom_data.world.capability.IWorldDataHandler;
import net.minecraft.world.World;

import static aurocosh.divinefavor.common.custom_data.world.capability.WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE;

// The default implementation of the capability. Holds all the logic.
public class WorldData {
    public static IWorldDataHandler get(World world){
        return world.getCapability(CAPABILITY_WORLD_DATA_DIVINE, null);
    }
}
