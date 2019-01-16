package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.item.arrows.ItemCursedArrow;
import aurocosh.divinefavor.common.potions.base.effect.PotionEffectCurse;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCursedArrow extends EntityArrow {
    private static final String TAG_ITEM = "Item";
    private static final String TAG_DURATION = "Duration";
    private static final String TAG_COLOR = "Color";

    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityCursedArrow.class, DataSerializers.VARINT);

    int duration;
    private ItemCursedArrow cursedArrow;

    public EntityCursedArrow(World worldIn) {
        super(worldIn);
    }

    public EntityCursedArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityCursedArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public void setPotionEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemCursedArrow))
            return;
        cursedArrow = (ItemCursedArrow) item;

        if (!UtilNbt.checkForTags(stack, ItemCursedArrow.TAG_DURATION))
            return;

        NBTTagCompound nbt = stack.getTagCompound();
        assert nbt != null;
        duration = nbt.getInteger(ItemCursedArrow.TAG_DURATION);
        dataManager.set(COLOR, cursedArrow.getColor());
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(COLOR, -1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote) {
            if (!inGround)
                spawnPotionParticles(2);
            else if (timeInGround % 5 == 0)
                spawnPotionParticles(1);
        }
        else if (inGround && timeInGround != 0 && timeInGround >= 6000) {
            world.setEntityState(this, (byte) 0);
            cursedArrow = null;
            duration = 0;
            dataManager.set(COLOR, -1);
        }
    }

    private void spawnPotionParticles(int particleCount) {
        int i = getColor();
        if (i != -1 && particleCount > 0) {
            double d0 = (double) (i >> 16 & 255) / 255.0D;
            double d1 = (double) (i >> 8 & 255) / 255.0D;
            double d2 = (double) (i >> 0 & 255) / 255.0D;

            for (int j = 0; j < particleCount; ++j)
                world.spawnParticle(EnumParticleTypes.SPELL_MOB, posX + (rand.nextDouble() - 0.5D) * (double) width, posY + rand.nextDouble() * (double) height, posZ + (rand.nextDouble() - 0.5D) * (double) width, d0, d1, d2);
        }
    }

    public int getColor() {
        return dataManager.get(COLOR);
    }

    private void setColor(int color) {
        dataManager.set(COLOR, color);
    }
//
//    public static void registerFixesTippedArrow(DataFixer fixer) {
//        EntityArrow.registerFixesArrow(fixer, "TippedArrow");
//    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        if (cursedArrow != null) {
            compound.setString(TAG_ITEM, cursedArrow.getRegistryName().toString());
            compound.setInteger(TAG_DURATION, duration);
        }

        int color = getColor();
        if (color != -1)
            compound.setInteger(TAG_COLOR, color);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (UtilNbt.checkForTags(compound, TAG_ITEM, TAG_DURATION)) {
            String potionName = compound.getString(TAG_ITEM);
            cursedArrow = (ItemCursedArrow) ModRegistries.arrows.getValue(new ResourceLocation(potionName));
            duration = compound.getInteger(TAG_DURATION);
        }
        if (compound.hasKey(TAG_COLOR))
            setColor(compound.getInteger(TAG_COLOR));
    }

    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);
        living.addPotionEffect(new PotionEffectCurse(cursedArrow.getPotion(), duration));
    }

    protected ItemStack getArrowStack() {
        if (cursedArrow == null)
            return new ItemStack(Items.ARROW);
        else {
            ItemStack itemstack = new ItemStack(cursedArrow);
            NBTTagCompound nbt = UtilNbt.getEistingOrNewNBT(itemstack);
            nbt.setInteger(TAG_DURATION, duration);
            return itemstack;
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 0) {
            int i = getColor();

            if (i != -1) {
                double d0 = (double) (i >> 16 & 255) / 255.0D;
                double d1 = (double) (i >> 8 & 255) / 255.0D;
                double d2 = (double) (i >> 0 & 255) / 255.0D;

                for (int j = 0; j < 20; ++j)
                    world.spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, posX + (rand.nextDouble() - 0.5D) * (double) width, posY + rand.nextDouble() * (double) height, posZ + (rand.nextDouble() - 0.5D) * (double) width, d0, d1, d2);
            }
        }
        else
            super.handleStatusUpdate(id);
    }
}