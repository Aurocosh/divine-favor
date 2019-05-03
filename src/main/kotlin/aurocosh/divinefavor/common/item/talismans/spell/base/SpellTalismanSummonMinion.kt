package aurocosh.divinefavor.common.item.talismans.spell.base

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLiving
import java.util.*

class SpellTalismanSummonMinion<T>(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, clazz: Class<out T>) : SpellTalismanSummonEntity<T>(name, spirit, favorCost, options, clazz) where T : EntityLiving, T : IMinion {

    override fun postProcessEntity(entityLiving: T, context: TalismanContext) {
        entityLiving.minionData.owner = context.player
    }
}
