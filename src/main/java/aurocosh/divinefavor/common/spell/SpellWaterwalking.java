package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;

public class SpellWaterwalking extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellWaterwalking() {
        super(SpellType.WATERWALKING, Side.SERVER);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect potioneffect = new PotionEffect(ModPotionEffects.waterwalk, NORMAL);
        context.playerIn.addPotionEffect(potioneffect);

        return true;
    }
}
