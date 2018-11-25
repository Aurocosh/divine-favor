package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.api.internal.Vector3;
import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.requirements.base.ModSpellRequirements;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public abstract class ItemTalisman extends ItemMod implements IDivineFavorItem {
    private boolean castOnUse;
    private boolean castOnRightClick;
    private String requirementName;
    private SpellRequirement requirement;

    public ItemTalisman(String name, String requirementName, boolean castOnUse, boolean castOnRightClick) {
        super(name);
        requirement = null;
        this.requirementName = requirementName;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;
    }

    public abstract boolean castUse(SpellContext context);

    public abstract boolean castRightClick(SpellContext context);

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = worldIn.getBlockState(pos);
        if(state.getBlock() == ModBlocks.blockDiviner)
        {
            if(playerIn.isSneaking()) {
                addCharge(playerIn);
            }
            else {
                announceCost(worldIn);
            }

            return EnumActionResult.SUCCESS;
        }

        if(!castOnUse)
            return EnumActionResult.SUCCESS;

        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        SpellContext context = new SpellContext(playerIn,worldIn,pos,hand,facing,data);

        if(!claimCost(context))
            return EnumActionResult.SUCCESS;

        castUse(context);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if(!castOnRightClick)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        Vector3 posVec = new Vector3(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
        Vector3 lookVec = new Vector3(playerIn.getLookVec());
        RayTraceResult pos = raycast(worldIn, posVec, lookVec, 20);
        BlockPos blockPos = null;
        EnumFacing facing = EnumFacing.UP;
        if(pos != null)
        {
            blockPos = pos.getBlockPos();
            facing = pos.sideHit;
        }

        SpellContext context = new SpellContext(playerIn,worldIn,blockPos,hand,facing,data);

        if(!claimCost(context))
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        boolean success = castRightClick(context);
        return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.PASS, itemStackIn);
    }

    private boolean claimCost(SpellContext context){
        if(context.worldIn.isRemote)
            return false;
        return getRequirement().claimCost(context);
    }

    private void announceCost(World world){
        if(!world.isRemote)
            return;
        Minecraft.getMinecraft().player.sendChatMessage(getRequirement().toString());
    }

    private void addCharge(EntityPlayer playerIn){
        if(playerIn.world.isRemote)
            return;
        SpellRequirement requirement = getRequirement();
        if(requirement == ModSpellRequirements.free)
            return;

        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        data.provideSpellCharge(getRequirement().getChargeType(),1);
    }

    public SpellRequirement getRequirement() {
        if(requirement == null) {
            requirement = ModSpellRequirements.getRequirement(requirementName);
            if(requirement == null)
                requirement = ModSpellRequirements.free;
        }
        return requirement;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public static RayTraceResult raycast(World world, Vector3 origin, Vector3 ray, double len) {
        Vector3 end = origin.copy().add(ray.copy().normalize().multiply(len));
        RayTraceResult pos = world.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
        return pos;
    }
}