package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibMultiBlockNames;
import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModMultiBlocks {
    private static Map<ResourceLocation, ModMultiBlock> multiBlockMap = new HashMap<>();

    public static ModMultiBlock allfire_altar;
    public static ModMultiBlock timber_altar;

    public static void preInit() {
        //ArrayList<String> requirementPaths = UtilAssets.getAssetPaths(DivineFavor.container, "multi_blocks", ".json");


        Gson gson = new Gson();
        allfire_altar = createMultiBlock(LibMultiBlockNames.ALLFIRE_ALTAR,gson);
        timber_altar = createMultiBlock(LibMultiBlockNames.TIMBER_ALTER,gson);
    }

    private static ModMultiBlock createMultiBlock(String name, Gson gson){
        String jsonString = UtilAssets.loadTextFile(DivineFavor.container, LibResources.MULTIBLOCK_ASSETS + name + ".json");
        MultiblockTemplateData data = gson.fromJson(jsonString, MultiblockTemplateData.class);
        List<MultiBlockPart> parts;
        if(data != null)
            parts = data.getBlockDataList();
        else
            parts = new ArrayList<>();
        CubeCoordinates coordinates = getBoundingBox(parts);
        return register(new ModMultiBlock(name,parts,coordinates));
    }

    private static CubeCoordinates getBoundingBox(List<MultiBlockPart> parts) {
        List<Vector3i> positions = new ArrayList<>(parts.size());
        for (MultiBlockPart part : parts)
            positions.add(part.position);
        return CubeCoordinates.getBoundingBox(positions);
    }

    public static ModMultiBlock register(ModMultiBlock spirit) {
        multiBlockMap.put(spirit.getRegistryName(), spirit);
//        SpiritRegestry.register(spirit);
        return spirit;
    }
}