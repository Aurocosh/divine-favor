package aurocosh.divinefavor.common.item.talismans.arrow.base;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTabArrowTalismans;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.FavorData;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
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

    public ItemArrowTalisman(String name, ModFavor favor, int favorCost, int color, double arrowDamage,  EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super("arrow_talisman_" + name, "arrow_talismans/" + name, favor, favorCost);
        this.color = color;
        this.arrowDamage = arrowDamage;
        this.options = options;
        this.arrowType = arrowType;
        gravityType = GravityType.NORMAL;

//        setMaxStackSize(64);
        setCreativeTab(DivineFavorCreativeTabArrowTalismans.INSTANCE);
    }

    public int getColor() {
        return color;
    }

    public boolean isBreakOnHit() {
        return options.contains(ArrowOptions.BreakOnHit);
    }

    public ArrowType getArrowType() {
        return arrowType;
    }

    public GravityType getGravityType() {
        return gravityType;
    }

    public boolean claimCost(@Nonnull World world, EntityLivingBase shooter) {
        if (!(shooter instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) shooter;
        FavorData usesData = PlayerData.get(player).getFavorData();
        if (!usesData.consumeFavor(getFavorId()))
            return false;
        if (world.isRemote)
            return true;

        new MessageSyncSpellUses(getFavorId(), usesData).sendTo(player);
        return true;
    }
// Talisman functions


    public EntityArrow createArrow(@Nonnull World world, @Nonnull ItemArrowTalisman talisman, EntityLivingBase shooter) {
        EntitySpellArrow arrow = getArrowByType(world, shooter);
        arrow.setSpell(talisman, shooter);
        arrow.setNoGravity(gravityType == GravityType.NO_GRAVITY || gravityType == GravityType.ANTIGRAVITY);
        arrow.setHasAntiGravity(gravityType == GravityType.ANTIGRAVITY);
        arrow.setDamage(arrowDamage);
        return arrow;
    }

    private EntitySpellArrow getArrowByType(@Nonnull World worldIn, EntityLivingBase shooter){
        // TODO
        return new EntitySpellArrow(worldIn, shooter);
    }

    public void cast(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if(options.contains(ArrowOptions.RequiresTarget) && target == null)
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
