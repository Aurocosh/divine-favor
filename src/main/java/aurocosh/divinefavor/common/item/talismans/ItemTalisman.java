package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.math.Vector3;
import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemTalisman extends ModItem {
    public ItemTalisman(String texturePath, String[] variants) {
        super(ConstItemNames.TALISMAN, texturePath, variants);
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return EnumActionResult.PASS;

        Talisman talisman = ModTalismans.getMetaContainer().getByMeta(stack.getMetadata());
        if(talisman == null)
            return EnumActionResult.PASS;

        if(!talisman.castOnUse)
            return EnumActionResult.PASS;

        SpellContext context = new SpellContext(playerIn, worldIn, pos, hand, facing, CastType.ITEM_USE_CAST);
        talisman.cast(context);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return new ActionResult<>(EnumActionResult.PASS, stack);

        if(worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.PASS, stack);


        Talisman talisman = ModTalismans.getMetaContainer().getByMeta(stack.getMetadata());
        if(talisman == null)
            return new ActionResult<>(EnumActionResult.PASS, stack);

        if(!talisman.castOnRightClick)
            return new ActionResult<>(EnumActionResult.PASS, stack);

        Vector3 posVec = new Vector3(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
        Vector3 lookVec = new Vector3(playerIn.getLookVec());
        RayTraceResult pos = UtilWorld.raycast(worldIn, posVec, lookVec, 20);
        BlockPos blockPos = null;
        EnumFacing facing = EnumFacing.UP;
        if (pos != null) {
            blockPos = pos.getBlockPos();
            facing = pos.sideHit;
        }

        SpellContext context = new SpellContext(playerIn, worldIn, blockPos, hand, facing, CastType.RIGHT_CLICK);
        boolean success = talisman.cast(context);
        return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.PASS, stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}