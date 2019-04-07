package aurocosh.divinefavor.common.item.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemBlock extends ItemBlock {
    public ModItemBlock(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public String getNameKey() {
        return getTranslationKey() + ".name";
    }

    @SideOnly(Side.CLIENT)
    public String getDescriptionKey() {
        return getTranslationKey() + ".description";
    }
}
