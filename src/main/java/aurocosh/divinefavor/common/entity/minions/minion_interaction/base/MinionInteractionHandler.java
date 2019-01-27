package aurocosh.divinefavor.common.entity.minions.minion_interaction.base;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionInteraction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.List;

public class MinionInteractionHandler<T extends EntityLiving & IMinion> {
    private final List<MinionInteraction<T>> interactions;

    public MinionInteractionHandler() {
        this.interactions = new ArrayList<>();
    }

    public void addInteraction(MinionInteraction<T> interaction) {
        interactions.add(interaction);
    }

    public boolean processInteract(T minion, EntityPlayer player, EnumHand hand) {
        if (player.world.isRemote)
            return false;
        for (MinionInteraction<T> interaction : interactions)
            if (interaction.process(minion, player, hand))
                return true;
        return false;
    }
}
