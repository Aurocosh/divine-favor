package aurocosh.divinefavor.common.potions.base.potion;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.player_data.grudge.IGrudgeHandler;
import aurocosh.divinefavor.common.player_data.spell_count.ISpellUsesHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;
import static aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler.CAPABILITY_SPELL_USES;

public abstract class ModPotionToggleLimited extends ModPotionToggle {
    protected static ItemTalisman talisman;

    public ModPotionToggleLimited(String name, boolean beneficial, int potionColor) {
        super(name, beneficial, potionColor);
    }

    public ItemTalisman getTalisman() {
        return talisman;
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

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ISpellUsesHandler usesHandler = player.getCapability(CAPABILITY_SPELL_USES, null);
        assert usesHandler != null;
        String s = "Uses left: " + usesHandler.getSpellUses(talisman.getId());
        mc.fontRenderer.drawStringWithShadow(s, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
