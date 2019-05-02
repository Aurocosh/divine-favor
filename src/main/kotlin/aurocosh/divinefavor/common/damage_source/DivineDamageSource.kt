package aurocosh.divinefavor.common.damage_source

import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation

class DivineDamageSource : DamageSource("divine") {
    init {
        setDamageBypassesArmor()
        setMagicDamage()
        setDamageIsAbsolute()
        setDamageBypassesArmor()
    }

    override fun getDeathMessage(entityLivingBaseIn: EntityLivingBase): ITextComponent {
        val s = "death.attack." + this.damageType
        return TextComponentTranslation(s, entityLivingBaseIn.displayName)
    }
}