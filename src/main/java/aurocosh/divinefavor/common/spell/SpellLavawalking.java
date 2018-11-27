package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;

public class SpellLavawalking extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellLavawalking() {
        super(SpellType.LAVAWALKING);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.playerIn.getEntityWorld().isRemote)
        //    return true;

        PotionEffect potioneffect = new PotionEffect(ModPotionEffects.lavawalk, NORMAL);
        context.playerIn.addPotionEffect(potioneffect);

        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }
}
