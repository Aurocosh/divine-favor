package aurocosh.divinefavor.common.muliblock.common;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.muliblock.patchouli.PatchouliMultiBlockData;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliMultiblockAdapter {
    public static void register(ModMultiBlock multiBlock) {
        for (MultiBlockConfiguration configuration : multiBlock.configurations) {
            if (!configuration.primary)
                continue;
            PatchouliMultiBlockData multiBlockData = new PatchouliMultiBlockData(configuration);
            System.out.print(multiBlockData.toString());
            System.out.println();

            IMultiblock iMultiblock = PatchouliAPI.instance.makeMultiblock(multiBlockData.pattern, multiBlockData.matchingData);
            ResourceLocation location = ResourceNamer.getFullName("patchouli", configuration.name);
            PatchouliAPI.instance.registerMultiblock(location, iMultiblock);
        }
    }
}
