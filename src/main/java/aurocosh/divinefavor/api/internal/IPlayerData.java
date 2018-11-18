package aurocosh.divinefavor.api.internal;

public interface IPlayerData {

    /**
     * Gets the player's level. May be 0.
     */
    public int getLevel();

    /**
     * Gets the total amount of psi energy the player has.
     * Does not include the amount in the player's CAD battery.
     */
    public int getTotalPsi();

    /**
     * Gets the current amount of psi energy the player has.
     * Does not include the amount in the player's CAD battery.
     */
    public int getAvailablePsi();

    /**
     * Gets the current amount of psi energy the player had last tick.
     * Not guaranteed to synchronise properly or be 100% accurate. Use with caution.
     * Does not include the amount in the player's CAD battery.
     */
    public int getLastAvailablePsi();

    /**
     * Gets the cooldown before the player can start regenerating psi energy.
     */
    public int getRegenCooldown();

    /**
     * Gets how much psi the player regenerates per tick.
     */
    public int getRegenPerTick();

    /**
     * Deducts the amount of psi given from the player's psi energy.
     * This will not check against the available amount. Any extra will be either
     * deducted from the player's CAD battery, or deducted as damage.
     */
    public void deductPsi(int psi, int cd, boolean sync, boolean shatter);

    /**
     * Gets it the piece group name is unlocked.
     */
    public boolean isPieceGroupUnlocked(String group);

    /**
     * Unlocks the given piece group.
     */
    public void unlockPieceGroup(String group);

    /**
     * Marks a spell piece as executed. Used for leveling.
     */
    //public void markPieceExecuted(SpellPiece piece);

}