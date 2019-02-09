package aurocosh.divinefavor.common.custom_data.living.data.soul_theft;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SoulTheftData {
    private final HashSet<UUID> thiefUUIDS;

    public SoulTheftData() {
        thiefUUIDS = new HashSet<>();
    }

    public void reset() {
        thiefUUIDS.clear();
    }

    public boolean isThief(EntityPlayer player) {
        UUID uuid = player.getGameProfile().getId();
        return thiefUUIDS.contains(uuid);
    }

    public void addThief(EntityPlayer player) {
        UUID uuid = player.getGameProfile().getId();
        thiefUUIDS.add(uuid);
    }

    public List<UUID> getUUIDS() {
        return new ArrayList<>(thiefUUIDS);
    }

    public void settUUIDS(List<UUID> uuidList) {
        thiefUUIDS.clear();
        thiefUUIDS.addAll(uuidList);
    }
}
