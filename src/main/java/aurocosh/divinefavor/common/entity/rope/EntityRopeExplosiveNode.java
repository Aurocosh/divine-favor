package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRopeExplosiveNode extends EntityRopeNodeBase {
    private static final DataParameter<Boolean> TRIGGERED = EntityDataManager.createKey(EntityRopeExplosiveNode.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(EntityRopeExplosiveNode.class, DataSerializers.VARINT);
    private static final String TAG_TRIGGERED = "Triggered";
    private static final String TAG_FUSE = "Fuse";

    private boolean triggered;
    private int fuse;

    public EntityRopeExplosiveNode(World world) {
        super(world);
        setSize(0.2f, 0.2f);
        triggered = false;
        fuse = 80;
        preventEntitySpawning = true;
        isImmuneToFire = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(TRIGGERED, false);
        dataManager.register(FUSE, 80);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (!triggered)
            if (world.isFlammableWithin(this.getEntityBoundingBox().shrink(0.001D)))
                setTriggered(true);

        if (triggered && --fuse <= 0) {
            setDead();

            if (!world.isRemote) {
                triggerNeighbour(getNextNode());
                triggerNeighbour(getPrevNode());

                world.createExplosion(this, posX, posY + (double) (height / 16.0F), posZ, 2.0f, true);
            }
        }
    }

    private void triggerNeighbour(Entity entity) {
        if (entity instanceof EntityRopeExplosiveNode) {
            EntityRopeExplosiveNode explosiveNode = (EntityRopeExplosiveNode) entity;
            triggerNode(explosiveNode);
        }
    }

    private void triggerNode(EntityRopeExplosiveNode explosiveNode) {
        if (!explosiveNode.getTriggered()) {
            explosiveNode.setTriggered(true);
            explosiveNode.setFuse(UtilRandom.nextInt(1, 5));
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        setTriggered(nbt.getBoolean(TAG_TRIGGERED));
        setFuse(nbt.getShort(TAG_FUSE));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean(TAG_TRIGGERED, getTriggered());
        nbt.setShort(TAG_FUSE, (short) getFuse());
    }

    public boolean getTriggered() {
        return dataManager.get(TRIGGERED);
    }

    public void setTriggered(boolean value) {
        dataManager.set(TRIGGERED, value);
        triggered = value;
    }

    public int getFuseDataManager() {
        return dataManager.get(FUSE);
    }

    public void setFuse(int value) {
        dataManager.set(FUSE, value);
        fuse = value;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key))
            this.fuse = this.getFuseDataManager();
        if (TRIGGERED.equals(key))
            this.triggered = this.getTriggered();
    }

    public int getFuse() {
        return this.fuse;
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.rope_explosive, 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.rope_explosive);
        if (slotData.slotIndex == -1)
            return false;
        slotData.stack.shrink(1);
        player.inventory.setInventorySlotContents(slotData.slotIndex, slotData.stack.getCount() > 0 ? slotData.stack : ItemStack.EMPTY);
        return true;
    }

    @Override
    protected EntityRopeNodeBase makeNewNode(World world) {
        return new EntityRopeExplosiveNode(world);
    }

    @Override
    protected Class<? extends EntityRopeNodeBase> getEntityClass() {
        return EntityRopeExplosiveNode.class;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (world.isRemote)
            return true;

        Item item = player.getHeldItem(hand).getItem();
        if (item == Items.FLINT_AND_STEEL) {
            setTriggered(true);
            Entity nextNode = getNextNode();
            if (nextNode instanceof EntityPlayer)
                setNextNode(null);
            return true;
        }
        else
            return super.processInitialInteract(player, hand);
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isExplosion())
            triggerNode(this);
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected boolean isEmittingLight() {
        return true;
    }
}