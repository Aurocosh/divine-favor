package aurocosh.divinefavor.common.muliblock.common

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.regName
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File
import java.io.FileReader

object ModCustomMultiBlocks {
    private data class MultiBlockFile(val type: String, val name: String, val file: File)

    private val multiblockTypes = listOf("altar", "soulbound_lectern")
    private val customMultiBlocks = HashMap<ResourceLocation, ModMultiBlock>()

    fun preInit(event: FMLPreInitializationEvent) {
        val configurationDirectory = event.modConfigurationDirectory
        val multiblocks = configurationDirectory.resolve("${DivineFavor.MOD_ID}/custom_multiblocks")
        if (!multiblocks.exists()) {
            DivineFavor.logger.info("No custom multiblocks detected")
            return
        }

        multiblockTypes.S
                .map(multiblocks::resolve)
                .filter(File::exists)
                .filter(File::isDirectory)
                .flatMap(this::getFileList)
                .forEach(this::loadMultiblock)
    }

    private fun getFileList(folder: File): Sequence<MultiBlockFile> {
        val type = folder.nameWithoutExtension
        val jsonFiles = folder.listFiles { file: File -> file.extension == "json" } ?: emptyArray<File>()
        return jsonFiles.S.map { MultiBlockFile(type, it.nameWithoutExtension, it) }
    }

    private fun loadMultiblock(multiBlockFile: MultiBlockFile) {
        val (type, name, file) = multiBlockFile
        val fileReader = FileReader(file)
        val jsonString = fileReader.readText()
        val data = MultiblockLoader.deserializeJson(jsonString)
        if (!data.isValid()) {
            DivineFavor.logger.error("Failed to load multiblock from file $file.")
            return
        }

        val fullName = type + "_" + name
        val configurations = MultiblockLoader.generateConfigurations(fullName, data)
        val multiBlock = ModMultiBlock(fullName, configurations)
        register(multiBlock)
    }

    private fun register(multiBlock: ModMultiBlock) {
        val name = multiBlock.regName
        if (customMultiBlocks.containsKey(name))
            DivineFavor.logger.error("Custom multiblock duplicate detected for multiblock $name. Previous custom multiblock discarded")
        customMultiBlocks[name] = multiBlock
    }

    fun getMultiblock(type: String, name: String, vararg configs: String): ModMultiBlock {
        val fullName = ResourceNamer.getFullName("multi_block", type + "_" + name)
        return customMultiBlocks[fullName] ?: return MultiblockLoader.load(type, name, configs)
    }
}