package aurocosh.divinefavor.common.item.talisman;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.api.internal.Vector3;
import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.constants.LibFavorType;
import aurocosh.divinefavor.common.constants.LibGuiIDs;
import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.item.base.IDescriptionProvider;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.darkhax.bookshelf.util.GameUtils;
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

public class ItemTalisman extends ItemMod implements IDivineFavorItem, IDescriptionProvider {
    private boolean castOnUse;
    private boolean castOnRightClick;
    private ModSpell modSpell;
    private SpellRequirement requirement;

    public ItemTalisman(TalismanData talismanData){
        this(talismanData.name,talismanData.spellType,talismanData.requirement,talismanData.castOnUse,talismanData.castOnRightClick);
    }

    public ItemTalisman(String name, SpellType spellType, SpellRequirement requirement, boolean castOnUse, boolean castOnRightClick) {
        super(name);
        this.modSpell = ModSpells.get(spellType);
        this.requirement = requirement;
        if(this.requirement == null)
            this.requirement = new SpellRequirement();
        requirement.init();
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }
    public boolean castUse(SpellContext context) {
        return modSpell.cast(context);
    }

    public boolean castRightClick(SpellContext context) {
        return modSpell.cast(context);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(modSpell.isWrongSpellSide())
            return EnumActionResult.SUCCESS;

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
        if(hand == EnumHand.OFF_HAND)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        if(playerIn.isSneaking()){
            if(!GameUtils.isClient())
                return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
            playerIn.openGui(DivineFavor.instance, LibGuiIDs.TALISMAN, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
        }

        if(modSpell.isWrongSpellSide())
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
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
        return requirement.claimCost(context);
    }

    private void announceCost(World world){
        if(!world.isRemote)
            return;
        Minecraft.getMinecraft().player.sendChatMessage(requirement.toString());
    }

    private void addCharge(EntityPlayer playerIn){
        if(playerIn.world.isRemote)
            return;

        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        data.provideSpellCharge(LibFavorType.ALLFIRE,1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public SpellRequirement getRequirement() { return requirement; }

    public static RayTraceResult raycast(World world, Vector3 origin, Vector3 ray, double len) {
        Vector3 end = origin.copy().add(ray.copy().normalize().multiply(len));
        RayTraceResult pos = world.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
        return pos;
    }

    public String getCostTranslationKey(){
        return  "cost." + getTranslationKey();
    }

    @Override
    public String getTranslationKey() {
        return LibMisc.MOD_ID + ":" + getRegistryName().getResourcePath();
    }
}