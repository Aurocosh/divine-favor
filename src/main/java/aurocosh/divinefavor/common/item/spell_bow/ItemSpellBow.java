package aurocosh.divinefavor.common.item.spell_bow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.spell_bow.capability.ISpellBowHandler;
import aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowProvider;
import aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowStorage;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW;

public class ItemSpellBow extends ModItem {
    public static final String TAG_IS_IN_BOOK_MODE = "IsInBookMode";
    private static String TAG_SHARE = "SpellBowShare";

    public ItemSpellBow()
    {
        super("spell_bow","spell_bow/spell_bow");
        this.maxStackSize = 1;
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
        this.setMaxDamage(384);

        this.addPropertyOverride(new ResourceLocation("book_mode"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase)
            {
                NBTTagCompound nbt = UtilNbt.getTag(stack);
                return nbt.getBoolean(TAG_IS_IN_BOOK_MODE) ? 1 : 0;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase)
            {
                if (entityLivingBase == null)
                    return 0.0F;
                else
                    return entityLivingBase.getActiveItemStack().getItem() != ModItems.spell_bow ? 0.0F : (float) (stack.getMaxItemUseDuration() - entityLivingBase.getItemInUseCount()) / 20.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
            {
                return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    protected boolean isArrow(ItemStack stack)
    {
        return stack.getItem() instanceof ItemArrow;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if(UtilNbt.getTag(stack).getBoolean(TAG_IS_IN_BOOK_MODE))
            return;

        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {
                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f == 1.0F)
                        {
                            entityarrow.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                        if (j > 0)
                        {
                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

                        if (k > 0)
                        {
                            entityarrow.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
                        {
                            entityarrow.setFire(100);
                        }

                        stack.damageItem(1, entityplayer);

                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
                        {
                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.spawnEntity(entityarrow);
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getArrowVelocity(int charge)
    {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!(itemstack.getItem() instanceof ItemSpellBow))
            return new ActionResult<>(EnumActionResult.PASS, itemstack);

        NBTTagCompound nbt = UtilNbt.getTag(itemstack);
        boolean isBook = nbt.getBoolean(TAG_IS_IN_BOOK_MODE);
        if(playerIn.isSneaking()){
            nbt.setBoolean(TAG_IS_IN_BOOK_MODE,!isBook);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        if(isBook)
            return doBookAction(worldIn, playerIn, itemstack);
        else
            return doBowAction(worldIn, playerIn, handIn, itemstack);
    }

    private ActionResult<ItemStack> doBookAction(World world, EntityPlayer player, ItemStack stack) {
        player.openGui(DivineFavor.instance, ConstGuiIDs.SPELL_BOW, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private ActionResult<ItemStack> doBowAction(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemStack itemstack) {
        boolean gotArrows = !this.findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, gotArrows);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !gotArrows)
        {
            return gotArrows ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
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