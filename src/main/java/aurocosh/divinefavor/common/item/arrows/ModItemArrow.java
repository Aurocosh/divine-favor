package aurocosh.divinefavor.common.item.arrows;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.base.IModelHolder;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModItemArrow extends ItemArrow implements IModelHolder {
    private final String texturePath;
    public ModItemArrow(String name, String texturePath) {
        this.texturePath = texturePath;
        setTranslationKey(name);
        setRegistryName(ResourceNamer.getFullName(name));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (!this.isInCreativeTab(tab))
            return;
        items.add(new ItemStack(this, 1));
    }

    @Override
    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }
}
