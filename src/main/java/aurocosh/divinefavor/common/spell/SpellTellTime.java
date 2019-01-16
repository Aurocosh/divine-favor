package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.text.TextComponentString;

public class SpellTellTime extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
    }

    @Override
    protected void performActionClient(SpellContext context) {
        long time = context.world.getWorldTime();
        context.player.sendMessage(new TextComponentString("Time: " + time));
        context.player.sendMessage(new TextComponentString("Day time: " + time % 24000));
    }
}
