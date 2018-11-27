package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.base.TalismanData;
import aurocosh.divinefavor.common.lib.RuntimeTypeAdapterFactory;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.requirements.requirement.Cost;
import aurocosh.divinefavor.common.requirements.requirement.CostFavor;
import aurocosh.divinefavor.common.requirements.requirement.CostMultipleOptions;
import aurocosh.divinefavor.common.spell.base.SpellType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

public class UtilAssets {
    public void Test(){
        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
        ArrayList<String> requirementPaths = getAssetPaths(mod,"requirements",".json");

        RuntimeTypeAdapterFactory<Cost> costFactory = RuntimeTypeAdapterFactory
                .of(Cost.class, "type")
                .registerSubtype(CostFavor.class)
                .registerSubtype(CostMultipleOptions.class);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(costFactory).create();

        ArrayList<SpellRequirement> requirements = loadObjectsFromJsonAssets(SpellRequirement.class,mod,requirementPaths,gson);

    }

    public static ArrayList<String> getAssetPaths(ModContainer mod, String assetFolderPath, String fileFormat)
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

    public static <T> ArrayList<T> loadObjectsFromJsonAssets(Class<T> clazz, ModContainer mod, ArrayList<String> assetPaths, Gson gson){
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
//        ArrayList<Cost> costs = new ArrayList<>();
//        costs.add(new CostFavor(0,1,1));
//        costs.add(new CostFavor(0,2,1));
//        CostMultipleOptions costMultipleOptions = new CostMultipleOptions(0,costs);
//
//        SpellRequirement spellRequirementNotFree = new SpellRequirement("reqtest",costMultipleOptions);
//
//        TalismanData talismanData = new TalismanData("ignition", SpellType.IGNITION,spellRequirementNotFree,true,false);
//
//        RuntimeTypeAdapterFactory<Cost> costFactory = RuntimeTypeAdapterFactory
//                .of(Cost.class, "type")
//                .registerSubtype(CostFavor.class)
//                .registerSubtype(CostMultipleOptions.class);
//
//        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(costFactory).create();
//
//        String result = gson.toJson(talismanData,TalismanData.class);
//        try {
//            File file = new File("D:/Test/test.txt");
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(result);
//            fileWriter.flush();
//            fileWriter.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        TalismanData deserial = gson.fromJson(result,TalismanData.class);
    }
}
