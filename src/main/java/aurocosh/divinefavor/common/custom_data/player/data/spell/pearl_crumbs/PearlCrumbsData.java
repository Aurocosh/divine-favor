package aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.lib.GlobalBlockPos;

import java.util.*;

// The default implementation of the capability. Holds all the logic.
public class PearlCrumbsData {
    private Deque<GlobalBlockPos> positions;

    public PearlCrumbsData() {
        positions = new ArrayDeque<>();
    }

    public GlobalBlockPos popGlobalPosition() {
        return positions.pop();
    }

    public void pushGlobalPosition(GlobalBlockPos pos) {
        positions.push(pos);
        if(positions.size() > ConfigSpells.pearlCrumbs.maxPositionsSaved)
            positions.removeLast();
    }

    public boolean hasPositions() {
        return !positions.isEmpty();
    }

    public List<GlobalBlockPos> getAllPositions() {
        return new ArrayList<>(positions);
    }

    public void setAllPositions(List<GlobalBlockPos> positions) {
        this.positions.clear();
        this.positions.addAll(positions);
    }
}
