package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItem extends Item implements IModelHolder {
    private final int orderIndex;
    private final String texturePath;

    public ModItem(String name, String texturePath) {
        this(name, texturePath, 0);
    }

    public ModItem(String name, String texturePath, int orderIndex) {
        this.texturePath = texturePath;
        this.orderIndex = orderIndex;
        ResourceLocation fullName = ResourceNamer.getFullName(name);
        setTranslationKey(fullName.toString());
        setRegistryName(fullName);
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

    @SideOnly(Side.CLIENT)
    public String getNameKey() {
        return getTranslationKey() + ".name";
    }

    @SideOnly(Side.CLIENT)
    public String getDescriptionKey() {
        return getTranslationKey() + ".description";
    }
}
