package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibMultiBlockNames;
import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import aurocosh.divinefavor.common.muliblock.parts.StateValidator;
import aurocosh.divinefavor.common.muliblock.serialization.StateValidatorSerializer;
import aurocosh.divinefavor.common.muliblock.serialization.Vector3iSerializer;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModMultiBlocks {
    private static Map<ResourceLocation, ModMultiBlock> multiBlockMap = new HashMap<>();
    private static Type listType = new TypeToken<ArrayList<MultiBlockPart>>(){}.getType();

    public static ModMultiBlock allfire_altar;
    public static ModMultiBlock timber_altar;

    public static void preInit() {
        //ArrayList<String> requirementPaths = UtilAssets.getAssetPaths(DivineFavor.container, "multi_blocks", ".json");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(StateValidator.class,new StateValidatorSerializer())
                .registerTypeAdapter(Vector3i.class,new Vector3iSerializer())
                .create();

        allfire_altar = createMultiBlock(LibMultiBlockNames.ALLFIRE_ALTAR,gson);
        timber_altar = createMultiBlock(LibMultiBlockNames.TIMBER_ALTER,gson);
    }

    private static ModMultiBlock createMultiBlock(String name, Gson gson){
        String jsonString = UtilAssets.loadTextFile(DivineFavor.container, LibResources.MULTIBLOCK_ASSETS + name + ".json");
        List<MultiBlockPart> parts = gson.fromJson(jsonString,listType);
        if(parts == null)
            parts = new ArrayList<>();

        List<Vector3i> localBounds = new ArrayList<>();
        for (MultiBlockPart part : parts) {
            CubeCoordinates coordinates = CubeCoordinates.getBoundingBox(part.positions);
            localBounds.add(coordinates.lowerCorner);
            localBounds.add(coordinates.upperCorner);
        }
        CubeCoordinates boundingBox = CubeCoordinates.getBoundingBox(localBounds);
        return register(new ModMultiBlock(name,parts,boundingBox));
    }

    public static ModMultiBlock register(ModMultiBlock spirit) {
        multiBlockMap.put(spirit.getRegistryName(), spirit);
//        SpiritRegestry.register(spirit);
        return spirit;
    }
}