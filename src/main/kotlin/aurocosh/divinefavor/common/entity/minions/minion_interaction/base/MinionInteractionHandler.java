package aurocosh.divinefavor.common.entity.minions.minion_interaction.base;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionInteraction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinionInteractionHandler<T extends EntityLiving & IMinion> {
    private final List<MinionInteraction<T>> interactions;
    private final List<MinionInteraction<T>> ownerInteractions;

    public MinionInteractionHandler() {
        this.interactions = new ArrayList<>();
        ownerInteractions = new ArrayList<>();
    }

    public void addInteraction(MinionInteraction<T> interaction) {
        interactions.add(interaction);
    }

    public void addOwnerInteraction(MinionInteraction<T> interaction) {
        ownerInteractions.add(interaction);
    }

    public boolean processInteract(T minion, EntityPlayer player, EnumHand hand) {
        if (player.world.isRemote)
            return false;

        UUID uuid = minion.getMinionData().getOwnerUUID();
        if (player.getGameProfile().getId().equals(uuid))
            for (MinionInteraction<T> interaction : ownerInteractions)
                if (interaction.process(minion, player, hand))
                    return true;

        for (MinionInteraction<T> interaction : interactions)
            if (interaction.process(minion, player, hand))
                return true;
        return false;
    }
}
