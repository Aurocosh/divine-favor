package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.entries.talismans.spell.SenseBlock;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightOre;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public class SpellTalismanHighlightOre extends ItemSpellTalisman {
    private final int radius;
    private final float minShift;
    private final float maxShift;
    private final Color3f color3f;

    public SpellTalismanHighlightOre(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseBlock senseBlock) {
        super(name, spirit, senseBlock.favorCost, options);
        this.color3f = color3f;
        radius = senseBlock.radius;
        minShift = senseBlock.minShift;
        maxShift = senseBlock.maxShift;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();

        new MessageParticlesHighlightOre(radius, 5, player.world.provider.getDimension(), playerPosition, minShift, maxShift, color3f)
                .sendToAllAround(world, playerPosition, ConfigGeneral.particleRadius);
    }
}
