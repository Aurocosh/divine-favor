package aurocosh.divinefavor.block;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.utility.NamingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class DivineFavorBlock extends Block {
    public DivineFavorBlock(ResourceLocation location, Material materialIn) {
        super(materialIn);
        setRegistryName(location);
        setUnlocalizedName(NamingHelper.GetUnlocalizedName(location.getResourcePath()));
        setCreativeTab(DivineFavor.tabDivineFavor);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
