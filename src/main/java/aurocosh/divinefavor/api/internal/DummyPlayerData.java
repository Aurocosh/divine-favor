package aurocosh.divinefavor.api.internal;

public class DummyPlayerData implements IPlayerData {

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getTotalPsi() {
        return 0;
    }

    @Override
    public int getAvailablePsi() {
        return 0;
    }

    @Override
    public int getLastAvailablePsi() {
        return 0;
    }

    @Override
    public int getRegenCooldown() {
        return 0;
    }

    @Override
    public int getRegenPerTick() {
        return 0;
    }

    @Override
    public void deductPsi(int psi, int cd, boolean sync, boolean shatter) {
        // NO-OP
    }

    @Override
    public boolean isPieceGroupUnlocked(String group) {
        return false;
    }

    @Override
    public void unlockPieceGroup(String group) {
        // NO-OP
    }

    /*@Override
    public void markPieceExecuted(SpellPiece piece) {
        // NO-OP
    }*/
}