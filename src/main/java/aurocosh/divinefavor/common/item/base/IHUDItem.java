package aurocosh.divinefavor.common.item.base;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHUDItem {
    @SideOnly(Side.CLIENT)
    public void drawHUD(ScaledResolution res, float partTicks, ItemStack stack);
}