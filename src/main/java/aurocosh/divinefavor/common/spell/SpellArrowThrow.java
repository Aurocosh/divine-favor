package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SpellArrowThrow extends ModSpell {
    public SpellArrowThrow() {
        super(SpellType.ARROW_THROW);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.world.isRemote)
        //    return true;

        ItemStack stack = context.player.getHeldItem(context.hand);
        shootArrow(stack,context.world,context.player,20000);

        return true;
    }

    private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
            return player.getHeldItem(EnumHand.OFF_HAND);
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
            return player.getHeldItem(EnumHand.MAIN_HAND);
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                    return itemstack;
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
    public void shootArrow(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int charge)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode;
            ItemStack itemstack = this.findAmmo(entityplayer);

            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, charge, !itemstack.isEmpty() || flag);
            if (charge < 0)
                return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                    itemstack = new ItemStack(Items.ARROW);

                float f = getArrowVelocity(charge);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {
                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f == 1.0F)
                            entityarrow.setIsCritical(true);

                        stack.damageItem(1, entityplayer);

                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;

                        worldIn.spawnEntity(entityarrow);
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (spellRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                            entityplayer.inventory.deleteStack(itemstack);
                    }
                }
            }
        }
    }

    /**
     * Gets the velocity of the arrow entity from the bow's favorCost
     */
    public static float getArrowVelocity(int charge)
    {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        return f < 1.0F ? f : 1.0F;
    }
}
