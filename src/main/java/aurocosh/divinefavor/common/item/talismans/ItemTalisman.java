package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.math.Vector3;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.client.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class ItemTalisman extends ModItem {
    private final ModSpell spell;
    private final ModFavor favor;
    private final int favorPerUse;
    public final boolean castOnUse;
    public final boolean castOnRightClick;

// Talisman functions
    public ItemTalisman(String name, ModSpell spell, ModFavor favor, int favorPerUse, boolean castOnUse, boolean castOnRightClick) {
        super("talisman_" + name, "talismans/");

        this.spell = spell;
        this.favor = favor;
        this.favorPerUse = favorPerUse;
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public boolean cast(SpellContext context) {
        if(!claimCost(context))
            return false;
        return spell.cast(context);
    }

    private boolean claimCost(SpellContext context) {
        if (context.world.isRemote)
            return false;
        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        if(!favorHandler.consumeFavor(favor.id,favorPerUse))
            return false;

        int favorValue = favorHandler.getFavor(favor.id);
        MessageSyncFavor message = new MessageSyncFavor(favor.id,favorValue);
        NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) context.player);
        return true;
    }

    public int getUseCount(EntityPlayer player){
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        int favorCount = favorHandler.getFavor(favor.id);
        if(favorCount == 0)
            return 0;

        return favorCount / favorPerUse;
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return EnumActionResult.PASS;

        if(!castOnUse)
            return EnumActionResult.PASS;

        SpellContext context = new SpellContext(playerIn, worldIn, pos, hand, facing, CastType.ITEM_USE_CAST);
        cast(context);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return new ActionResult<>(EnumActionResult.PASS, stack);

        if(worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.PASS, stack);

        if(!castOnRightClick)
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
        boolean success = cast(context);
        return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.PASS, stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        return super.updateItemStackNBT(nbt);
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        return super.getNBTShareTag(stack);
    }
}