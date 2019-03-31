package aurocosh.divinefavor.common.config.talismans.spell.conversion;

import net.minecraftforge.common.config.Config;

public class MaterialConversion {
    @Config.Name("Material")
    public String material = "snow";
    @Config.Name("Result")
    public String result = "minecraft:lava";

    public MaterialConversion(String material, String result) {
        this.material = material;
        this.result = result;
    }
}
