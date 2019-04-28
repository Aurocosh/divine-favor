package aurocosh.divinefavor.common.item.soul_shards

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilNbt
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import java.util.*

open class ItemSoulShard(name: String, val spirit: ModSpirit) : ModItem("soul_shard_$name", "soul_shards/$name", ConstGemTabOrder.SOUL_SHARD) {

    init {
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    override fun addInformation(stack: ItemStack?, world: World?, tooltip: MutableList<String>?, flag: ITooltipFlag?) {
        super.addInformation(stack, world, tooltip, flag)
        val compound = UtilNbt.getNbt(stack!!)
        if (compound.hasKey(NBT_ENTITY_NAME))
            tooltip!!.add(I18n.format("item.divinefavor:soul_shard.entity", compound.getString(NBT_ENTITY_NAME)))
    }

    companion object {
        public val TAG_ENTITY_ID = "Id"
        public val NBT_ENTITY_NAME = "EntityName"

        fun hasOwner(stack: ItemStack): Boolean {
            return getEntityId(stack) != null
        }

        fun getEntityId(stack: ItemStack): UUID? {
            val compound = UtilNbt.getNbt(stack)
            return if (compound.hasKey(TAG_ENTITY_ID)) UUID.fromString(compound.getString(TAG_ENTITY_ID)) else null
        }

        fun setOwner(stack: ItemStack, livingBase: EntityLivingBase) {
            val compound = UtilNbt.getNbt(stack)
            compound.setString(TAG_ENTITY_ID, livingBase.uniqueID.toString())
            compound.setString(NBT_ENTITY_NAME, livingBase.name)
        }
    }
}
