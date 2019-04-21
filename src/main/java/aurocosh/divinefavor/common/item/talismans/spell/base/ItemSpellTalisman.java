package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class ItemSpellTalisman extends ItemTalisman {
    private static final double ENTITY_SEARCH_DISTANCE = 30;
    private final EnumSet<SpellOptions> options;

    // Talisman functions
    public ItemSpellTalisman(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super("talisman_" + name, "spell_talismans/" + name, spirit, favorCost);
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

    protected boolean claimCost(TalismanContext context) {
        if (favorCost == 0)
            return true;
        if (!isConsumeCharge(context))
            return true;
        SpiritData spiritData = PlayerData.get(context.player).getSpiritData();
        if (!spiritData.consumeFavor(spirit.getId(), favorCost))
            return false;
        if (context.world.isRemote)
            return true;

        new MessageSyncFavor(spirit, spiritData).sendTo(context.player);
        return true;
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSpellTalisman) {
            boolean casted = castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            return casted ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
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

    public boolean castItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!options.contains(SpellOptions.ItemUseCast))
            return false;
        TalismanContext context = new TalismanContext(playerIn, worldIn, pos, new Vec3d(hitX, hitY, hitZ), hand, facing, null, CastType.UseCast, options);
        cast(context);
        return true;
    }

    public boolean castRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!options.contains(SpellOptions.RightClickCast))
            return false;

        BlockPos pos;
        Vec3d posVec;
        EnumFacing facing;
        EntityLivingBase target = null;
        if (options.contains(SpellOptions.OnRightCastRayTraceBlock)) {
            RayTraceResult traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance);
            if (traceResult == null)
                return false;
            pos = traceResult.getBlockPos();
            posVec = traceResult.hitVec;
            facing = traceResult.sideHit;
        }
        else {
            pos = player.getPosition();
            posVec = new Vec3d(pos);
            facing = EnumFacing.UP;
        }
        if (options.contains(SpellOptions.OnRightCastFindTargetEntity))
            target = UtilEntity.getEntityPlayerLookingAt(player, EntityLivingBase.class, ENTITY_SEARCH_DISTANCE, true);

        TalismanContext context = new TalismanContext(player, world, pos, posVec, hand, facing, target, CastType.RightCast, options);
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