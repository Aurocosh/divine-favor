package aurocosh.divinefavor.common.custom_data.world.data.altars;

import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.util.math.BlockPos;

import java.util.*;

// The default implementation of the capability. Holds all the logic.
public class AltarsData {
    private Map<Integer, Set<BlockPos>> positions;
    private Map<BlockPos, Integer> positionsInverse;

    public AltarsData() {
        positions = new HashMap<>();
        positionsInverse = new HashMap<>();
    }

    public void addAltarLocation(ModSpirit spirit, BlockPos pos) {
        if(positionsInverse.containsKey(pos))
            removeAltarLocation(spirit, pos);
        Set<BlockPos> altars = getAltars(spirit.getId());
        positionsInverse.put(pos, spirit.getId());
        altars.add(pos);
    }

    public void removeAltarLocation(BlockPos pos) {
        if(!positionsInverse.containsKey(pos))
            return;
        int spiritId = positionsInverse.get(pos);
        ModSpirit spirit = ModMappers.spirits.get(spiritId);
        removeAltarLocation(spirit, pos);
    }

    public void removeAltarLocation(ModSpirit spirit, BlockPos pos) {
        Set<BlockPos> altars = getAltars(spirit.getId());
        positionsInverse.remove(pos);
        altars.remove(pos);
    }

    private Set<BlockPos> getAltars(int spiritId) {
        return positions.computeIfAbsent(spiritId, integer -> new HashSet<>());
    }

    public Set<BlockPos> getAltarLocations(ModSpirit spirit) {
        return Collections.unmodifiableSet(getAltars(spirit.getId()));
    }

    public Map<Integer, Set<BlockPos>> getAllPositions() {
        return new HashMap<>(positions);
    }

    public void setAllPositions(Map<Integer, Set<BlockPos>> positions) {
        this.positions.clear();
        this.positions.putAll(positions);
    }
}
