package aurocosh.divinefavor.common.muliblock.common

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.patchouli.PatchouliMultiBlockData
import vazkii.patchouli.api.PatchouliAPI

object PatchouliMultiblockAdapter {
    fun register(multiBlock: ModMultiBlock) {
        for (configuration in multiBlock.configurations) {
            if (!configuration.primary)
                continue
            val multiBlockData = PatchouliMultiBlockData(configuration)

            print(multiBlockData.toString())
            println()

            val iMultiblock = PatchouliAPI.instance.makeMultiblock(multiBlockData.pattern, *multiBlockData.matchingData)
            val location = ResourceNamer.getFullName("patchouli", configuration.name)
            PatchouliAPI.instance.registerMultiblock(location, iMultiblock)
        }
    }
}
