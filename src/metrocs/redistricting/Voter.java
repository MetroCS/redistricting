package metrocs.redistricting;
/**
 * A voter has a party association and a location.
 *
 * @author Dr. Jody Paul
 * @version 20191006.1
 */
public class Voter implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 3L;

    /** This voter's party affiliation. */
    private Party partyAffiliation;
    /** This voter's location. */
    private Location myLocation;

    /**
     * Creates voter with specified affiliation and location.
     * @param affiliation the party affiliation; substitutes Party.NONE if null
     * @param location the location; throws exception if null
     */
    public Voter(final Party affiliation, final Location location) {
        if (null == location) {
            throw new IllegalArgumentException(
                        "Invalid voter location: " + location);
        } else {
            this.myLocation = location;
        }
        if (null == affiliation) {
            this.partyAffiliation = Party.NONE;
        } else {
            this.partyAffiliation = affiliation;
        }
    }

    /**
     * Accesses this voter's party affiliation.
     * @return the party of this voter
     */
    public Party affiliation() {
        return this.partyAffiliation;
    }

    /**
     * Accesses this voter's location.
     * @return the location of this voter
     */
    public Location location() {
        return this.myLocation;
    }
}
