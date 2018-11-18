package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public class ItemTalisman extends ItemMod implements IDivineFavorItem {
    public ItemTalisman() {
        super(LibItemNames.TALISMAN);
        setMaxStackSize(1);

        //new AssemblyScavengeRecipe();
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        SpellContext context = new SpellContext(playerIn,worldIn,pos,hand,facing,data);

        ModSpells.ignition.cast(context);
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);

        boolean did = true;
        /*
        ItemStack bullet = getBulletInSocket(itemStackIn, getSelectedSlot(itemStackIn));
        boolean did = cast(worldIn, playerIn, data, bullet, itemStackIn, 40, 25, 0.5F, null);

        if(bullet.isEmpty() && craft(playerIn, new ItemStack(Items.REDSTONE), new ItemStack(ModItems.material))) {
            if(!worldIn.isRemote)
                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, PsiSoundHandler.cadShoot, SoundCategory.PLAYERS, 0.5F, (float) (0.5 + Math.random() * 0.5));
            data.deductPsi(100, 60, true);

            if(data.level == 0)
                data.levelUp();
            did = true;
        }
        */

        return new ActionResult<ItemStack>(did ? EnumActionResult.SUCCESS : EnumActionResult.PASS, itemStackIn);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(tab != getCreativeTab())
            return;

        // Basic Iron CAD
        subItems.add(new ItemStack(ModItems.talisman, 1, 0));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}