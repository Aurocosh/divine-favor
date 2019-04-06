package aurocosh.divinefavor.common.integrations.patchouli.generation;

import aurocosh.divinefavor.common.integrations.patchouli.generation.pages.PatchouliPage;

import java.util.ArrayList;
import java.util.List;

public class PatchouliEntry {
    public String name;
    public String icon;
    public String category;
    public List<PatchouliPage> pages;

    public PatchouliEntry(String name, String icon, String category) {
        this.name = name;
        this.icon = icon;
        this.category = category;
        pages = new ArrayList<>();
    }
}
