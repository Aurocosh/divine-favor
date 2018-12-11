package aurocosh.divinefavor.common.item.base;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IVariantHolder {
	String[] getVariants();

	@SideOnly(Side.CLIENT)
	ItemMeshDefinition getCustomMeshDefinition();
}
