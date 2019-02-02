package aurocosh.divinefavor.common.item.grimoire;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireProvider;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireStorage;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;

public class ItemGrimoire extends ModItem {
    public final static int SLOT_COUNT = 27;
    private static String TAG_SHARE = "Grimoire";

    public ItemGrimoire() {
        super("grimoire", "grimoire");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemGrimoire))
            return EnumActionResult.PASS;

        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        assert grimoireHandler != null;

        ItemStack talismanStack = grimoireHandler.getSelectedStack();
        if (talismanStack.isEmpty())
            return EnumActionResult.PASS;

        ItemSpellTalisman talisman = (ItemSpellTalisman) talismanStack.getItem();
        talisman.castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() != ModItems.grimoire)
            return new ActionResult<>(EnumActionResult.PASS, stack);

        if (playerIn.isSneaking()) {
            playerIn.openGui(DivineFavor.instance, ConstGuiIDs.GRIMOIRE, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        assert grimoireHandler != null;

        ItemStack talismanStack = grimoireHandler.getSelectedStack();
        if (talismanStack.isEmpty())
            return new ActionResult<>(EnumActionResult.PASS, stack);

        ItemSpellTalisman talisman = (ItemSpellTalisman) talismanStack.getItem();
        talisman.castRightClick(worldIn, playerIn, hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack item, NBTTagCompound nbt) {
        if (item.getItem() == ModItems.grimoire)
            return new GrimoireProvider();
        return null;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        NBTTagCompound tag = super.getNBTShareTag(stack);
        if (tag == null)
            tag = new NBTTagCompound();

        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        assert grimoireHandler != null;
        NBTTagCompound tagShare = GrimoireStorage.getNbtBase(grimoireHandler);
        tag.setTag(TAG_SHARE, tagShare);
        return tag;
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        super.readNBTShareTag(stack, nbt);
        if (nbt == null)
            return;
        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        assert grimoireHandler != null;
        NBTTagCompound tagShare = nbt.getCompoundTag(TAG_SHARE);
        GrimoireStorage.readNbtBase(grimoireHandler, tagShare);
    }
}

