package aurocosh.divinefavor;

import aurocosh.divinefavor.block.BlockFastFurnace;
import aurocosh.divinefavor.block.BlockIronMedium;
import aurocosh.divinefavor.utility.NamingHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    public  static  final ResourceLocation FAST_FURNACE = NamingHelper.GetRegistryName("fast_furnace");
    public  static  final ResourceLocation IRON_MEDIUM = NamingHelper.GetRegistryName("iron_medium");

    @GameRegistry.ObjectHolder("divinefavor:fast_furnace")
    public static BlockFastFurnace blockFastFurnace;


    @GameRegistry.ObjectHolder("divinefavor:iron_medium")
    public static BlockIronMedium blockIronMedium;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFastFurnace.initModel();
        blockIronMedium.initModel();
    }
}
