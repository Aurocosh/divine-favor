package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.api.internal.Vector3;
import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public abstract class ItemTalisman extends ItemMod implements IDivineFavorItem {
    private boolean castOnUse;
    private boolean castOnRightClick;

    public ItemTalisman(String name, boolean castOnUse, boolean castOnRightClick) {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;
    }


    public abstract boolean castUse(SpellContext context);

    public abstract boolean castRightClick(SpellContext context);

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!castOnUse)
            return EnumActionResult.PASS;

        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        SpellContext context = new SpellContext(playerIn,worldIn,pos,hand,facing,data);

        boolean did = castUse(context);
        return did ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
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

        boolean did = castRightClick(context);
        return new ActionResult<ItemStack>(did ? EnumActionResult.SUCCESS : EnumActionResult.PASS, itemStackIn);
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