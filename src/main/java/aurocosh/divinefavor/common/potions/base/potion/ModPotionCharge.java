package aurocosh.divinefavor.common.potions.base.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.PotionEffect;

public abstract class ModPotionCharge extends ModPotion {
    public ModPotionCharge(String name, boolean beneficial, int potionColor) {
        super(name, beneficial, potionColor);
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect) {
        return false;
    }

    @Override
    public void renderCustomInvText(int x, int y, PotionEffect effect, Minecraft mc) {
        String s1 = I18n.format(getName());

        mc.fontRenderer.drawStringWithShadow(s1, (float) (x + 10 + 18), (float) (y + 6), 16777215);
        String s = "Uses left: " + effect.getAmplifier();
        mc.fontRenderer.drawStringWithShadow(s, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
