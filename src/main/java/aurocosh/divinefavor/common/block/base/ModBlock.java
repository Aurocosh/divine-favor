package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.registry.base.CommonRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class ModBlock extends Block {
    public ModBlock(String name, Material material) {
        super(material);
        setRegistryName(ResourceNamer.getFullName(name));
//
//        ItemBlock itemBlock = new ItemBlock(this);
//        itemBlock.setRegistryName(ResourceNamer.getFullName(name));
//        CommonRegistry.register(itemBlock);
    }
}
