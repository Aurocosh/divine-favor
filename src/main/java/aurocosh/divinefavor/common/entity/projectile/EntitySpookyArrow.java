package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntitySpookyArrow extends EntitySpellArrow {
    private static final DataParameter<Integer> SOUND_SET_INDEX = EntityDataManager.createKey(EntitySpookyArrow.class, DataSerializers.VARINT);
    private static final String TAG_SOUND_SET = "SoundSet";
    private static final String TAG_NEXT_SOUND_DELAY = "NextSoundDelay";

    private static final SoundEvent[][] SPOOKY_SOUNDS = new SoundEvent[][]{
            {SoundEvents.ENTITY_BAT_DEATH,
                    SoundEvents.ENTITY_BAT_HURT,
                    SoundEvents.ENTITY_BAT_LOOP,
                    SoundEvents.ENTITY_BAT_TAKEOFF},
            {SoundEvents.ENTITY_BLAZE_DEATH,
                    SoundEvents.ENTITY_BLAZE_BURN,
                    SoundEvents.ENTITY_BLAZE_HURT,
                    SoundEvents.ENTITY_BLAZE_SHOOT},
            {SoundEvents.ENTITY_CAT_DEATH,
                    SoundEvents.ENTITY_CAT_HISS,
                    SoundEvents.ENTITY_CAT_HURT,
                    SoundEvents.ENTITY_CAT_PURR,
                    SoundEvents.ENTITY_CAT_PURREOW},
            {SoundEvents.ENTITY_CHICKEN_DEATH,
                    SoundEvents.ENTITY_CHICKEN_EGG,
                    SoundEvents.ENTITY_CHICKEN_HURT,
                    SoundEvents.ENTITY_CHICKEN_STEP},
            {SoundEvents.ENTITY_COW_DEATH,
                    SoundEvents.ENTITY_COW_HURT,
                    SoundEvents.ENTITY_COW_MILK,
                    SoundEvents.ENTITY_COW_STEP},
            {SoundEvents.ENTITY_CREEPER_DEATH,
                    SoundEvents.ENTITY_CREEPER_HURT,
                    SoundEvents.ENTITY_CREEPER_PRIMED},
            {SoundEvents.ENTITY_DONKEY_ANGRY,
                    SoundEvents.ENTITY_DONKEY_CHEST,
                    SoundEvents.ENTITY_DONKEY_DEATH,
                    SoundEvents.ENTITY_DONKEY_HURT},
            {SoundEvents.ENTITY_ELDERGUARDIAN_AMBIENTLAND,
                    SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE,
                    SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH,
                    SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND,
                    SoundEvents.ENTITY_ELDER_GUARDIAN_FLOP,
                    SoundEvents.ENTITY_ELDER_GUARDIAN_HURT,
                    SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND},
            {SoundEvents.ENTITY_ENDERDRAGON_DEATH,
                    SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD,
                    SoundEvents.ENTITY_ENDERDRAGON_FLAP,
                    SoundEvents.ENTITY_ENDERDRAGON_GROWL,
                    SoundEvents.ENTITY_ENDERDRAGON_HURT,
                    SoundEvents.ENTITY_ENDERDRAGON_SHOOT},
            {SoundEvents.ENTITY_ENDERMEN_DEATH,
                    SoundEvents.ENTITY_ENDERMEN_HURT,
                    SoundEvents.ENTITY_ENDERMEN_SCREAM,
                    SoundEvents.ENTITY_ENDERMEN_STARE,
                    SoundEvents.ENTITY_ENDERMEN_TELEPORT},
            {SoundEvents.ENTITY_GHAST_DEATH,
                    SoundEvents.ENTITY_GHAST_HURT,
                    SoundEvents.ENTITY_GHAST_SCREAM,
                    SoundEvents.ENTITY_GHAST_SHOOT,
                    SoundEvents.ENTITY_GHAST_WARN},
            {SoundEvents.ENTITY_GUARDIAN_DEATH,
                    SoundEvents.ENTITY_GUARDIAN_ATTACK,
                    SoundEvents.ENTITY_GUARDIAN_FLOP,
                    SoundEvents.ENTITY_GUARDIAN_HURT},
            {SoundEvents.ENTITY_HORSE_DEATH,
                    SoundEvents.ENTITY_HORSE_ANGRY,
                    SoundEvents.ENTITY_HORSE_ARMOR,
                    SoundEvents.ENTITY_HORSE_BREATHE,
                    SoundEvents.ENTITY_HORSE_EAT,
                    SoundEvents.ENTITY_HORSE_GALLOP,
                    SoundEvents.ENTITY_HORSE_HURT,
                    SoundEvents.ENTITY_HORSE_JUMP,
                    SoundEvents.ENTITY_HORSE_LAND,
                    SoundEvents.ENTITY_HORSE_SADDLE,
                    SoundEvents.ENTITY_HORSE_STEP,
                    SoundEvents.ENTITY_HORSE_STEP_WOOD},
            {SoundEvents.ENTITY_HUSK_DEATH,
                    SoundEvents.ENTITY_HUSK_HURT,
                    SoundEvents.ENTITY_HUSK_STEP},
            {SoundEvents.ENTITY_LLAMA_DEATH,
                    SoundEvents.ENTITY_LLAMA_ANGRY,
                    SoundEvents.ENTITY_LLAMA_CHEST,
                    SoundEvents.ENTITY_LLAMA_EAT,
                    SoundEvents.ENTITY_LLAMA_HURT,
                    SoundEvents.ENTITY_LLAMA_SPIT,
                    SoundEvents.ENTITY_LLAMA_STEP,
                    SoundEvents.ENTITY_LLAMA_SWAG},
            {null, SoundEvents.ENTITY_MAGMACUBE_DEATH,
                    SoundEvents.ENTITY_MAGMACUBE_HURT,
                    SoundEvents.ENTITY_MAGMACUBE_JUMP,
                    SoundEvents.ENTITY_MAGMACUBE_SQUISH},
            {SoundEvents.ENTITY_PARROT_DEATH,
                    SoundEvents.ENTITY_PARROT_EAT,
                    SoundEvents.ENTITY_PARROT_FLY,
                    SoundEvents.ENTITY_PARROT_HURT},
            {SoundEvents.ENTITY_PIG_DEATH,
                    SoundEvents.ENTITY_PIG_HURT,
                    SoundEvents.ENTITY_PIG_SADDLE,
                    SoundEvents.ENTITY_PIG_STEP},
            {SoundEvents.ENTITY_POLAR_BEAR_BABY_AMBIENT,
                    SoundEvents.ENTITY_POLAR_BEAR_DEATH,
                    SoundEvents.ENTITY_POLAR_BEAR_HURT,
                    SoundEvents.ENTITY_POLAR_BEAR_STEP,
                    SoundEvents.ENTITY_POLAR_BEAR_WARNING},
            {SoundEvents.ENTITY_RABBIT_DEATH,
                    SoundEvents.ENTITY_RABBIT_ATTACK,
                    SoundEvents.ENTITY_RABBIT_HURT,
                    SoundEvents.ENTITY_RABBIT_JUMP},
            {SoundEvents.ENTITY_SHEEP_DEATH,
                    SoundEvents.ENTITY_SHEEP_HURT,
                    SoundEvents.ENTITY_SHEEP_SHEAR,
                    SoundEvents.ENTITY_SHEEP_STEP},
            {SoundEvents.ENTITY_SILVERFISH_DEATH,
                    SoundEvents.ENTITY_SILVERFISH_HURT,
                    SoundEvents.ENTITY_SILVERFISH_STEP},
            {SoundEvents.ENTITY_SKELETON_DEATH,
                    SoundEvents.ENTITY_SKELETON_HURT,
                    SoundEvents.ENTITY_SKELETON_SHOOT,
                    SoundEvents.ENTITY_SKELETON_STEP},
            {SoundEvents.ENTITY_SMALL_MAGMACUBE_DEATH,
                    SoundEvents.ENTITY_SMALL_MAGMACUBE_HURT,
                    SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH},
            {SoundEvents.ENTITY_SMALL_SLIME_DEATH,
                    SoundEvents.ENTITY_SMALL_SLIME_HURT,
                    SoundEvents.ENTITY_SMALL_SLIME_JUMP,
                    SoundEvents.ENTITY_SMALL_SLIME_SQUISH},
            {SoundEvents.ENTITY_SNOWMAN_DEATH,
                    SoundEvents.ENTITY_SNOWMAN_HURT,
                    SoundEvents.ENTITY_SNOWMAN_SHOOT},
            {SoundEvents.ENTITY_SPIDER_DEATH,
                    SoundEvents.ENTITY_SPIDER_HURT,
                    SoundEvents.ENTITY_SPIDER_STEP},
            {SoundEvents.ENTITY_SQUID_DEATH,
                    SoundEvents.ENTITY_SQUID_HURT},
            {SoundEvents.ENTITY_STRAY_DEATH,
                    SoundEvents.ENTITY_STRAY_HURT,
                    SoundEvents.ENTITY_STRAY_STEP},
            {SoundEvents.ENTITY_VEX_CHARGE,
                    SoundEvents.ENTITY_VEX_DEATH,
                    SoundEvents.ENTITY_VEX_HURT},
            {SoundEvents.ENTITY_VILLAGER_DEATH,
                    SoundEvents.ENTITY_VILLAGER_HURT,
                    SoundEvents.ENTITY_VILLAGER_NO,
                    SoundEvents.ENTITY_VILLAGER_TRADING,
                    SoundEvents.ENTITY_VILLAGER_YES},
            {SoundEvents.ENTITY_WITCH_DEATH,
                    SoundEvents.ENTITY_WITCH_DRINK,
                    SoundEvents.ENTITY_WITCH_HURT,
                    SoundEvents.ENTITY_WITCH_THROW},
            {SoundEvents.ENTITY_WITHER_DEATH,
                    SoundEvents.ENTITY_WITHER_BREAK_BLOCK,
                    SoundEvents.ENTITY_WITHER_HURT,
                    SoundEvents.ENTITY_WITHER_SHOOT,
                    SoundEvents.ENTITY_WITHER_SPAWN},
            {SoundEvents.ENTITY_WITHER_SKELETON_DEATH,
                    SoundEvents.ENTITY_WITHER_SKELETON_HURT,
                    SoundEvents.ENTITY_WITHER_SKELETON_STEP},
            {SoundEvents.ENTITY_WOLF_DEATH,
                    SoundEvents.ENTITY_WOLF_GROWL,
                    SoundEvents.ENTITY_WOLF_HOWL,
                    SoundEvents.ENTITY_WOLF_HURT,
                    SoundEvents.ENTITY_WOLF_PANT,
                    SoundEvents.ENTITY_WOLF_SHAKE,
                    SoundEvents.ENTITY_WOLF_STEP,
                    SoundEvents.ENTITY_WOLF_WHINE},
            {SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD,
                    SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,
                    SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD,
                    SoundEvents.ENTITY_ZOMBIE_DEATH,
                    SoundEvents.ENTITY_ZOMBIE_HORSE_AMBIENT,
                    SoundEvents.ENTITY_ZOMBIE_HORSE_DEATH,
                    SoundEvents.ENTITY_ZOMBIE_HORSE_HURT,
                    SoundEvents.ENTITY_ZOMBIE_HURT,
                    SoundEvents.ENTITY_ZOMBIE_INFECT},
            {SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY,
                    SoundEvents.ENTITY_ZOMBIE_PIG_DEATH,
                    SoundEvents.ENTITY_ZOMBIE_PIG_HURT,
                    SoundEvents.ENTITY_ZOMBIE_STEP},
            {SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED,
                    SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE,
                    SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH,
                    SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT,
                    SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP}
    };

    private int soundSetIndex;
    private int nextSoundDelay;

    public EntitySpookyArrow(World worldIn) {
        super(worldIn);
    }

    public EntitySpookyArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        soundSetIndex = -1;
        nextSoundDelay = 0;
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(SOUND_SET_INDEX, -1);

        setDespawnDelay(ConfigArrow.spookyArrow.despawnDelay);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote)
            return;
        if (!inGround)
            return;
        if (nextSoundDelay-- > 0)
            return;

        if (soundSetIndex == -1) {
            soundSetIndex = UtilRandom.INSTANCE.getRandomIndex(SPOOKY_SOUNDS);
            dataManager.set(SOUND_SET_INDEX, soundSetIndex);
        }

        nextSoundDelay = ConfigArrow.spookyArrow.soundDelay.random();
        SoundEvent soundEvent = UtilRandom.INSTANCE.getRandom(SPOOKY_SOUNDS[soundSetIndex]);
        if (soundEvent != null)
            world.playSound(null, posX, posY, posZ, soundEvent, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (SOUND_SET_INDEX.equals(key))
            soundSetIndex = dataManager.get(SOUND_SET_INDEX);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(TAG_SOUND_SET, soundSetIndex);
        compound.setInteger(TAG_NEXT_SOUND_DELAY, nextSoundDelay);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        setSound(compound.getInteger(TAG_SOUND_SET));
        nextSoundDelay = compound.getInteger(TAG_NEXT_SOUND_DELAY);
    }

    private void setSound(int soundSet) {
        this.soundSetIndex = soundSet;
        dataManager.set(SOUND_SET_INDEX, soundSet);
    }
}