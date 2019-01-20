package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.ITalismanUsesHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

public class ItemArrowTalisman extends ItemTalisman {
    private final int color;
    private final double arrowDamage;
    private final boolean breakOnHit;
    private final boolean requiresTarget;
    private final ArrowType arrowType;
    protected GravityType gravityType;


    public ItemArrowTalisman(String name, int startingSpellUses, int color, double arrowDamage, boolean breakOnHit, boolean requiresTarget, ArrowType arrowType) {
        super("arrow_talisman_" + name, "arrow_talismans/" + name, startingSpellUses);
        this.color = color;
        this.arrowDamage = arrowDamage;
        this.breakOnHit = breakOnHit;
        this.requiresTarget = requiresTarget;
        this.arrowType = arrowType;
        gravityType = GravityType.NORMAL;

//        setMaxStackSize(64);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public int getColor() {
        return color;
    }

    public boolean isBreakOnHit() {
        return breakOnHit;
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
        ITalismanUsesHandler usesHandler = player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;

        if (!usesHandler.consumeUse(id))
            return false;
        if (world.isRemote)
            return true;

        int usesLeft = usesHandler.getUses(id);
        new MessageSyncSpellUses(id, usesLeft).sendTo(player);
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
        if(requiresTarget && target == null)
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
