package metrocs.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import org.junit.jupiter.api.Test;
/**
 * Tests for {@link RedistrictingStatistics} utility.
 *
 * @version 20250808.1
 */
public class RedistrictingStatisticsTest {
    /**
     * Verify district preference counts across multiple districts.
     */
    @Test
    public void verifyRedistrictingPreferences() {
        Set<Voter> voters = new HashSet<Voter>();
        voters.add(new Voter(Party.PARTY0, new Location(0, 0)));
        voters.add(new Voter(Party.PARTY0, new Location(0, 1)));
        voters.add(new Voter(Party.PARTY1, new Location(1, 0)));
        voters.add(new Voter(Party.PARTY1, new Location(1, 1)));
        voters.add(new Voter(Party.PARTY0, new Location(2, 0)));
        voters.add(new Voter(Party.PARTY1, new Location(2, 1)));
        Region region = new Region(voters);
        Set<District> districts = new HashSet<District>();
        Set<Location> locs1 = new HashSet<Location>();
        locs1.add(new Location(0, 0));
        locs1.add(new Location(0, 1));
        districts.add(new District(locs1));
        Set<Location> locs2 = new HashSet<Location>();
        locs2.add(new Location(1, 0));
        locs2.add(new Location(1, 1));
        districts.add(new District(locs2));
        Set<Location> locs3 = new HashSet<Location>();
        locs3.add(new Location(2, 0));
        locs3.add(new Location(2, 1));
        districts.add(new District(locs3));
        Map<Party, Integer> counts =
            RedistrictingStatistics.computePartyPreferences(districts, region);
        assertThat(counts.get(Party.PARTY0), is(1));
        assertThat(counts.get(Party.PARTY1), is(1));
        assertThat(counts.get(Party.NONE), is(1));
    }

    /**
     * Verify that a tie within a district results in no preference.
     */
    @Test
    public void verifyTieGivesNoPreference() {
        Set<Voter> voters = new HashSet<Voter>();
        voters.add(new Voter(Party.PARTY0, new Location(0, 0)));
        voters.add(new Voter(Party.PARTY1, new Location(0, 1)));
        Region region = new Region(voters);
        Set<Location> dlocs = new HashSet<Location>();
        dlocs.add(new Location(0, 0));
        dlocs.add(new Location(0, 1));
        District district = new District(dlocs);
        assertThat(RedistrictingStatistics.districtPreference(district, region),
            is(Party.NONE));
    }

    /**
     * Verify that the party preference counts reflect the voters in the region.
     */
    @Test
    public void countsRegionPartiesCorrectly() {
        Set<Location> locs = new HashSet<>();
        Set<Voter> voters = new HashSet<>();
        Location l1 = new Location(0, 0);
        Location l2 = new Location(0, 1);
        Location l3 = new Location(1, 0);
        locs.add(l1);
        locs.add(l2);
        locs.add(l3);
        voters.add(new Voter(Party.PARTY0, l1));
        voters.add(new Voter(Party.PARTY0, l2));
        voters.add(new Voter(Party.PARTY1, l3));
        Region region = new Region(locs, voters);
        Set<District> districts = new HashSet<District>();
        Set<Location> locs1 = new HashSet<Location>();
        locs1.add(new Location(0, 0));
        locs1.add(new Location(0, 1));
        districts.add(new District(locs1));
        Set<Location> locs2 = new HashSet<Location>();
        locs2.add(new Location(1, 0));
        locs2.add(new Location(1, 1));
        districts.add(new District(locs2));
        Set<Location> locs3 = new HashSet<Location>();
        locs3.add(new Location(2, 0));
        locs3.add(new Location(2, 1));
        districts.add(new District(locs3));
        String summary =
          RedistrictingStatistics.formatDistrictPreferences(districts, region);
        assertThat(summary.contains("PARTY0: 1"), is(true));
        assertThat(summary.contains("PARTY1: 1"), is(true));
        assertThat(summary.contains("NONE: 1"), is(true));
    }
}
