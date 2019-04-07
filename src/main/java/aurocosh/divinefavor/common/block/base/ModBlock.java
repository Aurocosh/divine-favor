package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class ModBlock extends Block {
    public ModBlock(String name, Material material) {
        super(material);
        ResourceLocation fullName = ResourceNamer.getFullName(name);
        setTranslationKey(fullName.toString());
        setRegistryName(fullName);
        ModRegistries.blocks.register(this);

        ModItemBlock itemBlock = getItemBlock();
        itemBlock.setRegistryName(fullName);
        ModRegistries.itemBlocks.register(itemBlock);
    }

    protected ModItemBlock getItemBlock() {
        return new ModItemBlock(this);
    }
}
