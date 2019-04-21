package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import aurocosh.divinefavor.common.lib.interfaces.IOrdered;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class ModBlock extends Block implements IOrdered {
    private final int orderIndex;

    public ModBlock(String name, Material material, int orderIndex) {
        super(material);
        this.orderIndex = orderIndex;
        ResourceLocation fullName = ResourceNamer.getFullName(name);
        setTranslationKey(fullName.toString());
        setRegistryName(fullName);
        ModRegistries.blocks.register(this);

        ModItemBlock itemBlock = getItemBlock();
        if (itemBlock != null) {
            itemBlock.setRegistryName(fullName);
            ModRegistries.itemBlocks.register(itemBlock);
        }
    }

    @Override
    public int getOrderIndex() {
        return orderIndex;
    }

    protected ModItemBlock getItemBlock() {
        return new ModItemBlock(this, orderIndex);
    }
}
