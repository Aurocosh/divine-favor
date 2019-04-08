package aurocosh.divinefavor.common.block.common;

import aurocosh.divinefavor.common.block.BlockEnderPumpkin;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.block.bath_heater.BlockBathHeater;
import aurocosh.divinefavor.common.block.bath_heater.TileBathHeater;
import aurocosh.divinefavor.common.block.medium.BlockMedium;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.block.soulbound_lectern.BlockSoulboundLectern;
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static ModBlock bathHeater;
    public static ModBlock fastFurnace;

    public static ModBlock medium_gold;
    public static ModBlock medium_iron;
    public static ModBlock medium_lapis;
    public static ModBlock medium_log;
    public static ModBlock medium_obsidian;
    public static ModBlock medium_redstone;

    public static ModBlock soulboundLecternCoal;
    public static ModBlock soulboundLecternGold;
    public static ModBlock soulboundLecternIron;
    public static ModBlock soulboundLecternLog;
    public static ModBlock soulboundLecternObsidian;
    public static ModBlock soulboundLecternSnow;
    public static ModBlock soulboundLecternStone;
    public static ModBlock soulboundLecternWood;

    public static ModBlock enderPumpkin;

    public static void preInit() {
        bathHeater = new BlockBathHeater();

        medium_gold = new BlockMedium("gold", Material.IRON);
        medium_iron = new BlockMedium("iron", Material.IRON);
        medium_lapis = new BlockMedium("lapis", Material.ROCK);
        medium_log = new BlockMedium("log", Material.WOOD);
        medium_obsidian = new BlockMedium("obsidian", Material.ROCK);
        medium_redstone = new BlockMedium("redstone", Material.ROCK);

        soulboundLecternCoal = new BlockSoulboundLectern("coal", Material.ROCK);
        soulboundLecternGold = new BlockSoulboundLectern("gold", Material.IRON);
        soulboundLecternIron = new BlockSoulboundLectern("iron", Material.IRON);
        soulboundLecternLog = new BlockSoulboundLectern("log", Material.WOOD);
        soulboundLecternObsidian = new BlockSoulboundLectern("obsidian", Material.ROCK);
        soulboundLecternSnow = new BlockSoulboundLectern("snow", Material.SNOW);
        soulboundLecternStone = new BlockSoulboundLectern("stone", Material.ROCK);
        soulboundLecternWood = new BlockSoulboundLectern("wood", Material.WOOD);

        enderPumpkin = new BlockEnderPumpkin();

        initTileEntities();
    }

    public static void init() {
        //OreDictionary.registerOre("blockPsiDust", new ItemStack(psiDecorative, 1, 0));
    }

    private static void initTileEntities() {
        registerTile(TileBathHeater.class, ConstBlockNames.BATH_HEATER);
        registerTile(TileMedium.class, ConstBlockNames.MEDIUM);
        registerTile(TileSoulboundLectern.class, ConstBlockNames.SOULBOUND_LECTERN);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, ConstResources.PREFIX_MOD + key);
    }
}
