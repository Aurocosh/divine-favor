package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModItem extends Item implements IVariantHolder {
    private final String[] variants;

    public ModItem(String name, String... variants) {
        if (variants.length > 1)
            setHasSubtypes(true);
        if (variants.length == 0)
            variants = new String[]{name};
        this.variants = variants;

        setTranslationKey(name);
        setRegistryName(ResourceNamer.getFullName(name));
    }

    @Override
    public String getTranslationKey(ItemStack par1ItemStack) {
        ResourceLocation fullName = getRegistryName();
        if(fullName == null)
            return "";

        int damage = par1ItemStack.getItemDamage();
        boolean isVariant = damage < variants.length;

        String name = isVariant ? variants[damage] : fullName.getPath();
        return "item." + fullName.getNamespace() + ":" + name;
    }

    @Override
    public String[] getVariants() {
        return variants;
    }

    @Override
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }
}
