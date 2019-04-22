package aurocosh.divinefavor.common.config.entries.talismans.spell.conversion;

import net.minecraftforge.common.config.Config;

public class BlockConversion {
    @Config.Name("Block")
    public String block = "minecraft:snow";
    @Config.Name("Result")
    public String result = "minecraft:lava";

    public BlockConversion(String block, String result) {
        this.block = block;
        this.result = result;
    }
}
