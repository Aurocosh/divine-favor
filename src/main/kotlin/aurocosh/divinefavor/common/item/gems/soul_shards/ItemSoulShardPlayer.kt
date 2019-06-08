package aurocosh.divinefavor.common.item.gems.soul_shards

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.hasKey
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ItemSoulShardPlayer(name: String, spirit: ModSpirit) : ItemSoulShard(name, spirit) {
    init {
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        if (stack.hasKey(NBT_ENTITY_NAME))
            tooltip.add(I18n.format("item.divinefavor:soul_shard.player", stack.compound.getString(NBT_ENTITY_NAME)))
    }
}
