package aurocosh.divinefavor.common.potions.base.potion

import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.potion.PotionEffect
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ModPotionToggle(name: String, potionColor: Int) : ModPotion(name, potionColor) {

    override fun shouldRenderInvText(effect: PotionEffect?): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun renderCustomInvText(x: Int, y: Int, effect: PotionEffect, mc: Minecraft) {
        val potionName = I18n.format(name)
        mc.fontRenderer.drawStringWithShadow(potionName, (x + 10 + 18).toFloat(), (y + 6).toFloat(), 16777215)
        mc.fontRenderer.drawStringWithShadow("Permanent", (x + 10 + 18).toFloat(), (y + 6 + 10).toFloat(), 8355711)
    }
}
