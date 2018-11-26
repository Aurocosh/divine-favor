package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import com.google.gson.*;
import javafx.util.Pair;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class UtilAssets {
    public static void Test()
    {
        ArrayList<String> foundBooks = new ArrayList<>();
        String assetFolderPath = "requirements";
        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
        String id = mod.getModId();
        String folderPath = String.format("assets/%s/%s", LibMisc.MOD_ID, assetFolderPath);

        CraftingHelper.findFiles(mod, folderPath, (path) -> Files.exists(path),
                (path, file) -> {
                    if(file.toString().endsWith(".json")) {
                        String fileStr = file.toString().replaceAll("\\\\", "/");

                        String assetPath = fileStr.substring(fileStr.indexOf("/assets"));
                        foundBooks.add(assetPath);
                    }

                    return true;
                }, false, true);

        foundBooks.forEach((file) -> {

            InputStream stream = mod.getMod().getClass().getResourceAsStream(file);



            Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            SpellRequirement requirement = SpellRequirement.deserialize(jsonObject);
        });
    }
}
