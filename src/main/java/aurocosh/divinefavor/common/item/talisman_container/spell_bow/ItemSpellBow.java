package aurocosh.divinefavor.common.item.talisman_container.spell_bow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.ISpellBowHandler;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowProvider;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowStorage;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.UtilBow;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW;

public class ItemSpellBow extends ModItem {
    public static final int SIZE = 27;
    public static final String TAG_IS_IN_BOOK_MODE = "IsInBookMode";
    private static String TAG_SHARE = "SpellBowShare";

    public ItemSpellBow() {
        super("spell_bow", "spell_bow/spell_bow", ConstMainTabOrder.CONTAINERS);
        maxStackSize = 1;
        setCreativeTab(DivineFavor.TAB_MAIN);
        setMaxDamage(384);

        addPropertyOverride(new ResourceLocation("book_mode"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase) {
                NBTTagCompound nbt = UtilNbt.getNbt(stack);
                return nbt.getBoolean(TAG_IS_IN_BOOK_MODE) ? 1 : 0;
            }
        });
        addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase) {
                if (entityLivingBase == null)
                    return 0.0F;
                else
                    return entityLivingBase.getActiveItemStack().getItem() != ModItems.spell_bow ? 0.0F : (float) (stack.getMaxItemUseDuration() - entityLivingBase.getItemInUseCount()) / 20.0F;
            }
        });
        addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    private ItemStack findAmmo(EntityPlayer player) {
        return UtilPlayer.findStackInInventory(player, stack -> stack.getItem() instanceof ItemArrow).stack;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack bowStack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if (UtilNbt.getNbt(bowStack).getBoolean(TAG_IS_IN_BOOK_MODE))
            return;
        if (!(entityLiving instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entityLiving;
        boolean unlimitedArrows = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bowStack) > 0;
        ItemStack arrowStack = findAmmo(player);

        int charge = getMaxItemUseDuration(bowStack) - timeLeft;
        charge = ForgeEventFactory.onArrowLoose(bowStack, world, player, charge, !arrowStack.isEmpty() || unlimitedArrows);
        if (charge < 0)
            return;
        if (arrowStack.isEmpty() && !unlimitedArrows)
            return;

        if (arrowStack.isEmpty())
            arrowStack = new ItemStack(Items.ARROW);

        float velocity = UtilBow.getArrowVelocity(charge);
        if (!((double) velocity >= 0.1D))
            return;

        boolean stackIsInfinite = player.capabilities.isCreativeMode || (arrowStack.getItem() instanceof ItemArrow && ((ItemArrow) arrowStack.getItem()).isInfinite(arrowStack, bowStack, player));
        if (!world.isRemote) {
            ItemArrowTalisman talisman = SpellBowStorage.get(bowStack).getSelectedTalisman();
            EntityArrow entityArrow;
            if (talisman != null && talisman.claimCost(world, player))
                entityArrow = talisman.createArrow(world, talisman, player);
            else
                entityArrow = getStandardArrow(world, arrowStack, player);
            entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity * 3.0F, 1.0F);

            if (velocity == 1.0F)
                entityArrow.setIsCritical(true);

            int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bowStack);
            if (powerLevel > 0)
                entityArrow.setDamage(entityArrow.getDamage() + (double) powerLevel * 0.5D + 0.5D);

            int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bowStack);
            if (punchLevel > 0)
                entityArrow.setKnockbackStrength(punchLevel);

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bowStack) > 0)
                entityArrow.setFire(100);

            bowStack.damageItem(1, player);

            if (stackIsInfinite || player.capabilities.isCreativeMode && (arrowStack.getItem() == Items.SPECTRAL_ARROW || arrowStack.getItem() == Items.TIPPED_ARROW))
                entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
            world.spawnEntity(entityArrow);
            if (talisman != null)
                talisman.postInit(entityArrow);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

        if (!stackIsInfinite && !player.capabilities.isCreativeMode)
            UtilPlayer.damageStack(player, arrowStack);
        player.addStat(StatList.getObjectUseStats(this));
    }

    private EntityArrow getStandardArrow(World world, ItemStack arrowStack, EntityLivingBase shooter) {
        EntityTippedArrow arrow = new EntityTippedArrow(world, shooter);
        arrow.setPotionEffect(arrowStack);
        return arrow;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!(itemstack.getItem() instanceof ItemSpellBow))
            return new ActionResult<>(EnumActionResult.PASS, itemstack);

        NBTTagCompound nbt = UtilNbt.getNbt(itemstack);
        boolean isBook = nbt.getBoolean(TAG_IS_IN_BOOK_MODE);
        if (playerIn.isSneaking()) {
            nbt.setBoolean(TAG_IS_IN_BOOK_MODE, !isBook);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        if (isBook)
            return doBookAction(worldIn, playerIn, itemstack);
        else
            return doBowAction(worldIn, playerIn, handIn, itemstack);
    }

    private ActionResult<ItemStack> doBookAction(World world, EntityPlayer player, ItemStack stack) {
        player.openGui(DivineFavor.instance, ConstGuiIDs.SPELL_BOW, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private ActionResult<ItemStack> doBowAction(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemStack itemstack) {
        boolean gotArrows = !findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, gotArrows);
        if (ret != null)
            return ret;

        if (!playerIn.capabilities.isCreativeMode && !gotArrows)
            return gotArrows ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack item, NBTTagCompound nbt) {
        if (item.getItem() == ModItems.spell_bow)
            return new SpellBowProvider();
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

        ISpellBowHandler grimoireHandler = stack.getCapability(CAPABILITY_SPELL_BOW, null);
        assert grimoireHandler != null;
        NBTTagCompound tagShare = SpellBowStorage.getNbtBase(grimoireHandler);
        tag.setTag(TAG_SHARE, tagShare);
        return tag;
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        super.readNBTShareTag(stack, nbt);
        if (nbt == null)
            return;
        ISpellBowHandler grimoireHandler = stack.getCapability(CAPABILITY_SPELL_BOW, null);
        assert grimoireHandler != null;
        NBTTagCompound tagShare = nbt.getCompoundTag(TAG_SHARE);
        SpellBowStorage.readNbtBase(grimoireHandler, tagShare);
    }
}