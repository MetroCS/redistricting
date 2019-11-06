package swdmt.redistricting;
import java.util.ArrayList;

/**
 *
 * AllDistrictGen will generate all possible districts within a
 *    region.
 *
 *
 * @author Jessica
 * @version 1.0
 */
public class AllDistrictGen
{
  public static ArrayList<District> generateDistricts(int gridWidth, int gridHeight, int districtSize){
     ArrayList<ArrayList<Location>> starPattern = StarGenerator.generatePattern(districtSize);
     ArrayList<ArrayList<Location>> fourthQuadrantPattern = new  ArrayList<ArrayList<Location>>();
     for (ArrayList<Location> pattern : starPattern){
       boolean hasNegativeLocation = false;
       
       for (Location coord : pattern){
         if (coord.xCoordinate() < 0 || coord.yCoordinate() < 0){
           hasNegativeLocation = true;
         }
       }
              
       if (!hasNegativeLocation){
         fourthQuadrantPattern.add(pattern);
       }
     }
     
     ArrayList<District> currentDistricts = new ArrayList<District>();
     for (int x = 0; x < gridWidth; x++){
       for (int y = 0; y < gridHeight; y++){
         for (ArrayList<Location> pattern : fourthQuadrantPattern){
           ArrayList<Location> transformed = transformPattern(pattern, x, y);
           if (!isDistrictInBounds(transformed, gridWidth, gridHeight)){
             continue;
           }
           
           District transformedAsDistrict = new District(transformed);
           
           if (!Contains(currentDistricts, transformedAsDistrict)){
             
             currentDistricts.add(transformedAsDistrict);
           }
         }
         
       }
       
     }
  
     return currentDistricts;
  }
  
  private static boolean Contains(ArrayList<District> districts, District district){
    for (District d : districts){
      if (Equals(d, district)){
        return true;
      }
    }
    return false;
  }
  
  private static boolean Equals(District districtA, District districtB){
    if (districtA.size() != districtB.size()){
      return false;
    }
    
    for (Location coordA : districtA.locations()){
      boolean anyMatch = false;
      for (Location coordB : districtB.locations()){
        if (coordA.equals(coordB)){
          anyMatch = true;
        }
      }
      if (!anyMatch){
        return false;
      }
    }
    return true;
  }
  
  private static ArrayList<Location> transformPattern(ArrayList<Location> pattern, int offsetX, int offsetY){
    ArrayList<Location> newPattern = new ArrayList<Location>();
    
    for (Location coord : pattern){
      Location transformedCoord = new Location(coord.xCoordinate() + offsetX, coord.yCoordinate() + offsetY);
      newPattern.add(transformedCoord);
    }
    
    return newPattern;
  }
  
  
  private static boolean isDistrictInBounds(ArrayList<Location> district, int gridWidth, int gridHeight){
    for (Location cord : district){
      if (cord.xCoordinate() < 0)
        return false;
      if (cord.yCoordinate() < 0)
        return false;
      
      
      if (cord.xCoordinate() >= gridWidth)
        return false;
      if (cord.yCoordinate() >= gridHeight)
        return false;
    }
    
    return true;
  }
  
}

