package aurocosh.divinefavor.common.item.bone_dagger;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigItem;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftData;
import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.soul_shards.ItemSoulShard;
import aurocosh.divinefavor.common.item.soul_shards.ItemSoulShardPlayer;
import aurocosh.divinefavor.common.item.soul_shards.ModSoulShards;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class ItemBoneDaggerAwakened extends ModItem {
    private static final String TAG_SOUL_STEAL_CHANCE = "SOUL_STEAL_CHANCE";
    private static final Set<Class<? extends EntityLiving>> witherClasses = new HashSet<>();
    private static final Set<Class<? extends EntityLiving>> hellClasses = new HashSet<>();

    static {
        witherClasses.add(EntityWither.class);
        witherClasses.add(EntityWitherSkeleton.class);

        ResourceLocation hell = new ResourceLocation("hell");
        for (EnumCreatureType creatureType : EnumCreatureType.values())
            for (Biome.SpawnListEntry spawnListEntry : Biome.REGISTRY.getObject(hell).getSpawnableList(creatureType))
                hellClasses.add(spawnListEntry.entityClass);
    }

    public ItemBoneDaggerAwakened() {
        super("bone_dagger_awakened", "bone_dagger_awakened", ConstMainTabOrder.DAGGERS);
        setMaxStackSize(1);
        setContainerItem(this);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote || !player.isSneaking())
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        makeSoulShard(player, player);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (player.world.isRemote)
            return false;
        if (!(entity instanceof EntityLivingBase))
            return false;

        NBTTagCompound nbt = UtilNbt.getNbt(stack);
        float chance = nbt.getFloat(TAG_SOUL_STEAL_CHANCE);
        if (UtilRandom.rollDiceFloat(chance)) {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            livingBase.addPotionEffect(new ModEffect(ModCurses.soul_theft, ConfigItem.awakenedBoneDagger.soulTheftDuration).setIsCurse());
            SoulTheftData theftData = LivingData.get(livingBase).getSoulTheftData();
            theftData.addThief(player);
            makeSoulShard(livingBase, player);

            nbt.setFloat(TAG_SOUL_STEAL_CHANCE, 0);
        }
        else
            nbt.setFloat(TAG_SOUL_STEAL_CHANCE, chance + ConfigItem.awakenedBoneDagger.soulSteelingSpeed);
        return false;
    }

    private void makeSoulShard(EntityLivingBase victim, EntityPlayer user) {
        ItemSoulShard item;
        if (victim instanceof EntityPlayer)
            item = ModSoulShards.shard_will;
        else if (victim instanceof EntityWitherSkeleton || victim instanceof EntityWither)
            item = ModSoulShards.shard_wither;
        else if (victim instanceof EntityEnderman || victim instanceof EntityEndermite || victim instanceof EntityShulker || victim instanceof EntityDragon)
            item = ModSoulShards.shard_end;
        else if (victim instanceof EntityVillager)
            item = ModSoulShards.shard_mind;
        else if (victim.isEntityUndead())
            item = ModSoulShards.shard_undeath;
        else if (hellClasses.contains(victim.getClass()))
            item = ModSoulShards.shard_nether;
        else if (victim instanceof EntityWaterMob)
            item = ModSoulShards.shard_water;
        else if (victim instanceof EntityAnimal)
            item = ModSoulShards.shard_peace;
        else
            item = ModSoulShards.shard_wild;

        ItemStack stack = new ItemStack(item);
        ItemSoulShardPlayer.setOwner(stack, victim);
        user.attackEntityFrom(ModDamageSources.divineDamage, 0.5f);
        user.addItemStackToInventory(stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
