package aurocosh.divinefavor.common.player_data.pearl_crumbs;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;

import java.util.*;

// The default implementation of the capability. Holds all the logic.
public class DefaultPearlCrumbsHandler implements IPearlCrumbsHandler {
    private Stack<GlobalBlockPos> positions;

    public DefaultPearlCrumbsHandler() {
        positions = new Stack<>();
    }

    @Override
    public GlobalBlockPos popGlobalPosition() {
        return positions.pop();
    }

    @Override
    public void pushGlobalPosition(GlobalBlockPos pos) {
        positions.push(pos);
    }

    @Override
    public boolean hasPositions() {
        return !positions.isEmpty();
    }

    @Override
    public List<GlobalBlockPos> getAllPositions() {
        return new ArrayList<>(positions);
    }

    @Override
    public void setAllPositions(List<GlobalBlockPos> positions) {
        this.positions.clear();
        this.positions.addAll(positions);
    }
}
