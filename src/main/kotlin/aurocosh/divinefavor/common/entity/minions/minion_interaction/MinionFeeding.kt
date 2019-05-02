package aurocosh.divinefavor.common.entity.minions.minion_interaction

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemFood
import net.minecraft.util.EnumHand
import java.util.*

class MinionFeeding<T>(private val healMultiplier: Float, vararg foodList: Item) : MinionInteraction<T>() where T : IMinion, T : EntityLiving {
    private val foodList: MutableList<ItemFood>

    init {
        this.foodList = ArrayList(foodList.size)
        for (item in foodList)
            if (item is ItemFood)
                this.foodList.add(item)
    }

    override fun process(minion: T, player: EntityPlayer, hand: EnumHand): Boolean {
        val stack = player.getHeldItem(hand)
        if (stack.isEmpty)
            return false
        val item = stack.item as? ItemFood ?: return false
        if (!foodList.contains(item))
            return false

        if (!player.capabilities.isCreativeMode)
            stack.shrink(1)
        minion.heal(item.getHealAmount(stack) * healMultiplier)
        return true
    }
}
