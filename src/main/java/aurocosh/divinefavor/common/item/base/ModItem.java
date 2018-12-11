package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ModItem extends Item implements IVariantHolder {
    private final String[] variants;

    public ModItem(String name, String... variants) {
        if (variants.length > 1)
            setHasSubtypes(true);
        if (variants.length == 0)
            variants = new String[]{""};
        this.variants = variants;

        setTranslationKey(name);
        setRegistryName(ResourceNamer.getFullName(name));
    }

    @Override
    public String getTranslationKey(ItemStack par1ItemStack) {
        ResourceLocation fullName = getRegistryName();
        if (fullName == null)
            return "";

        int damage = par1ItemStack.getItemDamage();
        damage = damage < variants.length ? damage : 0;

        return "item." + fullName.toString() + "_" + variants[damage];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (!this.isInCreativeTab(tab))
            return;
        for (int i = 0; i < variants.length; ++i)
            items.add(new ItemStack(this, 1, i));
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
