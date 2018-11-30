package aurocosh.divinefavor.common.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class UtilCommon {
    public static Side getCodeSide() {
        return FMLCommonHandler.instance().getSide();
    }
}
