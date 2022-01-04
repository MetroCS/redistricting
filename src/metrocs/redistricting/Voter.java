package metrocs.redistricting;
/**
 * A voter has a party association and a location.
 *
 * @author Dr. Jody Paul
 * @version 20220102.0
 */
public class Voter implements Comparable<Voter>, java.io.Serializable {
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

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Voter)) {
            return false;
        }
        Voter v = (Voter) o;
        return (this.partyAffiliation.equals(v.partyAffiliation)
                && this.myLocation.equals(v.myLocation));
    }

    @Override
    public int hashCode() {
        return this.partyAffiliation.hashCode() + this.myLocation.hashCode();
    }

    @Override
    public int compareTo(final Voter v) {
        if (v == null) {
            throw new NullPointerException(
                          "Attempt to compare Voter with null");
        }
        if (this.equals(v)) {
            return 0;
        }
        if (!this.myLocation.equals(v.myLocation)) {
            return this.myLocation.compareTo(v.myLocation);
        }
        return this.partyAffiliation.compareTo(v.partyAffiliation);
    }
}
