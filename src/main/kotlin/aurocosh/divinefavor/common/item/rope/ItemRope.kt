package aurocosh.divinefavor.common.item.rope

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.multicatch
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import java.lang.reflect.InvocationTargetException

abstract class ItemRope<T : EntityRopeNodeBase>(name: String, texturePath: String, private val clazz: Class<T>, ropeDistance: Double) : ModItem(name, texturePath, ConstMainTabOrder.ROPES) {
    private val ropeDistanceSq: Double = ropeDistance * ropeDistance

    init {
        this.creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        if (world.isRemote)
            return EnumActionResult.SUCCESS

        val connectedRopeNode = world.loadedEntityList.S.
                filterIsInstance(clazz).
                firstOrNull { node -> node.nextNodeByUUID === player }

        if (connectedRopeNode != null && connectedRopeNode.getDistanceSq((pos.x + hitX).toDouble(), (pos.y + hitY).toDouble(), (pos.z + hitZ).toDouble()) > ropeDistanceSq) {
            val translationKey = "chat.$translationKey.too_far"
            player.sendStatusMessage(TextComponentTranslation(translationKey), true)
            return EnumActionResult.FAIL
        }

        if (connectedRopeNode != null)
            connectedRopeNode.extendRope(player, (pos.x + hitX).toDouble(), (pos.y + hitY).toDouble(), (pos.z + hitZ).toDouble())
        else {
            try {
                spawnNewNode(player, world, pos, hitX, hitY, hitZ, stack)
            } catch (e: Throwable) {
                e.multicatch(InstantiationException::class, IllegalAccessException::class, InvocationTargetException::class, NoSuchMethodException::class) {
                    e.printStackTrace()
                }
            }
        }

        return EnumActionResult.SUCCESS
    }

    @Throws(IllegalAccessException::class, InvocationTargetException::class, InstantiationException::class, NoSuchMethodException::class)
    private fun spawnNewNode(player: EntityPlayer, world: World, pos: BlockPos, hitX: Float, hitY: Float, hitZ: Float, stack: ItemStack) {
        val constructor = clazz.getConstructor(World::class.java)
        val ropeNode = constructor.newInstance(world)
        ropeNode.setLocationAndAngles((pos.x + hitX).toDouble(), (pos.y + hitY).toDouble(), (pos.z + hitZ).toDouble(), 0f, 0f)
        ropeNode.nextNode = player
        world.spawnEntity(ropeNode)
        world.playSound(null, ropeNode.posX, ropeNode.posY, ropeNode.posZ, SoundEvents.BLOCK_METAL_STEP, SoundCategory.PLAYERS, 1f, 1.5f)
        stack.shrink(1)
    }

    //
    //    @SideOnly(Side.CLIENT)
    //    @Override
    //    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    //        tooltip.addAll(ItemTooltipHandler.splitTooltip(I18n.format("tooltip.caving_rope", KeyBindings.ropeKey.getDisplayName(), StringUtils.ticksToElapsedTime(1200 * 20)), 0));
    //    }
}