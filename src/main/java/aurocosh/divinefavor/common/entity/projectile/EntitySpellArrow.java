package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowType;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ItemArrowTalisman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpellArrow extends EntityArrow {
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);

    private ItemArrowTalisman talisman;
    private EntityLivingBase shooter;

    public EntitySpellArrow(World worldIn) {
        super(worldIn);
        init();
    }

    public EntitySpellArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        init();
    }

    public EntitySpellArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        init();
    }

    private void init() {
        setDamage(0.0f);
    }

    public void setSpell(ItemArrowTalisman talisman, EntityLivingBase shooter) {
        this.talisman = talisman;
        this.shooter = shooter;
        dataManager.set(COLOR, talisman.getColor());
        dataManager.set(TYPE, talisman.getArrowType().getValue());
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(COLOR, -1);
        dataManager.register(TYPE, ArrowType.WOODEN_ARROW.getValue());
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
            talisman = null;
            shooter = null;
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

    public ArrowType getArrowType() {
        return ArrowType.get(dataManager.get(TYPE));
    }

    public int getColor() {
        return dataManager.get(COLOR);
    }

//    protected void arrowHit(EntityLivingBase living) {
//        if (talisman != null && shooter != null)
//            talisman.getSpell().cast(living, shooter, this);
//    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        super.onHit(raytraceResultIn);
        if (talisman == null || shooter == null)
            return;
        Entity entity = raytraceResultIn.entityHit;
        EntityLivingBase living = entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
        talisman.getSpell().cast(living, shooter, this);
        if(talisman.isBreakOnHit())
            setDead();
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
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