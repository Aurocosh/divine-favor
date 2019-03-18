package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavor;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class ItemSpellTalisman extends ItemTalisman {
    private static final double ENTITY_SEARCH_DISTANCE = 30;
    private final EnumSet<SpellOptions> options;

    // Talisman functions
    public ItemSpellTalisman(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super("talisman_" + name, "talismans/" + name, favor, favorCost);
        this.options = options;

        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_SPELL_TALISMANS);
    }

    public boolean cast(TalismanContext context) {
        if (!validate(context))
            return false;
        if (!claimCost(context))
            return false;
        if (context.world.isRemote)
            performActionClient(context);
        else
            performActionServer(context);
        return true;
    }

    private boolean claimCost(TalismanContext context) {
        if (favorCost == 0)
            return true;
        if (!isConsumeCharge(context))
            return true;
        FavorData favorData = PlayerData.get(context.player).getFavorData();
        if (!favorData.consumeFavor(favor.getId(), favorCost))
            return false;
        if (context.world.isRemote)
            return true;

        new MessageSyncFavor(favor, favorData).sendTo(context.player);
        return true;
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSpellTalisman) {
            castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemSpellTalisman))
            return new ActionResult<>(EnumActionResult.PASS, stack);
        boolean success = castRightClick(worldIn, playerIn, hand);
        return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.PASS, stack);
    }

    public void castItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!options.contains(SpellOptions.ItemUseCast))
            return;
        TalismanContext context = new TalismanContext(playerIn, worldIn, pos, hand, facing, null, CastType.UseCast, options);
        cast(context);
    }

    public boolean castRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!options.contains(SpellOptions.RightClickCast))
            return false;

        BlockPos pos;
        EnumFacing facing;
        EntityLivingBase target = null;
        if (options.contains(SpellOptions.OnRightCastRayTraceBlock)) {
            RayTraceResult traceResult = UtilEntity.getBlockPlayerLookingAt(player);
            if (traceResult == null)
                return false;
            pos = traceResult.getBlockPos();
            facing = traceResult.sideHit;
        }
        else {
            pos = player.getPosition();
            facing = EnumFacing.UP;
        }
        if (options.contains(SpellOptions.OnRightCastFindTargetEntity))
            target = UtilEntity.getEntityPlayerLookingAt(player, EntityLivingBase.class, ENTITY_SEARCH_DISTANCE, true);

        TalismanContext context = new TalismanContext(player, world, pos, hand, facing, target, CastType.RightCast, options);
        return cast(context);
    }

    protected boolean validate(TalismanContext context) {
        return true;
    }

    protected boolean isConsumeCharge(TalismanContext context) {
        return true;
    }

    protected void performActionServer(TalismanContext context) {
    }

    protected void performActionClient(TalismanContext context) {
    }
}