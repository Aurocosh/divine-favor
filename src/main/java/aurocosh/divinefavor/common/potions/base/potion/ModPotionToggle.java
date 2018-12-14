package aurocosh.divinefavor.common.potions.base.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModPotionToggle extends ModPotion {
    public ModPotionToggle(String name, boolean beneficial, int potionColor) {
        super(name, beneficial, potionColor);
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderCustomInvText(int x, int y, PotionEffect effect, Minecraft mc) {
        String potionName = I18n.format(getName());
        mc.fontRenderer.drawStringWithShadow(potionName, (float) (x + 10 + 18), (float) (y + 6), 16777215);
        mc.fontRenderer.drawStringWithShadow("Permament", (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
