package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModItem extends Item implements IModelHolder {
    private final int orderIndex;
    private final String texturePath;

    public ModItem(String name, String texturePath) {
        this(name, texturePath, 0);
    }

    public ModItem(String name, String texturePath, int orderIndex) {
        this.texturePath = texturePath;
        this.orderIndex = orderIndex;
        setTranslationKey(name);
        setRegistryName(ResourceNamer.getFullName(name));
        ModRegistries.items.register(this);
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
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
