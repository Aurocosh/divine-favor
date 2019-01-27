package aurocosh.divinefavor.common.util;

import net.minecraft.world.World;

import java.util.EnumSet;

public class UtilSide {
    public static boolean shouldExecute(World world, EnumSet<EnumSide> sides) {
        if(world.isRemote)
            return sides.contains(EnumSide.Client);
        else
            return sides.contains(EnumSide.Server);
    }
}
