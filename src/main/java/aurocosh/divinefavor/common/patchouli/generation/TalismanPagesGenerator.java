package aurocosh.divinefavor.common.patchouli.generation;

import aurocosh.divinefavor.common.lib.RuntimeTypeAdapterFactory;
import aurocosh.divinefavor.common.patchouli.generation.pages.PatchouliPage;
import aurocosh.divinefavor.common.patchouli.generation.pages.SpellTalismanPatchouliPage;
import aurocosh.divinefavor.common.patchouli.generation.pages.SpotlightPatchouliPage;
import aurocosh.divinefavor.common.patchouli.generation.pages.TextPatchouliPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TalismanPagesGenerator {
    public static void generate(){
        RuntimeTypeAdapterFactory<PatchouliPage> pageFactory = RuntimeTypeAdapterFactory
                .of(PatchouliPage.class, "type")
                .registerSubtype(TextPatchouliPage.class,"text")
                .registerSubtype(SpotlightPatchouliPage.class,"spotlight")
                .registerSubtype(SpellTalismanPatchouliPage.class,"spell_talisman");

        final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(pageFactory).create();

        PatchouliEntry entry = new PatchouliEntry("Test Entry","minecraft:writable_book","test_category");
        entry.pages.add(new SpotlightPatchouliPage("divinefavor:talisman_summon_creeper","Summons creeper minion"));
        entry.pages.add(new SpellTalismanPatchouliPage("divinefavor:talisman_summon_creeper"));
        entry.pages.add(new TextPatchouliPage("This is a test entry, but it should show up!"));

        String result = gson.toJson(entry,PatchouliEntry.class);
        try {
            File file = new File("D:/BufWriter/test.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(result);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
