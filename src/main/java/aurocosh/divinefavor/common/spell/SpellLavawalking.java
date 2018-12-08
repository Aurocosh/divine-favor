package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;

public class SpellLavawalking extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellLavawalking() {
        super(SpellType.LAVAWALKING, true);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.player.getEntityWorld().isRemote)
        //    return true;

        PotionEffect potioneffect = new PotionEffect(ModPotionEffects.lavawalk, NORMAL);
        context.player.addPotionEffect(potioneffect);

        return true;
    }
}
