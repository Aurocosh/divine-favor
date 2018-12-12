package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModBlock extends Block {
    public ModBlock(String name, Material material) {
        super(material);
        ResourceLocation fullName = ResourceNamer.getFullName(name);
        setTranslationKey(fullName.toString());
        setRegistryName(fullName);
    }
}
