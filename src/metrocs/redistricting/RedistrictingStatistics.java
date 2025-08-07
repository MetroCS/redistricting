package metrocs.redistricting;
import java.util.Set;
import java.util.Map;
import java.util.EnumMap;
/**
 * Utility methods for computing party preference statistics of districts.
 *
 * <p>The statistics indicate which party holds a plurality of voters in each
 * district. If multiple parties share the largest number of voters in a
 * district, that district is considered to have no preference and is counted
 * under {@code Party.NONE}.</p>
 *
 * @author OpenAI ChatGPT
 * @version 20240528.0
 */
public final class RedistrictingStatistics implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 1L;

    /**
     * Private constructor to prevent instantiation.
     */
    private RedistrictingStatistics() { }

    /**
     * Determines the preferred party for a district.
     *
     * <p>The preferred party is the one with the most voters in the district.
     * If no single party has a plurality, {@code Party.NONE} is returned.</p>
     *
     * @param district the district to analyze
     * @param region the region containing the voters
     * @return the party that holds the plurality of voters; {@code Party.NONE}
     *         if there is no single-party plurality
     * @throws IllegalArgumentException if any parameter is {@code null}
     */
    public static Party districtPreference(final District district,
                                           final Region region) {
        if (district == null || region == null) {
            throw new IllegalArgumentException("Null parameter provided");
        }
        Map<Party, Integer> counts = new EnumMap<Party, Integer>(Party.class);
        for (Party p : Party.values()) {
            counts.put(p, 0);
        }
        for (Voter v : region.voters()) {
            if (district.locations().contains(v.location())) {
                Party party = v.affiliation();
                counts.put(party, counts.get(party) + 1);
            }
        }
        Party preferred = Party.NONE;
        int max = -1;
        boolean unique = false;
        for (Map.Entry<Party, Integer> e : counts.entrySet()) {
            int c = e.getValue();
            if (c > max) {
                max = c;
                preferred = e.getKey();
                unique = true;
            } else if (c == max) {
                unique = false;
            }
        }
        if (!unique || max == 0) {
            return Party.NONE;
        }
        return preferred;
    }

    /**
     * Computes party preference statistics for a redistricting.
     *
     * @param districts the set of districts comprising the redistricting
     * @param region the region containing the voters
     * @return a mapping from party to the number of districts preferring that
     *         party; {@code Party.NONE} counts districts with no preference
     * @throws IllegalArgumentException if any parameter is {@code null}
     */
    public static Map<Party, Integer> computePartyPreferences(
            final Set<District> districts, final Region region) {
        if (districts == null || region == null) {
            throw new IllegalArgumentException("Null parameter provided");
        }
        Map<Party, Integer> result = new EnumMap<Party, Integer>(Party.class);
        for (Party p : Party.values()) {
            result.put(p, 0);
        }
        for (District d : districts) {
            Party p = districtPreference(d, region);
            result.put(p, result.get(p) + 1);
        }
        return result;
    }


    /**
     * Counts the number of voters in the specified region that are affiliated
     * with each {@link Party}.
     *
     * @param region the region to analyze; may be {@code null}
     * @return map associating each party with the number of voters affiliated
     *         with that party
     */
    public static Map<Party, Integer> countPartyPreferences(final Region region) {
        Map<Party, Integer> counts = new EnumMap<>(Party.class);
        for (Party p : Party.values()) {
            counts.put(p, 0);
        }
        if (region != null) {
            for (Voter v : region.voters()) {
                Party p = v.affiliation();
                counts.put(p, counts.get(p) + 1);
            }
        }
        return counts;
    }


    /**
     * Produces a human-readable summary of party preference statistics for the
     * given region. The summary lists the number and percentage of voters for
     * each party.
     *
     * @param region the region to analyze; may be {@code null}
     * @return summary string describing party preferences
     */
    public static String formatPartyPreferences(final Region region) {
        Map<Party, Integer> counts = countPartyPreferences(region);
        int total = (region == null) ? 0 : region.numberOfVoters();
        StringBuilder sb = new StringBuilder();
        Party[] order = {Party.PARTY0, Party.PARTY1, Party.THIRDPARTY,
                         Party.UNAFFILIATED, Party.NONE};
        for (Party p : order) {
            int c = counts.get(p);
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            if (total > 0) {
                double pct = (double) c * 100.0 / total;
                sb.append(p.name()).append(": ").append(c)
                  .append(String.format(" (%.1f%%)", pct));
            } else {
                sb.append(p.name()).append(": ").append(c);
            }
        }
        return sb.toString();
    }
}
