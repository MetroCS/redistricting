package metrocs.redistricting;
/**
 * Enumeration class Party
 * specifies constants for all known parties
 * as well as for unaffiliated and none (unknown).
 *
 * @author Dr. Jody Paul
 * @version 20240807.1
 */
public enum Party implements java.io.Serializable {
    /** Major Party 0. */
    PARTY0('0'),
    /** Major Party 1. */
    PARTY1('1'),
    /** Unaffiliated voter. */
    UNAFFILIATED('U'),
    /** Any non-major party. */
    THIRDPARTY('T'),
    /** Unrecognized party. */
    NONE('?');

    /** The single-character party affiliation identification. */
    private final char id;

    /**
     * Constructs a party with the given ID.
     * @param displayID the affiliation display character
     */
    Party(final char displayID) {
        this.id = displayID;
    }

    /**
     * Accesses the display character for this party.
     * @return the display identification
     */
    public char id() {
        return this.id;
    }
}
