package aurocosh.divinefavor.common.muliblock.common

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration
import aurocosh.divinefavor.common.muliblock.serialization.BlockPosToByteSerializer
import aurocosh.divinefavor.common.muliblock.serialization.MultiBlockData
import aurocosh.divinefavor.common.muliblock.serialization.StateValidatorSerializer
import aurocosh.divinefavor.common.muliblock.validators.StateValidator
import aurocosh.divinefavor.common.util.UtilAssets
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import net.minecraft.util.math.BlockPos
import java.util.*

object MultiblockLoader {
    private val gson = GsonBuilder()
            .registerTypeAdapter(StateValidator::class.java, StateValidatorSerializer())
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(BlockPos::class.java, BlockPosToByteSerializer())
            .create()

    fun load(type: String, name: String, configs: Array<out String>): ModMultiBlock {
        val confPaths = ArrayList<String>()
        if (configs.isEmpty())
            confPaths.add(name)

        val configurations = ArrayList<MultiBlockConfiguration>(confPaths.size)
        for (configurationName in confPaths) {
            val jsonString = UtilAssets.loadTextFile(DivineFavor.container, ConstResources.MULTIBLOCK_ASSETS + type + "/" + configurationName + ".json")
            val data = deserializeJson(jsonString)
            configurations.addAll(generateConfigurations(type + "_" + configurationName, data))
        }

        return ModMultiBlock(type + "_" + name, configurations)
    }

    fun deserializeJson(jsonString: String): MultiBlockData {
        var data: MultiBlockData? = null
        try {
            data = MultiblockLoader.gson.fromJson(jsonString, MultiBlockData::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        if (data == null)
            data = MultiBlockData()
        return data
    }

    fun generateConfigurations(name: String, data: MultiBlockData): List<MultiBlockConfiguration> {
        val localBounds = ArrayList<BlockPos>()
        for (part in data.parts) {
            val coordinates = CuboidBoundingBox.getBoundingBox(part.positions)
            localBounds.add(coordinates.lowerCorner)
            localBounds.add(coordinates.upperCorner)
        }
        val boundingBox = CuboidBoundingBox.getBoundingBox(localBounds)

        val configurations = ArrayList<MultiBlockConfiguration>()
        var configuration = MultiBlockConfiguration(name, true, data.basePosition, data.controllerPosition, data.parts, boundingBox)
        configurations.add(configuration)
        if (!data.symmetrical) {
            configuration = configuration.rotateClockwise()
            configurations.add(configuration)
            configuration = configuration.rotateClockwise()
            configurations.add(configuration)
            configuration = configuration.rotateClockwise()
            configurations.add(configuration)
        }
        return configurations
    }
}