package aurocosh.divinefavor.client.core.handler.talisman_container

import aurocosh.divinefavor.common.item.talisman_tools.IStackContainer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11
import java.util.*

class TalismanScrollHUD {
    private var state = 0
    private var handler: IStackContainer? = null

    private var selectedStack = ItemStack.EMPTY
    private var nextStacks: List<ItemStack>? = null
    private var previousStacks: MutableList<ItemStack>? = null

    @SideOnly(Side.CLIENT)
    fun draw(mc: Minecraft, width: Int, height: Int, stackContainer: IStackContainer) {
        refreshItemStacks(stackContainer)
        if (selectedStack.isEmpty)
            return
        val nextStacks = nextStacks ?: return
        val previousStacks = previousStacks ?: return

        val alpha = 255
        val x = width / 2
        val y = 10

        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GlStateManager.pushMatrix()
        GlStateManager.translate(x.toFloat(), y.toFloat(), 0f)
        GlStateManager.scale(alpha / 255f, 1f, 1f)
        GlStateManager.color(1f, 1f, 1f)
        mc.renderItem.renderItemIntoGUI(selectedStack, 0, 0)
        for (i in nextStacks.indices)
            mc.renderItem.renderItemIntoGUI(nextStacks[i], i * 18 + 21, -6)
        for (i in previousStacks.indices)
            mc.renderItem.renderItemIntoGUI(previousStacks[i], -i * 18 - 21, -6)
        GlStateManager.popMatrix()
        GlStateManager.disableBlend()
    }

    private fun refreshItemStacks(stackContainer: IStackContainer) {
        if (handler === stackContainer && state == stackContainer.getState())
            return

        handler = stackContainer
        state = stackContainer.getState()

        val next = stackContainer.getNextStacks()
        val previous = ArrayList(stackContainer.getPreviousStacks())
        for (stack in next)
            previous.remove(stack)

        selectedStack = stackContainer.getSelectedStack()
        previousStacks = previous
        nextStacks = next
    }
}
