package metrocs.redistricting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Simple graphical interface for experimenting with the redistricting model.
 *
 * <p>The interface allows non-programmers to specify basic parameters such as
 * the number of rows and columns in a region and the number of districts to
 * generate.  The resulting districts and voter affiliations are displayed as a
 * colored grid.</p>
 */
public final class RedistrictingGUI {
    /** Default size of display. */
    private static final int STD_SIZE = 500;
    /** Width of a thicker border. */
    private static final float THICK = 3f;

    /** Text field for number of rows. */
    private final JTextField rowsField = new JTextField("5", 3);
    /** Text field for number of columns. */
    private final JTextField colsField = new JTextField("5", 3);
    /** Text field for number of districts. */
    private final JTextField districtsField = new JTextField("5", 3);
    /** Panel that renders the region and districts. */
    private final RegionPanel regionPanel = new RegionPanel();
    /** Label for displaying party preference statistics. */
    private final JLabel statsLabel = new JLabel();

    /**
     * Constructs the GUI and displays the initial frame.
     */
    private RedistrictingGUI() {
        JFrame frame = new JFrame("Redistricting Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel input = new JPanel(new FlowLayout());
        input.add(new JLabel("Rows:"));
        input.add(rowsField);
        input.add(new JLabel("Columns:"));
        input.add(colsField);
        input.add(new JLabel("Districts:"));
        input.add(districtsField);
        JButton generateButton = new JButton("Generate");
        input.add(generateButton);
        frame.add(input, BorderLayout.NORTH);

        frame.add(regionPanel, BorderLayout.CENTER);
        frame.add(statsLabel, BorderLayout.SOUTH);
        generateButton.addActionListener(e -> generate());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Generates a new random region and computes districts based on user input.
     */
    private void generate() {
        int rows = Integer.parseInt(rowsField.getText());
        int cols = Integer.parseInt(colsField.getText());
        int districts = Integer.parseInt(districtsField.getText());

        // Start with a rectangular region.
        Region initial = new Region(rows, cols);
        Set<Location> locs = new HashSet<>(initial.locations());
        Set<Voter> voters = new HashSet<>();
        Random rand = new Random();
        for (Location loc : locs) {
            Party p = rand.nextBoolean() ? Party.PARTY0 : Party.PARTY1;
            voters.add(new Voter(p, loc));
        }
        Region region = new Region(locs, voters);
        HashSet<District> dists
            = Redistrictor.generateDistricts(region, districts);
        regionPanel.update(region, dists);
        statsLabel.setText(
                     RedistrictingStatistics.formatPartyPreferences(region));
    }

    /**
     * Launch the redistricting graphical interface.
     * @param args ignored command line arguments
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new RedistrictingGUI());
    }

    /**
     * Panel that draws the region and its districts.
     */
    private static class RegionPanel extends JPanel {
        /** Serialization version requirement. */
        private static final long serialVersionUID = 4L;

        /** The current region. */
        private Region region;
        /** The current districts. */
        private HashSet<District> districts;

        /**
         * Update the panel with a new region and set of districts.
         * @param r the region to display
         * @param d the districts to display
         */
        void update(final Region r, final HashSet<District> d) {
            this.region = r;
            this.districts = d;
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(STD_SIZE, STD_SIZE);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            if (region == null) {
                return;
            }
            Map<Location, Voter> voterMap = new HashMap<>();
            for (Voter v : region.voters()) {
                voterMap.put(v.location(), v);
            }
            int maxX = 0;
            int maxY = 0;
            for (Location l : region.locations()) {
                maxX = Math.max(maxX, l.xCoordinate());
                maxY = Math.max(maxY, l.yCoordinate());
            }
            int cols = maxX + 1;
            int rows = maxY + 1;
            int size = Math.min(getWidth() / Math.max(cols, 1),
                                 getHeight() / Math.max(rows, 1));
            Graphics2D g2 = (Graphics2D) g.create();
            // Draw voter backgrounds.
            for (Location l : region.locations()) {
                int x = l.xCoordinate();
                int y = l.yCoordinate();
                Voter v = voterMap.get(l);
                g2.setColor(colorForParty(v.affiliation()));
                g2.fillRect(x * size, y * size, size, size);
            }

            // Draw district boundaries with a thicker stroke.
            if (districts != null) {
                Stroke oldStroke = g2.getStroke();
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(THICK));
                for (District dist : districts) {
                    Set<Location> locset = new HashSet<>(dist.locations());
                    for (Location l : locset) {
                        int x = l.xCoordinate() * size;
                        int y = l.yCoordinate() * size;
                        if (!locset.contains(new Location(l.xCoordinate() - 1,
                                                        l.yCoordinate()))) {
                            g2.drawLine(x, y, x, y + size);
                        }
                        if (!locset.contains(new Location(l.xCoordinate() + 1,
                                                        l.yCoordinate()))) {
                            g2.drawLine(x + size, y, x + size, y + size);
                        }
                        if (!locset.contains(new Location(l.xCoordinate(),
                                                        l.yCoordinate() - 1))) {
                            g2.drawLine(x, y, x + size, y);
                        }
                        if (!locset.contains(new Location(l.xCoordinate(),
                                                        l.yCoordinate() + 1))) {
                            g2.drawLine(x, y + size, x + size, y + size);
                        }
                    }
                }
                g2.setStroke(oldStroke);
            }

            // Draw voter symbols after boundaries so they are visible.
            for (Location l : region.locations()) {
                int x = l.xCoordinate();
                int y = l.yCoordinate();
                Voter v = voterMap.get(l);
                g2.setColor(textColorForParty(v.affiliation()));
                String sym = String.valueOf(symbolForParty(v.affiliation()));
                FontMetrics fm = g2.getFontMetrics();
                int textX
                    = x * size + (size - fm.stringWidth(sym)) / 2;
                int textY
                    = y * size + ((size - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(sym, textX, textY);
            }
            g2.dispose();
        }

        /**
         * Color associated with a party.
         * @param p the party
         * @return color for display
         */
        private Color colorForParty(final Party p) {
            switch (p) {
                case PARTY0:
                    return Color.BLUE;
                case PARTY1:
                    return Color.RED;
                case THIRDPARTY:
                    return Color.GREEN;
                default:
                    return Color.LIGHT_GRAY;
            }
        }

        /** Perceptual color constant for red. */
        private static final double RED_PERCEPTUAL_CONST = 0.299;
        /** Perceptual color constant for green. */
        private static final double GREEN_PERCEPTUAL_CONST = 0.587;
        /** Perceptual color constant for blue. */
        private static final double BLUE_PERCEPTUAL_CONST = 0.114;
        /** Perceptual constant for luminance. */
        private static final double LUMINANCE_CONST = 0.114;
        /**
         * Selects a contrasting text color for a party cell.
         * Uses constants from luminance perception models used in computer
         * graphics and color science to use white or black text color for
         * contrast and readability. (See: https://poynton.ca/PDFs/ColorFAQ.pdf)
         * @param p the party
         * @return color for text display
         */
        private Color textColorForParty(final Party p) {
            Color base = colorForParty(p);
            int luminance = (int) Math.sqrt(
                base.getRed() * base.getRed() * RED_PERCEPTUAL_CONST
                + base.getGreen() * base.getGreen() * GREEN_PERCEPTUAL_CONST
                + base.getBlue() * base.getBlue() * BLUE_PERCEPTUAL_CONST);
            return luminance < LUMINANCE_CONST ? Color.WHITE : Color.BLACK;
        }

        /**
         * Symbol associated with a party.
         * @param p the party
         * @return character symbol for display
         */
        private char symbolForParty(final Party p) {
            return p.id();
        }
    }
}
