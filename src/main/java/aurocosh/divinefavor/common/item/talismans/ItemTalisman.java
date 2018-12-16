package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTabTalismans;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.math.Vector3;
import aurocosh.divinefavor.common.network.message.client.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.ModSpell;
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

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class ItemTalisman extends ModItem {
    public final boolean castOnUse;
    public final boolean castOnRightClick;
    private final ModSpell spell;
    private final ModFavor favor;
    private final int favorPerUse;

    // Talisman functions
    public ItemTalisman(String name, ModSpell spell, ModFavor favor, int favorPerUse, boolean castOnUse, boolean castOnRightClick) {
        super("talisman_" + name, "talismans/");

        this.spell = spell;
        this.favor = favor;
        this.favorPerUse = favorPerUse;
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTabTalismans.INSTANCE);
    }

    public boolean cast(SpellContext context) {
        if (!claimCost(context))
            return false;
        spell.cast(context);
        return true;
    }

    private boolean claimCost(SpellContext context) {
        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler == null)
            return false;

        if (!favorHandler.consumeFavor(favor.id, favorPerUse))
            return false;
        if (context.world.isRemote)
            return true;

        int favorValue = favorHandler.getFavor(favor.id);
        new MessageSyncFavor(favor.id, favorValue).sendTo(context.player);
        return true;
    }

    public int getUseCount(EntityPlayer player) {
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        int favorCount = favorHandler.getFavor(favor.id);
        if (favorCount == 0)
            return 0;

        return favorCount / favorPerUse;
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return EnumActionResult.PASS;
        castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return EnumActionResult.SUCCESS;
    }

    public void castItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!castOnUse)
            return;
        SpellContext context = new SpellContext(playerIn, worldIn, pos, hand, facing, CastType.ITEM_USE_CAST);
        cast(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
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

        SpellContext context = new SpellContext(playerIn, worldIn, blockPos, hand, facing, CastType.RIGHT_CLICK);
        return cast(context);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}