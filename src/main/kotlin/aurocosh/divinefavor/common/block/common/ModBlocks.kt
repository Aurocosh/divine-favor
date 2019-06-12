package aurocosh.divinefavor.common.block.common

import aurocosh.divinefavor.common.block.BlockEnderPumpkin
import aurocosh.divinefavor.common.block.BlockRedPulse
import aurocosh.divinefavor.common.block.base.ModBlock
import aurocosh.divinefavor.common.block.bath_heater.BlockBathHeater
import aurocosh.divinefavor.common.block.bath_heater.TileBathHeater
import aurocosh.divinefavor.common.block.ethereal.BlockEtherealFlash
import aurocosh.divinefavor.common.block.ethereal.BlockEtherealLight
import aurocosh.divinefavor.common.block.medium.BlockMedium
import aurocosh.divinefavor.common.block.medium.TileMedium
import aurocosh.divinefavor.common.block.rope.BlockLuminousRopeLight
import aurocosh.divinefavor.common.block.rope.BlockLuminousRopeSource
import aurocosh.divinefavor.common.block.rope.BlockRopeLight
import aurocosh.divinefavor.common.block.soulbound_lectern.BlockSoulboundLectern
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.ConstBlockNames
import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.registry.GameRegistry

object ModBlocks {
    lateinit var bathHeater: ModBlock

    lateinit var medium_gold: ModBlock
    lateinit var medium_iron: ModBlock
    lateinit var medium_lapis: ModBlock
    lateinit var medium_log: ModBlock
    lateinit var medium_obsidian: ModBlock
    lateinit var medium_redstone: ModBlock

    lateinit var soulboundLecternCoal: ModBlock
    lateinit var soulboundLecternGold: ModBlock
    lateinit var soulboundLecternIron: ModBlock
    lateinit var soulboundLecternLog: ModBlock
    lateinit var soulboundLecternObsidian: ModBlock
    lateinit var soulboundLecternSnow: ModBlock
    lateinit var soulboundLecternStone: ModBlock
    lateinit var soulboundLecternWood: ModBlock

    lateinit var standardRopeLight: ModBlock
    lateinit var luminousRopeLight: ModBlock
    lateinit var luminousRopeSource: ModBlock

    lateinit var enderPumpkin: ModBlock

    lateinit var ethereal_light: ModBlock
    lateinit var ethereal_flash: ModBlock
    lateinit var red_pulse: ModBlock
    lateinit var red_signal: ModBlock

    fun preInit() {
        bathHeater = BlockBathHeater()

        medium_gold = BlockMedium("gold", Material.IRON)
        medium_iron = BlockMedium("iron", Material.IRON)
        medium_lapis = BlockMedium("lapis", Material.ROCK)
        medium_log = BlockMedium("log", Material.WOOD)
        medium_obsidian = BlockMedium("obsidian", Material.ROCK)
        medium_redstone = BlockMedium("redstone", Material.ROCK)

        soulboundLecternCoal = BlockSoulboundLectern("coal", Material.ROCK)
        soulboundLecternGold = BlockSoulboundLectern("gold", Material.IRON)
        soulboundLecternIron = BlockSoulboundLectern("iron", Material.IRON)
        soulboundLecternLog = BlockSoulboundLectern("log", Material.WOOD)
        soulboundLecternObsidian = BlockSoulboundLectern("obsidian", Material.ROCK)
        soulboundLecternSnow = BlockSoulboundLectern("snow", Material.SNOW)
        soulboundLecternStone = BlockSoulboundLectern("stone", Material.ROCK)
        soulboundLecternWood = BlockSoulboundLectern("wood", Material.WOOD)

        standardRopeLight = BlockRopeLight("rope_light")
        luminousRopeLight = BlockLuminousRopeLight("luminous_rope_light")
        luminousRopeSource = BlockLuminousRopeSource("luminous_rope_source")

        enderPumpkin = BlockEnderPumpkin()

        ethereal_light = BlockEtherealLight("ethereal_light", Material.AIR, ConfigSpell.etherealLight.lightLevel)
        ethereal_flash = BlockEtherealFlash("ethereal_flash", ConfigSpell.etherealFlash.lightLevel, ConfigSpell.etherealFlash.despawnDelay)
        red_pulse = BlockRedPulse("red_pulse", ConfigSpell.redPulse.redLevel, ConfigSpell.redPulse.lightLevel, ConfigSpell.redPulse.despawnDelay)
        red_signal = BlockRedPulse("red_signal", ConfigSpell.redSignal.redLevel, ConfigSpell.redSignal.lightLevel, ConfigSpell.redSignal.despawnDelay)

        initTileEntities()
    }

    fun init() {
        //OreDictionary.registerOre("blockPsiDust", new ItemStack(psiDecorative, 1, 0));
    }

    private fun initTileEntities() {
        registerTile(TileBathHeater::class.java, ConstBlockNames.BATH_HEATER)
        registerTile(TileMedium::class.java, ConstBlockNames.MEDIUM)
        registerTile(TileSoulboundLectern::class.java, ConstBlockNames.SOULBOUND_LECTERN)
    }

    private fun registerTile(clazz: Class<out TileEntity>, key: String) {
        GameRegistry.registerTileEntity(clazz, ConstResources.PREFIX_MOD + key)
    }
}
