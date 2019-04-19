package aurocosh.divinefavor.common.item.talismans.arrow.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class ItemArrowTalisman extends ItemTalisman {
    private final int color;
    private final double arrowDamage;
    private final ArrowType arrowType;
    private final EnumSet<ArrowOptions> options;
    protected GravityType gravityType;

    public ItemArrowTalisman(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super("arrow_talisman_" + name, "arrow_talismans/" + name, spirit, favorCost);
        this.color = color;
        this.arrowDamage = arrowDamage;
        this.options = options;
        this.arrowType = arrowType;
        gravityType = GravityType.NORMAL;

//        setMaxStackSize(64);
        setCreativeTab(DivineFavor.TAB_ARROW_TALISMANS);
    }

    public int getColor() {
        return color;
    }

    public double getArrowDamage() {
        return arrowDamage;
    }

    public boolean isBreakOnHit() {
        return options.contains(ArrowOptions.BreakOnHit);
    }

    public ArrowType getArrowType() {
        return arrowType;
    }

    public EnumSet<ArrowOptions> getOptions() {
        return options;
    }

    public GravityType getGravityType() {
        return gravityType;
    }

    public boolean claimCost(@Nonnull World world, EntityLivingBase shooter) {
        if (!(shooter instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) shooter;
        SpiritData spiritData = PlayerData.get(player).getSpiritData();
        if (!spiritData.consumeFavor(spirit.getId(), favorCost))
            return false;
        if (world.isRemote)
            return true;

        new MessageSyncFavor(spirit, spiritData).sendTo(player);
        return true;
    }
// Talisman functions

    public EntityArrow createArrow(@Nonnull World world, @Nonnull ItemArrowTalisman talisman, EntityPlayer player) {
        EntitySpellArrow spellArrow = getArrow(world, player);
        spellArrow.setSpell(talisman, player);
        init(spellArrow, player);
        spellArrow.setNoGravity(gravityType == GravityType.NO_GRAVITY || gravityType == GravityType.ANTIGRAVITY);
        spellArrow.setHasAntiGravity(gravityType == GravityType.ANTIGRAVITY);
        spellArrow.setDamage(arrowDamage);
        return spellArrow;
    }

    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        return new EntitySpellArrow(world, shooter);
    }

    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
    }

    public boolean cast(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (options.contains(ArrowOptions.RequiresTarget) && target == null)
            return true;
        if (spellArrow.world.isRemote)
            return performActionClient(target, shooter, spellArrow, blockPos, sideHit);
        else
            return performActionServer(target, shooter, spellArrow, blockPos, sideHit);
    }

    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        return true;
    }

    protected boolean performActionClient(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        return true;
    }

    public void onUpdate(EntitySpellArrow spellArrow) {
    }

    public boolean onCollideWithPlayer(EntitySpellArrow spellArrow, EntityPlayer player) {
        return true;
    }

    public void postInit(EntityArrow spellArrow) {
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow arrow) {
    }
}
