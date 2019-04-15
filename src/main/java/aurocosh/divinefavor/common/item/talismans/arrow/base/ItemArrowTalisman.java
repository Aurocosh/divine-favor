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
import net.minecraft.world.World;

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


    public EntityArrow createArrow(@Nonnull World world, @Nonnull ItemArrowTalisman talisman, EntityLivingBase shooter) {
        EntitySpellArrow arrow = getArrow(world, shooter);
        arrow.setSpell(talisman, shooter);
        arrow.setNoGravity(gravityType == GravityType.NO_GRAVITY || gravityType == GravityType.ANTIGRAVITY);
        arrow.setHasAntiGravity(gravityType == GravityType.ANTIGRAVITY);
        arrow.setDamage(arrowDamage);
        return arrow;
    }

    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        // TODO
        return new EntitySpellArrow(world, shooter);
    }

    public void cast(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (options.contains(ArrowOptions.RequiresTarget) && target == null)
            return;
        if (arrow.world.isRemote)
            performActionClient(target, shooter, arrow);
        else
            performActionServer(target, shooter, arrow);
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
    }

    protected void performActionClient(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
    }
}
