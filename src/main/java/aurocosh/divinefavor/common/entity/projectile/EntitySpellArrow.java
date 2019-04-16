package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpellArrow extends EntityArrow {
    private static final String TAG_COLOR = "Color";
    private static final String TAG_ARROW_TYPE = "ArrowType";
    private static final String TAG_TALISMAN = "Talisman";
    private double gravityValue = 0.05000000074505806D;

    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<String> TALISMAN_ID = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> HAS_ANTI_GRAVITY = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.BOOLEAN);

    private ItemArrowTalisman talisman;
    private EntityLivingBase shooter;

    public EntitySpellArrow(World worldIn) {
        super(worldIn);
    }

    public EntitySpellArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntitySpellArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public void setSpell(ItemArrowTalisman talisman, EntityLivingBase shooter) {
        this.talisman = talisman;
        this.shooter = shooter;
        setColor(talisman.getColor());
        setArrowType(talisman.getArrowType().getValue());
        setTalismanId(talisman.getRegistryName().toString());
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(COLOR, -1);
        dataManager.register(TYPE, ArrowType.WOODEN_ARROW.getValue());
        dataManager.register(TALISMAN_ID, "");
        dataManager.register(HAS_ANTI_GRAVITY, true);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();
        if (hasAntiGravity())
            this.motionY += gravityValue;
        if (world.isRemote) {
            if (!inGround)
                spawnPotionParticles(2);
            else if (timeInGround % 5 == 0)
                spawnPotionParticles(1);
            if (talisman != null)
                talisman.spawnParticles(this);
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


//    protected void arrowHit(EntityLivingBase living) {
//        if (talisman != null && shooter != null)
//            talisman.getSpell().cast(living, shooter, this);
//    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        if (talisman != null && shooter != null) {
            Entity entity = raytraceResultIn.entityHit;
            EntityLivingBase living = entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
            talisman.cast(living, shooter, this, raytraceResultIn.getBlockPos(), raytraceResultIn.sideHit);
            if (talisman.isBreakOnHit())
                setDead();
        }
        super.onHit(raytraceResultIn);
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }

    public boolean isInGround() {
        return inGround;
    }

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


    public void notifyDataManagerChange(DataParameter<?> key) {
        if (TALISMAN_ID.equals(key))
            talisman = getTalisman();
    }

    private ItemArrowTalisman getTalisman() {
        Item item = Item.getByNameOrId(dataManager.get(TALISMAN_ID));
        if (item instanceof ItemArrowTalisman)
            return (ItemArrowTalisman) item;
        return null;
    }

    public boolean hasAntiGravity() {
        return this.dataManager.get(HAS_ANTI_GRAVITY);
    }

    public void setHasAntiGravity(boolean hasAntiGravity) {
        this.dataManager.set(HAS_ANTI_GRAVITY, hasAntiGravity);
    }

    public void setDespawnDelay(int delay) {
        timeInGround = 1200 - delay;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(TAG_COLOR, getColor());
        compound.setInteger(TAG_ARROW_TYPE, getArrowType().getValue());
        compound.setString(TAG_TALISMAN, getTalismanId());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        setColor(compound.getInteger(TAG_COLOR));
        setArrowType(compound.getInteger(TAG_ARROW_TYPE));
        String talismanId = compound.getString(TAG_TALISMAN);
        setTalismanId(talismanId);

        Item item = Item.getByNameOrId(talismanId);
        if (item instanceof ItemArrowTalisman)
            talisman = (ItemArrowTalisman) item;
    }

    public String getTalismanId() {
        return dataManager.get(TALISMAN_ID);
    }

    public ArrowType getArrowType() {
        return ArrowType.get(dataManager.get(TYPE));
    }

    public int getColor() {
        return dataManager.get(COLOR);
    }

    private void setTalismanId(String string) {
        dataManager.set(TALISMAN_ID, string);
    }

    private void setArrowType(int value) {
        dataManager.set(TYPE,  ArrowType.get(value).getValue());
    }

    private void setColor(int color) {
        dataManager.set(COLOR, color);
    }
}