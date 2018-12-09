package aurocosh.divinefavor.common.muliblock.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.constants.ConstMultiBlockNames;
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
import aurocosh.divinefavor.common.registry.CommonRegistry;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModMultiBlocks {
    public static ModMultiBlock allfire_altar;
    public static ModMultiBlock timber_altar;
    private static Map<ResourceLocation, ModMultiBlock> multiBlockMap = new HashMap<>();

    public static final HashMap<ResourceLocation, IMultiblock> MULTIBLOCKS = new HashMap<>();
    public static IMultiblock test;

    public static void preInit() {
        //ArrayList<String> requirementPaths = UtilAssets.getAssetPaths(DivineFavor.container, "multi_blocks", ".json");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(StateValidator.class, new StateValidatorSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Vector3i.class, new Vector3iByteSerializer())
                .create();

        allfire_altar = createMultiBlock(ConstMultiBlockNames.ALLFIRE_ALTAR, gson);
        timber_altar = createMultiBlock(ConstMultiBlockNames.TIMBER_ALTER, gson);

        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.instance;


        test = api.makeMultiblock(new String[][] {
                        { "   ", " 0 ", "   " },
                        { "SSS", "SFS", "SSS" }},
                '0', Blocks.CAULDRON,
                'F', Blocks.FIRE,
                'S', api.predicateMatcher(Blocks.STONEBRICK, (state) -> state.isOpaqueCube() && state.getMaterial() == Material.ROCK),
                ' ', api.anyMatcher())
                .setSymmetrical(true);
        api.registerMultiblock(new ResourceLocation(ConstMisc.MOD_ID, "test"),test);
    }

    public static IMultiblock registerMultiblock(ResourceLocation location, IMultiblock multiblock) {
        MULTIBLOCKS.put(location, multiblock);
        return multiblock.setResourceLocation(location);
    }

    private static ModMultiBlock createMultiBlock(String name, Gson gson) {
        String jsonString = UtilAssets.loadTextFile(DivineFavor.container, ConstResources.MULTIBLOCK_ASSETS + name + ".json");

        MultiBlockData data = null;
        try {
            data = gson.fromJson(jsonString, MultiBlockData.class);
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        if(data == null)
            data = new MultiBlockData(Vector3i.ZERO,new ArrayList<>());

        List<Vector3i> localBounds = new ArrayList<>();
        for (MultiBlockPart part : data.parts) {
            CubeCoordinates coordinates = CubeCoordinates.getBoundingBox(part.positions);
            localBounds.add(coordinates.lowerCorner);
            localBounds.add(coordinates.upperCorner);
        }
        CubeCoordinates boundingBox = CubeCoordinates.getBoundingBox(localBounds);
        return register(new ModMultiBlock(name, data.controllerPosition, data.parts, boundingBox));
    }

    public static void init() {
        for (ModMultiBlock multiBlock : multiBlockMap.values())
            registerPatchouli(multiBlock);
    }

    public static ModMultiBlock register(ModMultiBlock multiBlock) {
        multiBlockMap.put(multiBlock.getRegistryName(), multiBlock);
        CommonRegistry.register(multiBlock);
        return multiBlock;
    }

    public static void registerPatchouli(ModMultiBlock multiBlock) {
        if(multiBlock.configurations.size() == 0)
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