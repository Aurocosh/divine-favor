package aurocosh.divinefavor.common.item.gems.soul_shards

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.hasKey
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import java.util.*

open class ItemSoulShard(val name: String, vararg val spirits: ModSpirit) : ModItem("soul_shard_$name", "soul_shards/$name", ConstGemTabOrder.SOUL_SHARD) {

    init {
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        if (stack.hasKey(NBT_ENTITY_NAME))
            tooltip.add(I18n.format("item.divinefavor:soul_shard.entity", stack.compound.getString(NBT_ENTITY_NAME)))
    }

    companion object {
        private const val TAG_ENTITY_ID = "Id"
        const val NBT_ENTITY_NAME = "EntityName"

        fun hasOwner(stack: ItemStack): Boolean {
            return getEntityId(stack) != null
        }

        fun getEntityId(stack: ItemStack): UUID? {
            val compound = stack.compound
            return if (compound.hasKey(TAG_ENTITY_ID)) UUID.fromString(compound.getString(TAG_ENTITY_ID)) else null
        }

        fun setOwner(stack: ItemStack, livingBase: EntityLivingBase) {
            val compound = stack.compound
            compound.setString(TAG_ENTITY_ID, livingBase.uniqueID.toString())
            compound.setString(NBT_ENTITY_NAME, livingBase.name)
        }
    }
}
