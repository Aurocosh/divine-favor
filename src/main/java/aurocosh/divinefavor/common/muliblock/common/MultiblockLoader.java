package aurocosh.divinefavor.common.muliblock.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.muliblock.MultiBlockData;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import aurocosh.divinefavor.common.muliblock.parts.StateValidator;
import aurocosh.divinefavor.common.muliblock.serialization.StateValidatorSerializer;
import aurocosh.divinefavor.common.muliblock.serialization.Vector3iByteSerializer;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

public final class MultiblockLoader {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(StateValidator.class, new StateValidatorSerializer())
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Vector3i.class, new Vector3iByteSerializer())
            .create();

    public static ModMultiBlock load(String type, String name, String... configs) {
        List<String> confPaths = new ArrayList<>();
        if (configs.length == 0)
            confPaths.add(name);

        List<MultiBlockConfiguration> configurations = new ArrayList<>(confPaths.size());
        for (String configurationName : confPaths) {
            String jsonString = UtilAssets.loadTextFile(DivineFavor.container, ConstResources.MULTIBLOCK_ASSETS + "/" + type + "/" + configurationName + ".json");

            MultiBlockData data = null;
            try {
                data = gson.fromJson(jsonString, MultiBlockData.class);
            }
            catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            if (data == null)
                data = new MultiBlockData();
            configurations.addAll(generateConfigurations(type + "_" + configurationName, data));
        }

        return new ModMultiBlock(type + "_" + name, configurations);
    }

    private static List<MultiBlockConfiguration> generateConfigurations(String name, MultiBlockData data) {
        List<Vector3i> localBounds = new ArrayList<>();
        for (MultiBlockPart part : data.parts) {
            CubeCoordinates coordinates = CubeCoordinates.getBoundingBox(part.positions);
            localBounds.add(coordinates.lowerCorner);
            localBounds.add(coordinates.upperCorner);
        }
        CubeCoordinates boundingBox = CubeCoordinates.getBoundingBox(localBounds);

        List<MultiBlockConfiguration> configurations = new ArrayList<>();
        MultiBlockConfiguration configuration = new MultiBlockConfiguration(name, true, data.basePosition, data.controllerPosition, data.parts, boundingBox);
        configurations.add(configuration);
        if (!data.symmetrical) {
            configuration = configuration.rotateClockwise();
            configurations.add(configuration);
            configuration = configuration.rotateClockwise();
            configurations.add(configuration);
            configuration = configuration.rotateClockwise();
            configurations.add(configuration);
        }
        return configurations;
    }
}