package swdmt.redistricting;
/**
 * Enumeration class PARTY
 * specifies constants for all known parties
 * as well as for unaffiliated and none (unknown).
 *
 * @author Dr. Jody Paul
 * @version 20191006
 */
public enum Party implements java.io.Serializable {
    /** Unrecognized party. */
    NONE,
    /** Unaffiliated voter. */
    UNAFFILIATED,
    /** Major Party 0. */
    PARTY0,
    /** Major Party 1. */
    PARTY1,
    /** Any non-major party. */
    THIRDPARTY;

    /** Serialization version requirement. */
    private static final long serialVersionUID = 3L;

}
