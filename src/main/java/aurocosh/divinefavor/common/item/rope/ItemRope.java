package aurocosh.divinefavor.common.item.rope;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class ItemRope<T extends EntityRopeNodeBase> extends ModItem {
    private final Class<T> clazz;
    private final double ropeDistance;
    private final double ropeDistanceSq;

    public ItemRope(String name, String texturePath, Class<T> clazz, double ropeDistance) {
        super(name, texturePath);
        this.clazz = clazz;
        this.ropeDistance = ropeDistance;
        ropeDistanceSq = ropeDistance * ropeDistance;
        this.setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
            return EnumActionResult.SUCCESS;

        List<T> chargeNodes = UtilList.select(world.loadedEntityList, clazz);
        T connectedRopeNode = UtilList.findFirst(chargeNodes, node -> node.getNextNodeByUUID() == player);

        if (connectedRopeNode != null && connectedRopeNode.getDistanceSq(pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ) > ropeDistanceSq) {
            player.sendStatusMessage(new TextComponentTranslation("chat.rope_explosive.too_far"), true);
            return EnumActionResult.FAIL;
        }

        if (connectedRopeNode != null)
            connectedRopeNode.extendRope(player, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ);
        else {
            try {
                spawnNewNode(player, world, pos, hitX, hitY, hitZ, stack);
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return EnumActionResult.SUCCESS;
    }

    private void spawnNewNode(EntityPlayer player, World world, BlockPos pos, float hitX, float hitY, float hitZ, ItemStack stack) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<T> constructor = clazz.getConstructor(World.class);
        T ropeNode = constructor.newInstance(world);
        ropeNode.setLocationAndAngles(pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ, 0, 0);
        ropeNode.setNextNode(player);
        world.spawnEntity(ropeNode);
        world.playSound(null, ropeNode.posX, ropeNode.posY, ropeNode.posZ, SoundEvents.BLOCK_METAL_STEP, SoundCategory.PLAYERS, 1, 1.5F);
        stack.shrink(1);
    }

//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        tooltip.addAll(ItemTooltipHandler.splitTooltip(I18n.format("tooltip.caving_rope", KeyBindings.ropeKey.getDisplayName(), StringUtils.ticksToElapsedTime(1200 * 20)), 0));
//    }
}