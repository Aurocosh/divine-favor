package aurocosh.divinefavor.common.block.common;

import aurocosh.divinefavor.common.block.BlockDiviner;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.block.fast_furnace.BlockFastFurnaceMod;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.BlockMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.registry.RegistryMap;
import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    private static RegistryMap<ModBlock> blocks = new RegistryMap<>();
    private static RegistryMap<ItemBlock> itemBlocks = new RegistryMap<>();

    public static ModBlock blockFastFurnace;
    public static ModBlock immaterial_medium;
    public static ModBlock blockDiviner;

    public static Collection<ModBlock> getBlocks(){
        return blocks.getValues();
    }
    public static Collection<ItemBlock> getItemBlocks(){
        return itemBlocks.getValues();
    }

    public static void preInit() {
        blockFastFurnace = blocks.register(new BlockFastFurnaceMod());
        immaterial_medium = blocks.register(new BlockMedium());
        blockDiviner = blocks.register(new BlockDiviner());

        for (ModBlock block : blocks.getValues()) {
            ItemBlock itemBlock = new ItemBlock(block);
            itemBlock.setRegistryName(block.getRegistryName());
            itemBlocks.register(itemBlock);
        }

        initTileEntities();
    }

    public static void init() {
        //OreDictionary.registerOre("blockPsiDust", new ItemStack(psiDecorative, 1, 0));
    }

    private static void initTileEntities() {
        registerTile(TileFastFurnace.class, ConstBlockNames.FAST_FURNACE);
        registerTile(TileMedium.class, ConstBlockNames.IRON_MEDIUM);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, ConstResources.PREFIX_MOD + key);
    }
}
