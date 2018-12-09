package aurocosh.divinefavor.common.item.talisman;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibGuiIDs;
import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.math.Vector3;
import aurocosh.divinefavor.common.network.base.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
    private final ModSpell spell;
    private final ModFavor favor;
    private final int favorPerUse;
    private final boolean castOnUse;
    private final boolean castOnRightClick;

    public ItemTalisman(String name, ModSpell spell, ModFavor favor, int favorPerUse, boolean castOnUse, boolean castOnRightClick) {
        super(name);
        this.spell = spell;
        this.favor = favor;
        this.favorPerUse = favorPerUse;
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public static RayTraceResult raycast(World world, Vector3 origin, Vector3 ray, double len) {
        Vector3 end = origin.copy().add(ray.copy().normalize().multiply(len));
        RayTraceResult pos = world.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
        return pos;
    }

    public boolean castUse(SpellContext context) {
        return spell.cast(context);
    }

    public boolean castRightClick(SpellContext context) {
        return spell.cast(context);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!spell.isCodeSideCorrect(worldIn))
            return EnumActionResult.SUCCESS;

        if (!castOnUse)
            return EnumActionResult.SUCCESS;

        SpellContext context = new SpellContext(playerIn, worldIn, pos, hand, facing);
        if (!claimCost(context))
            return EnumActionResult.SUCCESS;

        castUse(context);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if (hand == EnumHand.OFF_HAND)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        if (playerIn.isSneaking()) {
            if (!worldIn.isRemote)
                return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
            playerIn.openGui(DivineFavor.instance, LibGuiIDs.TALISMAN, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
        }

        if (!spell.isCodeSideCorrect(worldIn))
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        if (!castOnRightClick)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        Vector3 posVec = new Vector3(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
        Vector3 lookVec = new Vector3(playerIn.getLookVec());
        RayTraceResult pos = raycast(worldIn, posVec, lookVec, 20);
        BlockPos blockPos = null;
        EnumFacing facing = EnumFacing.UP;
        if (pos != null) {
            blockPos = pos.getBlockPos();
            facing = pos.sideHit;
        }

        SpellContext context = new SpellContext(playerIn, worldIn, blockPos, hand, facing);

        if (!claimCost(context))
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        boolean success = castRightClick(context);
        return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.PASS, itemStackIn);
    }

    private boolean claimCost(SpellContext context) {
        if (context.world.isRemote)
            return false;
        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        if(favorHandler == null)
            return false;
        if(favorHandler.consumeFavor(favor.id,favorPerUse))
            return false;

        int favorValue = favorHandler.getFavor(favor.id);
        MessageSyncFavor message = new MessageSyncFavor(favor.id,favorValue);
        NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) context.player);
        return true;
    }

    public int getUseCount(EntityPlayer player){
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        if(favorHandler == null)
            return 0;

        int favorCount = favorHandler.getFavor(favor.id);
        if(favorCount == 0)
            return 0;

        return favorCount / favorPerUse;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public String getCostTranslationKey() {
        return "cost." + getTranslationKey();
    }

    @Override
    public String getTranslationKey() {
        return LibMisc.MOD_ID + ":" + getRegistryName().getPath();
    }
}