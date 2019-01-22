package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTabTalismans;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.TalismanUsesData;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.math.Vector3;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.util.UtilWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemSpellTalisman extends ItemTalisman {
    protected final boolean castOnUse;
    protected final boolean castOnRightClick;

    // Talisman functions
    public ItemSpellTalisman(String name, int startingSpellUses, boolean castOnUse, boolean castOnRightClick) {
        super("talisman_" + name, "talismans/" + name, startingSpellUses);
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTabTalismans.INSTANCE);
    }

    public boolean cast(TalismanContext context) {
        if (!claimCost(context))
            return false;
        if (context.world.isRemote)
            performActionClient(context);
        else
            performActionServer(context);
        return true;
    }

    private boolean claimCost(TalismanContext context) {
        if(!isConsumeCharge(context))
            return true;

        TalismanUsesData usesData = PlayerData.get(context.player).getTalismanUsesData();
        if (!usesData.consumeUse(id))
            return false;
        if (context.world.isRemote)
            return true;

        new MessageSyncSpellUses(id, usesData).sendTo(context.player);
        return true;
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemSpellTalisman))
            return EnumActionResult.PASS;
        if (getSpellUses(playerIn, worldIn, pos, stack))
            return EnumActionResult.SUCCESS;
        castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return EnumActionResult.SUCCESS;
    }

    public void castItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!castOnUse)
            return;
        TalismanContext context = new TalismanContext(playerIn, worldIn, pos, hand, facing, CastType.ITEM_USE_CAST);
        cast(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemSpellTalisman))
            return new ActionResult<>(EnumActionResult.PASS, stack);
        boolean success = castRightClick(worldIn, playerIn, hand);
        return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.PASS, stack);
    }


    public boolean castRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (!castOnRightClick)
            return false;

        Vector3 posVec = new Vector3(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
        Vector3 lookVec = new Vector3(playerIn.getLookVec());
        RayTraceResult pos = UtilWorld.raycast(worldIn, posVec, lookVec, 20);
        BlockPos blockPos = null;
        EnumFacing facing = EnumFacing.UP;
        if (pos != null) {
            blockPos = pos.getBlockPos();
            facing = pos.sideHit;
        }

        TalismanContext context = new TalismanContext(playerIn, worldIn, blockPos, hand, facing, CastType.RIGHT_CLICK);
        return cast(context);
    }

    protected boolean isConsumeCharge(TalismanContext context) {
        return true;
    }

    protected void performActionServer(TalismanContext context) {
    }

    protected void performActionClient(TalismanContext context) {
    }
}