package swdmt.redistricting;

import java.util.ArrayList;

/**
 *
 *
 *
 *
 * @author Jessica
 * @version 1.0
 */
public class StarGenerator
{
  public static ArrayList<ArrayList<Location>> generatePattern(int n){
    ArrayList<ArrayList<Location>> cc = new ArrayList<ArrayList<Location>>();
    ArrayList<Location> visited = new ArrayList<Location>();

    Location currentCoord = new Location(0,0);
    Traverse(new ArrayList<Location>(), currentCoord, n, cc);

    return cc;
  }

  public static void Traverse(ArrayList<Location> visited, Location current, int moves, ArrayList<ArrayList<Location>> cc){
    int remainingMoves = moves - 1;

    ArrayList<Location> newChain = new ArrayList<Location>(visited);
    newChain.add(current);

    if(remainingMoves == 0){
      cc.add(newChain);
      return;
    }

    Location northCoordinate = new Location(current.xCoordinate(), current.yCoordinate() - 1);
    if (!Contains(visited,northCoordinate)){
      Traverse(newChain, northCoordinate, remainingMoves, cc);
    }

    Location southCoordinate = new Location(current.xCoordinate(), current.yCoordinate() + 1);
    if (!Contains(visited,southCoordinate)){
      Traverse(newChain, southCoordinate, remainingMoves, cc);
    }

    Location eastCoordinate = new Location(current.xCoordinate() + 1, current.yCoordinate());
    if (!Contains(visited,eastCoordinate)){
      Traverse(newChain, eastCoordinate, remainingMoves, cc);
    }


    Location westCoordinate = new Location(current.xCoordinate() - 1, current.yCoordinate());
    if (!Contains(visited,westCoordinate)){
      Traverse(newChain, westCoordinate, remainingMoves, cc);
    }
  }

  public static boolean Contains(ArrayList<Location> coordinates, Location coordinate){
    for (Location cord : coordinates){
      if (cord.equals(coordinate))
        return true;
    }
    return false;
  }
  
}

