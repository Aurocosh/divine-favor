package aurocosh.divinefavor.common.muliblock.instance

import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.math.BlockPos

class MultiBlockSpiritInstance(multiBlock: ModMultiBlock, configuration: MultiBlockConfiguration, controllerPosition: BlockPos, val spirit: ModSpirit) : MultiBlockInstance(multiBlock, configuration, controllerPosition)
