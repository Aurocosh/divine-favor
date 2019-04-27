package aurocosh.divinefavor.common.item.talismans.spell.sense;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.SenseConfig;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightEntities;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public class SpellTalismanSenseEntities extends ItemSpellTalisman {
    protected final int radius;
    protected final float minShift;
    protected final float maxShift;
    protected final Color3f color3f;
    protected final SenseEntitiesPredicate predicate;

    public SpellTalismanSenseEntities(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseConfig senseConfig, SenseEntitiesPredicate predicate) {
        super(name, "sense/", spirit, senseConfig.favorCost, options);
        this.color3f = color3f;
        this.predicate = predicate;
        minShift = senseConfig.minShift;
        maxShift = senseConfig.maxShift;
        this.radius = senseConfig.radius;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();

        World world = player.world;
        new MessageParticlesHighlightEntities(50, radius, world.provider.getDimension(), context.pos, minShift, maxShift, color3f, predicate).sendToAllAround(world, playerPosition, ConfigGeneral.particleRadius);
    }
}
