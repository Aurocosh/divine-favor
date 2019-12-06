package aurocosh.divinefavor.client.core.handler.base

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

interface IHudDescriptionRenderer {
    fun drawDescription(mc: Minecraft, width: Int, height: Int, player: EntityPlayer, stack: ItemStack, drawName: Boolean)
}