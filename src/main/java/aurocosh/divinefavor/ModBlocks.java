package aurocosh.divinefavor;

import aurocosh.divinefavor.block.BlockFastFurnace;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    @GameRegistry.ObjectHolder("divinefavor:fast_furnace")
    public static BlockFastFurnace blockFastFurnace;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFastFurnace.initModel();
    }
}
