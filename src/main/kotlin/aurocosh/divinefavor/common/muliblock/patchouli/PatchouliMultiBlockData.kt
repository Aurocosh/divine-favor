package aurocosh.divinefavor.common.muliblock.patchouli

import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration
import aurocosh.divinefavor.common.muliblock.MultiBlockPart
import aurocosh.divinefavor.common.muliblock.validators.StateValidator
import net.minecraft.util.math.BlockPos
import vazkii.patchouli.common.multiblock.StateMatcher

import java.util.ArrayList
import java.util.HashMap

class PatchouliMultiBlockData(configuration: MultiBlockConfiguration) {

    val pattern: Array<Array<String?>?>
    val matchingData: Array<Any>

    init {
        var nextSymbolId = 0
        val symbolMap = HashMap<StateValidator, Char>()
        val parts = configuration.parts
        for (i in parts.indices) {
            val part = parts[i]
            val symbol: Char
            if (part.positions.size != 1 || part.positions[0] != configuration.baseRelPosition) {
                symbol = possibleBlockMarkers[nextSymbolId++]
                symbolMap[part.validator] = symbol
            }
        }

        val validatorMap = HashMap<BlockPos, StateValidator>()
        for (part in configuration.parts)
            for (position in part.positions)
                validatorMap[position] = part.validator

        val size = configuration.boundingBox.sizeVector
        val layers = arrayOfNulls<Array<String?>?>(size.y)
        for (y in 0 until size.y) {
            val layer = arrayOfNulls<String>(size.z)
            for (z in 0 until size.z) {
                val builder = StringBuilder(size.x)
                for (x in 0 until size.x) {
                    val position = BlockPos(x, size.y - 1 - y, z)

                    var symbol: Char?
                    if (position == configuration.baseRelPosition)
                        symbol = '0'
                    else {
                        val validator = validatorMap[position]
                        symbol = symbolMap[validator]
                        if (symbol == null)
                            symbol = ' '
                    }
                    builder.append(symbol)
                }
                layer[z] = builder.toString()
            }
            layers[y] = layer
        }

        pattern = layers
        val matchers = ArrayList<Any>()

        for ((key, value) in symbolMap) {
            val matcher = key.getPatchouliMatcher()

            matchers.add(value)
            matchers.add(matcher)
        }

        matchers.add(' ')
        matchers.add(StateMatcher.ANY)

        val validator = validatorMap[configuration.baseRelPosition]
        matchers.add('0')
        matchers.add(validator?.getPatchouliMatcher() ?: StateMatcher.ANY)

        matchingData = matchers.toTypedArray()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (i in pattern.indices) {
            val layer = pattern[i]
            for (j in layer!!.indices) {
                val s = layer[j]
                builder.append(s)
                builder.append("|")
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    companion object {
        val possibleBlockMarkers = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    }
}
