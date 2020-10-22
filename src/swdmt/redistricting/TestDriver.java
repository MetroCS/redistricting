package swdmt.redistricting;
import swdmt.redistricting.Location.java;
import swdmt.redistricting.Voter.java;
import swdmt.redistricting.Region.java;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;


public class TestDriver {
	public static void main(String[] args) {
		Set<Location> locations = new TreeSet<Location>();
		Set<Voter> voters = new HashSet<Voter>();

		Set<Voter> voterSet = new HashSet<Voter>();
		
		Region test1 = new Region(voters, locations);
	}
}
