package aurocosh.divinefavor.common.effect.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModPotionCharge extends ModPotion {
    public ModPotionCharge(String name, boolean beneficial, int potionColor) {
        super(name, beneficial, potionColor);
    }

    @SideOnly(Side.CLIENT)
    public static String getPotionDurationString(PotionEffect effect) {
        return "Uses left: " + effect.getAmplifier();
    }

    @Override

    public boolean shouldRenderInvText(PotionEffect effect) {
        return false;
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        super.renderInventoryEffect(x, y, effect, mc);

        String s1 = I18n.format(getName());

        mc.fontRenderer.drawStringWithShadow(s1, (float) (x + 10 + 18), (float) (y + 6), 16777215);
        String s = ModPotionCharge.getPotionDurationString(effect);
        mc.fontRenderer.drawStringWithShadow(s, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
