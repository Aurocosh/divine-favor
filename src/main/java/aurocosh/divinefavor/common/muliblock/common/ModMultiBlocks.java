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
import aurocosh.divinefavor.common.muliblock.patchouli.PatchouliMultiBlockData;
import aurocosh.divinefavor.common.muliblock.serialization.StateValidatorSerializer;
import aurocosh.divinefavor.common.muliblock.serialization.Vector3iByteSerializer;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.List;

public final class ModMultiBlocks {
    public static ModMultiBlock altar_arbow;
    public static ModMultiBlock altar_blizrabi;
    public static ModMultiBlock altar_endererer;
    public static ModMultiBlock altar_loon;
    public static ModMultiBlock altar_neblaze;
    public static ModMultiBlock altar_redwind;
    public static ModMultiBlock altar_romol;
    public static ModMultiBlock altar_squarefury;
    public static ModMultiBlock altar_timber;

    public static ModMultiBlock soulbound_lectern_wood;
    public static ModMultiBlock soulbound_lectern_stone;

    public static ModMultiBlock snowman;
    public static ModMultiBlock iron_golem;

    public static void preInit() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(StateValidator.class, new StateValidatorSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Vector3i.class, new Vector3iByteSerializer())
                .create();

        altar_arbow = createMultiBlock("altar_arbow", gson);
        altar_blizrabi = createMultiBlock("altar_blizrabi", gson);
        altar_endererer = createMultiBlock("altar_endererer", gson);
        altar_loon = createMultiBlock("altar_loon", gson);
        altar_neblaze = createMultiBlock("altar_neblaze", gson);
        altar_redwind = createMultiBlock("altar_redwind", gson);
        altar_romol = createMultiBlock("altar_romol", gson);
        altar_squarefury = createMultiBlock("altar_squarefury", gson);
        altar_timber = createMultiBlock("altar_timber", gson);

        soulbound_lectern_wood = createMultiBlock("soulbound_lectern_wood", gson);
        soulbound_lectern_stone = createMultiBlock("soulbound_lectern_stone", gson);

        snowman = createMultiBlock("snowman", gson);
        iron_golem = createMultiBlock("iron_golem", gson);
    }

    private static ModMultiBlock createMultiBlock(String name, Gson gson) {
        String fullName = "" + name;
        String jsonString = UtilAssets.loadTextFile(DivineFavor.container, ConstResources.MULTIBLOCK_ASSETS + fullName + ".json");

        MultiBlockData data = null;
        try {
            data = gson.fromJson(jsonString, MultiBlockData.class);
        }
        catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (data == null)
            data = new MultiBlockData(Vector3i.ZERO, new ArrayList<>());

        List<Vector3i> localBounds = new ArrayList<>();
        for (MultiBlockPart part : data.parts) {
            CubeCoordinates coordinates = CubeCoordinates.getBoundingBox(part.positions);
            localBounds.add(coordinates.lowerCorner);
            localBounds.add(coordinates.upperCorner);
        }
        CubeCoordinates boundingBox = CubeCoordinates.getBoundingBox(localBounds);
        return ModRegistries.multiBlocks.register(new ModMultiBlock(fullName, data.controllerPosition, data.parts, boundingBox));
    }

    public static void init() {
        for (ModMultiBlock multiBlock : ModRegistries.multiBlocks.getValues())
            registerPatchouli(multiBlock);
    }

    public static void registerPatchouli(ModMultiBlock multiBlock) {
        if (multiBlock.configurations.size() == 0)
            return;

        MultiBlockConfiguration configuration = multiBlock.configurations.get(0);
        PatchouliMultiBlockData multiBlockData = new PatchouliMultiBlockData(configuration);
        System.out.print(multiBlockData.toString());
        System.out.println();

        IMultiblock iMultiblock = PatchouliAPI.instance.makeMultiblock(multiBlockData.pattern, multiBlockData.matchingData);
        ResourceLocation name = multiBlock.getRegistryName();
        ResourceLocation location = new ResourceLocation(name.getNamespace(), name.getPath() + ".patchouli");
        PatchouliAPI.instance.registerMultiblock(location, iMultiblock);
    }
}