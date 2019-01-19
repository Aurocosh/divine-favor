package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTabTalismans;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.lib.math.Vector3;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.player_data.talisman_uses.ITalismanUsesHandler;
import aurocosh.divinefavor.common.util.UtilWorld;
import net.minecraft.block.state.IBlockState;
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

import static aurocosh.divinefavor.common.player_data.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

public class ItemTalisman extends ModItem implements IIndexedEntry {
    private static int nextId = 0;

    protected final int id;
    protected final String name;
    protected final int startingSpellUses;
    protected final boolean castOnUse;
    protected final boolean castOnRightClick;

    // Talisman functions
    public ItemTalisman(String name, int startingSpellUses, boolean castOnUse, boolean castOnRightClick) {
        super("talisman_" + name, "talismans/" + name);

        id = nextId++;
        this.name = name;
        this.startingSpellUses = startingSpellUses;
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTabTalismans.INSTANCE);
    }

    public String getName() {
        return name;
    }

    public int getStartingSpellUses() {
        return startingSpellUses;
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

        ITalismanUsesHandler usesHandler = context.player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;

        if (!usesHandler.consumeUse(id))
            return false;
        if (context.world.isRemote)
            return true;

        int usesLeft = usesHandler.getUses(id);
        new MessageSyncSpellUses(id, usesLeft).sendTo(context.player);
        return true;
    }

    public int getUseCount(EntityPlayer player) {
        ITalismanUsesHandler usesHandler = player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        return usesHandler.getUses(id);
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return EnumActionResult.PASS;
        if (getSpellUses(playerIn, worldIn, pos, stack))
            return EnumActionResult.SUCCESS;
        castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return EnumActionResult.SUCCESS;
    }

    private boolean getSpellUses(EntityPlayer playerIn, World worldIn, BlockPos pos, ItemStack stack) {
        if (!playerIn.isSneaking())
            return false;
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() != ModBlocks.blockDiviner)
            return false;
        ITalismanUsesHandler usesHandler = playerIn.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        usesHandler.addUses(id, 10);
        return true;
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

        TalismanContext context = new TalismanContext(playerIn, worldIn, blockPos, hand, facing, CastType.RIGHT_CLICK);
        return cast(context);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public int getId() {
        return id;
    }

    protected boolean isConsumeCharge(TalismanContext context) {
        return true;
    }

    protected void performActionServer(TalismanContext context) {
    }

    protected void performActionClient(TalismanContext context) {
    }
}