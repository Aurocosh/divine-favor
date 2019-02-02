package aurocosh.divinefavor.common.block.common;

import aurocosh.divinefavor.common.block.BlockDiviner;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.block.fast_furnace.BlockFastFurnaceMod;
import aurocosh.divinefavor.common.block.fast_furnace.TileFastFurnace;
import aurocosh.divinefavor.common.block.medium.BlockMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.block.soulbound_lectern.BlockSoulboundLectern;
import aurocosh.divinefavor.common.block.soulbound_lectern.tile_entities.TileSoulboundLecternStone;
import aurocosh.divinefavor.common.block.soulbound_lectern.tile_entities.TileSoulboundLecternWood;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static ModBlock diviner;
    public static ModBlock fastFurnace;
    public static ModBlock immaterialMedium;
    public static ModBlock soulboundLecternWood;
    public static ModBlock soulboundLecternStone;

    public static void preInit() {
        diviner = new BlockDiviner();
        fastFurnace = new BlockFastFurnaceMod();
        immaterialMedium = new BlockMedium();
        soulboundLecternWood = new BlockSoulboundLectern("wood", Material.WOOD, TileSoulboundLecternWood.class);
        soulboundLecternStone = new BlockSoulboundLectern("stone", Material.ROCK, TileSoulboundLecternStone.class);

        initTileEntities();
    }

    public static void init() {
        //OreDictionary.registerOre("blockPsiDust", new ItemStack(psiDecorative, 1, 0));
    }

    private static void initTileEntities() {
        registerTile(TileFastFurnace.class, ConstBlockNames.FAST_FURNACE);
        registerTile(TileMedium.class, ConstBlockNames.IRON_MEDIUM);
        registerTile(TileSoulboundLecternWood.class, "soulbound_lectern_wood");
        registerTile(TileSoulboundLecternStone.class, "soulbound_lectern_stone");
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, ConstResources.PREFIX_MOD + key);
    }
}
