package aurocosh.divinefavor.utility;

import aurocosh.divinefavor.DivineFavor;
import net.minecraft.util.ResourceLocation;

public class NamingHelper {
    public static ResourceLocation GetRegistryName(String name) {
        return new ResourceLocation(DivineFavor.MODID,name);
    }

    public static String GetUnlocalizedName(String name){
        return DivineFavor.MODID + "." +  name;
    }
}


