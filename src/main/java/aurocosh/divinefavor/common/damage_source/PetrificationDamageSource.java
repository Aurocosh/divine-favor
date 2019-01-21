package aurocosh.divinefavor.common.damage_source;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class PetrificationDamageSource extends DamageSource {
    public PetrificationDamageSource() {
        super("petrification");
        setDamageBypassesArmor();
        setMagicDamage();
    }

    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        String s = "death.attack." + this.damageType;
        return new TextComponentTranslation(s, entityLivingBaseIn.getDisplayName());
    }
}