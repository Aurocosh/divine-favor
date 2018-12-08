package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.block.BlockDiviner;
import aurocosh.divinefavor.common.block.fast_furnace.BlockFastFurnaceMod;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.BlockMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.LibBlockNames;
import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.registry.base.CommonRegistry;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    private static Map<ResourceLocation,ModBlock> blockMap = new HashMap<>();
    private static Map<ResourceLocation, ItemBlock> itemBlockMap = new HashMap<>();

    public static ModBlock blockFastFurnace;
    public static ModBlock blockIronMedium;
    public static ModBlock blockDiviner;

    public static Collection<ModBlock> getBlocks(){
        return blockMap.values();
    }
    public static Collection<ItemBlock> getItemBlocks(){
        return itemBlockMap.values();
    }

    public static void preInit() {
        blockFastFurnace = register(new BlockFastFurnaceMod());
        blockIronMedium = register(new BlockMedium());
        blockDiviner = register(new BlockDiviner());

        initTileEntities();
    }

    public static void init() {
        //OreDictionary.registerOre("blockPsiDust", new ItemStack(psiDecorative, 1, 0));
    }

    private static void initTileEntities() {
        registerTile(TileFastFurnace.class, LibBlockNames.FAST_FURNACE);
        registerTile(TileMedium.class, LibBlockNames.IRON_MEDIUM);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, LibResources.PREFIX_MOD + key);
    }

    //@SideOnly(Side.CLIENT)
    //public static void initModels() {
    //    blockFastFurnace.initModel();
    //    blockIronMedium.initModel();

    //}

    private static ModBlock register(ModBlock block) {
        ResourceLocation name = block.getRegistryName();
        blockMap.put(name,block);
        CommonRegistry.register(block);

        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(name);
        itemBlockMap.put(name,itemBlock);
        CommonRegistry.register(itemBlock);
        return block;
    }
}
