package vazkii.arl.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.arl.interf.IModBlock;
import vazkii.arl.item.ItemModBlock;
import vazkii.arl.util.ProxyRegistry;

public abstract class BlockModFalling extends BlockFalling implements IModBlock {

	private final String[] variants;
	private final String bareName;

	public BlockModFalling(String name, String... variants) {
		if(variants.length == 0)
			variants = new String[] { name };

		bareName = name;
		this.variants = variants;

		if(registerInConstruction())
			setUnlocalizedName(name);
	}

	@Override
	public Block setUnlocalizedName(String name) {
		super.setUnlocalizedName(name);
		setRegistryName(getPrefix() + name);
		ProxyRegistry.register(this);
		ProxyRegistry.register(createItemBlock(new ResourceLocation(getPrefix() + name)));
		return this;
	}
	
	public ItemBlock createItemBlock(ResourceLocation res) {
		return new ItemModBlock(this, res);
	}
	
	public boolean registerInConstruction() {
		return true;
	}
	
	@Override
	public String getBareName() {
		return bareName;
	}

	@Override
	public String[] getVariants() {
		return variants;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemMeshDefinition getCustomMeshDefinition() {
		return null;
	}

	@Override
	public EnumRarity getBlockRarity(ItemStack stack) {
		return EnumRarity.COMMON;
	}

	@Override
	public IProperty[] getIgnoredProperties() {
		return new IProperty[0];
	}

	@Override
	public IProperty getVariantProp() {
		return null;
	}

	@Override
	public Class getVariantEnum() {
		return null;
	}
	
}
