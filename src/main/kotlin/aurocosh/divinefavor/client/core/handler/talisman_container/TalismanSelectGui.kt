package aurocosh.divinefavor.client.core.handler.talisman_container

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.core.handler.talisman.TalismanHUD
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.talisman.ITalismanToolContainer
import aurocosh.divinefavor.common.item.talisman_tools.ITalismanTool
import aurocosh.divinefavor.common.item.talisman_tools.TalismanAdapter
import aurocosh.divinefavor.common.lib.math.Vector2i
import aurocosh.divinefavor.common.util.UtilGui
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.settings.GameSettings
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11
import java.util.*

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
@SideOnly(Side.CLIENT)
class TalismanSelectGui : GuiScreen() {

    private var hand: EnumHand = EnumHand.MAIN_HAND
    private var state: Int = 0
    private var selectedIndex: Int = -1
    private var handler: ITalismanTool? = null

    private var allStacks: List<ItemStack>? = null
    private var slotPositions: List<Vector2i>? = null
    private val activePositionMap: MutableMap<Vector2i, Int>

    init {
        mc = Minecraft.getMinecraft()
        hand = EnumHand.MAIN_HAND
        state = 0
        selectedIndex = 0
        handler = null
        allStacks = null
        slotPositions = null
        activePositionMap = HashMap()
    }

    override fun drawScreen(mx: Int, my: Int, partialTicks: Float) {
        super.drawScreen(mx, my, partialTicks)

        val player = DivineFavor.proxy.clientPlayer
        hand = UtilPlayer.getHandWithItem(player) { it is ITalismanToolContainer} ?: return

        val stack = player.getHeldItem(hand)
        val toolContainer = stack.item as ITalismanToolContainer
        val talismanTool = toolContainer.getTalismanTool(stack)

        refreshStackData(talismanTool)
        renderSpellMassSelector(mx, my, talismanTool)

        if (selectedIndex > 0) {
            val talismanStack = talismanTool.getStackInSlot(selectedIndex)
            TalismanHUD.drawTalismanDescription(mc, width, height, player, talismanStack, true)
        }
    }

    private fun renderSpellMassSelector(mx: Int, my: Int, talismanTool: ITalismanTool) {
        val slotPositions = slotPositions ?: return
        val allStacks = allStacks ?: return

        val x = width / 2
        val y = height / 2

        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GlStateManager.pushMatrix()
        GlStateManager.translate(x.toFloat(), y.toFloat(), 0f)
        //        GlStateManager.color(1f, 1f, 1f);

        UtilGui.drawPolyline(slotPositions, 0.3f, 0f, 1f, 0.3f)

        val mousePosition = Vector2i(mx - x, my - y)
        val closestPoint = UtilGui.findClosestPoint(mousePosition, activePositionMap.keys, mousePosition)
        selectedIndex = activePositionMap[closestPoint] ?: -1
        val mouseLine = Arrays.asList(mousePosition, closestPoint)
        UtilGui.drawPolyline(mouseLine, 0.3f, 0f, 1f, 0.3f)

        GL11.glColor4f(1f, 1f, 1f, 1f)
        val currentSlotIndex = talismanTool.selectedSlotIndex
        val (x1, y1) = slotPositions[currentSlotIndex]

        mc.textureManager.bindTexture(marker)
        Gui.drawModalRectWithCustomSizedTexture(x1 - 8, y1 - 8, 0f, 0f, 16, 16, 16f, 16f)

        mc.textureManager.bindTexture(selector)
        Gui.drawModalRectWithCustomSizedTexture(closestPoint.x - 8, closestPoint.y - 8, 0f, 0f, 16, 16, 16f, 16f)

        for (index in activePositionMap.values) {
            val (x2, y2) = slotPositions[index]
            val stack = allStacks[index]
            mc.renderItem.renderItemIntoGUI(stack, x2 - 8, y2 - 8)
        }

        GlStateManager.popMatrix()
        GlStateManager.disableBlend()
    }

    private fun refreshStackData(talismanTool: ITalismanTool) {
        val k = 3 //scalar
        val a = 0.15 //a-value
        val aDec = -0.0008 // a-value change over time

        if (slotPositions == null || handler !== talismanTool)
            slotPositions = UtilGui.generateSpiral(talismanTool.getSlotCount(), 4, k, a, aDec, 9.0, 7.0)

        if (handler === talismanTool && state == talismanTool.getState())
            return

        handler = talismanTool
        state = talismanTool.getState()

        allStacks = talismanTool.getAllStacks()

        val activeIndexes = talismanTool.getSlotIndexes { !it.isEmpty }

        activePositionMap.clear()
        for (index in activeIndexes)
            activePositionMap[slotPositions!![index]] = index
    }

    override fun updateScreen() {
        super.updateScreen()
        if (!GameSettings.isKeyDown(KeyBindings.talismanSelect)) {

            mc.displayGuiScreen(null)
            if (selectedIndex != -1) {
                val player = DivineFavor.proxy.clientPlayer
                val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
                TalismanAdapter.selectSlot(playerSlot, selectedIndex)
                selectedIndex = -1
            }
        }

        //        ImmutableSet<KeyBinding> setNullable = ImmutableSet.of(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSneak, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump);
        //        for (KeyBinding k : setNullable)
        //            KeyBinding.setKeyBindState(k.getKeyCode(), GameSettings.isKeyDown(k));
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    companion object {
        val INSTANCE = TalismanSelectGui()

        private val marker = ResourceLocation(ConstResources.GUI_GRIMOIRE_MARKER)
        private val selector = ResourceLocation(ConstResources.GUI_GRIMOIRE_SELECTOR)
    }
}
