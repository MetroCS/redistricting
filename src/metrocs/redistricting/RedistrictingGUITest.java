package metrocs.redistricting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link RedistrictingGUI} internal components.
 */
public class RedistrictingGUITest {
    /**
     * Verify that RegionPanel.update stores the provided region and districts.
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

        assertEquals(Color.CYAN, colorMethod.invoke(panel, Party.PARTY0));
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
        assertEquals(Color.WHITE,
                     textColorMethod.invoke(panel, Party.PARTY1));
        assertEquals(Color.BLACK,
                     textColorMethod.invoke(panel, Party.THIRDPARTY));
    }

    /**
     * Verify that the GUI footer displays both region and district statistics.
     * @throws Exception if reflection fails
     */
    @Test
    public void generateShowsRegionAndDistrictStats() throws Exception {
        Class<?> clazz
            = Class.forName("metrocs.redistricting.RedistrictingGUI");
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Object unsafe = unsafeField.get(null);
        Method allocate
            = unsafeClass.getMethod("allocateInstance", Class.class);
        Object gui = allocate.invoke(unsafe, clazz);

        Field rowsField = clazz.getDeclaredField("rowsField");
        rowsField.setAccessible(true);
        rowsField.set(gui, new JTextField("1", 3));

        Field colsField = clazz.getDeclaredField("colsField");
        colsField.setAccessible(true);
        colsField.set(gui, new JTextField("1", 3));

        Field districtsField = clazz.getDeclaredField("districtsField");
        districtsField.setAccessible(true);
        districtsField.set(gui, new JTextField("1", 3));

        Class<?> panelClass
          = Class.forName("metrocs.redistricting.RedistrictingGUI$RegionPanel");
        Constructor<?> panelCtor = panelClass.getDeclaredConstructor();
        panelCtor.setAccessible(true);
        Object panel = panelCtor.newInstance();
        Field panelField = clazz.getDeclaredField("regionPanel");
        panelField.setAccessible(true);
        panelField.set(gui, panel);

        Field statsField = clazz.getDeclaredField("statsLabel");
        statsField.setAccessible(true);
        statsField.set(gui, new JLabel());

        Method generate = clazz.getDeclaredMethod("generate");
        generate.setAccessible(true);
        generate.invoke(gui);

        JLabel label = (JLabel) statsField.get(gui);
        String text = label.getText();

        assertTrue(text.contains("Region:"));
        assertTrue(text.contains("Districts:"));
    }
}
