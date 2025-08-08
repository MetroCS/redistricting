package metrocs.redistricting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link RedistrictingGUI}'s internal components.
 */
public class RedistrictingGUITest {
    /**
     * Verifies that RegionPanel.update stores the provided region and districts.
     * @throws Exception if reflection fails
     */
    @Test
    public void updateStoresRegionAndDistricts() throws Exception {
        Class<?> clazz = Class.forName("metrocs.redistricting.RedistrictingGUI$RegionPanel");
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object panel = ctor.newInstance();

        Method update = clazz.getDeclaredMethod("update", Region.class, HashSet.class);
        update.setAccessible(true);

        Set<Location> locs = new HashSet<>();
        Location loc = new Location(0, 0);
        locs.add(loc);
        Set<Voter> voters = new HashSet<>();
        voters.add(new Voter(Party.PARTY0, loc));
        Region region = new Region(locs, voters);
        Set<Location> distLocs = new HashSet<>();
        distLocs.add(loc);
        HashSet<District> districts = new HashSet<>();
        districts.add(new District(distLocs));

        update.invoke(panel, region, districts);

        Field regionField = clazz.getDeclaredField("region");
        regionField.setAccessible(true);
        Field districtsField = clazz.getDeclaredField("districts");
        districtsField.setAccessible(true);

        assertSame(region, regionField.get(panel));
        assertEquals(districts, districtsField.get(panel));
    }

    /**
     * Ensures that colorForParty returns the expected colors.
     * @throws Exception if reflection fails
     */
    @Test
    public void colorForPartyMatchesExpected() throws Exception {
        Class<?> clazz = Class.forName("metrocs.redistricting.RedistrictingGUI$RegionPanel");
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object panel = ctor.newInstance();

        Method colorMethod = clazz.getDeclaredMethod("colorForParty", Party.class);
        colorMethod.setAccessible(true);

        assertEquals(Color.BLUE, colorMethod.invoke(panel, Party.PARTY0));
        assertEquals(Color.RED, colorMethod.invoke(panel, Party.PARTY1));
        assertEquals(Color.GREEN, colorMethod.invoke(panel, Party.THIRDPARTY));
    }

    /**
     * Ensures that symbolForParty returns the expected symbols.
     * @throws Exception if reflection fails
     */
    @Test
    public void symbolForPartyMatchesExpected() throws Exception {
        Class<?> clazz = Class.forName("metrocs.redistricting.RedistrictingGUI$RegionPanel");
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object panel = ctor.newInstance();

        Method symbolMethod = clazz.getDeclaredMethod("symbolForParty", Party.class);
        symbolMethod.setAccessible(true);

        assertEquals('0', ((Character) symbolMethod.invoke(panel, Party.PARTY0)).charValue());
        assertEquals('1', ((Character) symbolMethod.invoke(panel, Party.PARTY1)).charValue());
        assertEquals('T', ((Character) symbolMethod.invoke(panel, Party.THIRDPARTY)).charValue());
    }

    /**
     * Ensures that textColorForParty chooses contrasting colors.
     * @throws Exception if reflection fails
     */
    @Test
    public void textColorForPartyContrasts() throws Exception {
        Class<?> clazz = Class.forName("metrocs.redistricting.RedistrictingGUI$RegionPanel");
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object panel = ctor.newInstance();

        Method textColorMethod = clazz.getDeclaredMethod("textColorForParty", Party.class);
        textColorMethod.setAccessible(true);

        assertEquals(Color.BLACK,
                     textColorMethod.invoke(panel, Party.PARTY0));
        assertEquals(Color.BLACK,
                     textColorMethod.invoke(panel, Party.PARTY1));
        assertEquals(Color.BLACK,
                     textColorMethod.invoke(panel, Party.THIRDPARTY));
    }
}
