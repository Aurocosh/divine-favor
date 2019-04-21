package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.lib.interfaces.IOrdered;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemBlock extends ItemBlock implements IOrdered, IDescriptionProvider {
    private final int orderIndex;

    public ModItemBlock(Block block, int orderIndex) {
        super(block);
        this.orderIndex = orderIndex;
    }

    @SideOnly(Side.CLIENT)
    public String getNameKey() {
        return getTranslationKey() + ".name";
    }

    @SideOnly(Side.CLIENT)
    public String getDescriptionKey() {
        return getTranslationKey() + ".description";
    }

    @Override
    public int getOrderIndex() {
        return orderIndex;
    }
}
