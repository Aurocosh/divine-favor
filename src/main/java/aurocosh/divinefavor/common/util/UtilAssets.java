package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.lib.RuntimeTypeAdapterFactory;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.requirements.base.SpellRequirementFree;
import aurocosh.divinefavor.common.requirements.base.SpellRequirementNotFree;
import aurocosh.divinefavor.common.requirements.requirement.Cost;
import aurocosh.divinefavor.common.requirements.requirement.CostFavor;
import aurocosh.divinefavor.common.requirements.requirement.CostMultipleOptions;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class UtilAssets {
    public void Test(){
        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
        ArrayList<String> requirementPaths = getAssetPaths(mod,"requirements",".json");

        RuntimeTypeAdapterFactory<SpellRequirement> spellFactory = RuntimeTypeAdapterFactory
                .of(SpellRequirement.class, "type")
                .registerSubtype(SpellRequirementFree.class)
                .registerSubtype(SpellRequirementNotFree.class);
        RuntimeTypeAdapterFactory<Cost> costFactory = RuntimeTypeAdapterFactory
                .of(Cost.class, "type")
                .registerSubtype(CostFavor.class)
                .registerSubtype(CostMultipleOptions.class);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(spellFactory).registerTypeAdapterFactory(costFactory).create();

        ArrayList<SpellRequirement> requirements = loadObjectsFromJsonAssets(SpellRequirement.class,mod,requirementPaths,gson);

    }

    public ArrayList<String> getAssetPaths(ModContainer mod, String assetFolderPath, String fileFormat)
    {
        ArrayList<String> foundPaths = new ArrayList<>();
        String id = mod.getModId();
        String base = String.format("assets/%s/%s", id, assetFolderPath);

        CraftingHelper.findFiles(mod, base, (path) -> Files.exists(path),
            (path, file) -> {
                if(file.toString().endsWith(fileFormat)) {
                    String fileStr = file.toString().replaceAll("\\\\", "/");
                    String assetPath = fileStr.substring(fileStr.indexOf("/assets"));
                    foundPaths.add(assetPath);
                }
                return true;
            },false, true);
        return foundPaths;
    }

    private <T> ArrayList<T> loadObjectsFromJsonAssets(Class<T> clazz, ModContainer mod, ArrayList<String> assetPaths, Gson gson){
        ArrayList<T> objectList = new ArrayList<>();
        Class modClass = mod.getMod().getClass();
        for (String path:assetPaths) {
            InputStream stream = modClass.getResourceAsStream(path);
            Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            T object = gson.fromJson(reader,clazz);
            objectList.add(object);
        }
        return objectList;
    }

    public static void Test2()
    {
        ArrayList<Cost> costs = new ArrayList<>();
        costs.add(new CostFavor(0,1,1));
        costs.add(new CostFavor(0,2,1));
        CostMultipleOptions costMultipleOptions = new CostMultipleOptions(0,costs);

        SpellRequirementNotFree spellRequirementNotFree = new SpellRequirementNotFree("reqtest",costMultipleOptions);

        RuntimeTypeAdapterFactory<SpellRequirement> spellFactory = RuntimeTypeAdapterFactory
                .of(SpellRequirement.class, "type")
                .registerSubtype(SpellRequirementFree.class)
                .registerSubtype(SpellRequirementNotFree.class);
        RuntimeTypeAdapterFactory<Cost> costFactory = RuntimeTypeAdapterFactory
                .of(Cost.class, "type")
                .registerSubtype(CostFavor.class)
                .registerSubtype(CostMultipleOptions.class);

        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(spellFactory).registerTypeAdapterFactory(costFactory).create();

        String result = gson.toJson(spellRequirementNotFree,SpellRequirement.class);
        try {
            File file = new File("D:/Test/test.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(result);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        SpellRequirement deserial = gson.fromJson(result,SpellRequirement.class);
    }
}
