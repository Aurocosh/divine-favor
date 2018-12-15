package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellObsidianRoad extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellObsidianRoad() {
        super("obsidian_road");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        PotionEffect potioneffect = new ModEffect(ModPotions.obsidian_road, NORMAL);
        context.player.addPotionEffect(potioneffect);
    }
}
