package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.block.BlockFastFurnace;
import aurocosh.divinefavor.common.block.BlockIronMedium;
import net.minecraft.tileentity.TileEntity;
import vazkii.arl.block.BlockMod;

public class ModBlocks {
    public static BlockMod blockFastFurnace;
    public static BlockMod blockIronMedium;

    public static void preInit() {
        blockFastFurnace = new BlockFastFurnace();
        blockIronMedium = new BlockIronMedium();

        initTileEntities();
    }

    public static void init() {
        // Psi oredict mappings
        //OreDictionary.registerOre("blockPsiDust", new ItemStack(psiDecorative, 1, 0));
        //OreDictionary.registerOre("blockPsiMetal", new ItemStack(psiDecorative, 1, 1));
        //OreDictionary.registerOre("blockPsiGem", new ItemStack(psiDecorative, 1, 2));
    }

    private static void initTileEntities() {
        //registerTile(TileCADAssembler.class, LibBlockNames.CAD_ASSEMBLER);
        //registerTile(TileProgrammer.class, LibBlockNames.PROGRAMMER);
        //registerTile(TileConjured.class, LibBlockNames.CONJURED);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        //GameRegistry.registerTileEntity(clazz, LibResources.PREFIX_MOD + key);
    }


    //@SideOnly(Side.CLIENT)
    //public static void initModels() {
    //    blockFastFurnace.initModel();
    //    blockIronMedium.initModel();
    //}
}
