import java.io.*;
import java.util.*;

/*
 * https://medium.freecodecamp.org/bet-you-cant-solve-this-google-interview-question-4a6e5a4dc8ee
 */

class Solution {
  public static void main(String[] args) {
    
    Solution s = new Solution();
    int R = 4; int C = 4;
    // int arr[][] = s.generateData(R,C);
    
    int arr[][] = {
    
      {2, 1, 0, 4}, 
      {1, 1, 4, 0 },
      {3, 3, 1, 4 },
      {0, 1, 2, 3}
    };
    
    Map<Integer, Set<Position>>  map = s.getAllColorsSets(arr, R,C);
    for(Integer key: map.keySet()){
      Set<Position> sameColorPos = map.get(key);
      int maxLen = s.findMaxLen ( R,  C, sameColorPos);
      System.out.println("maxLen for key "+ key + " is :: "+ maxLen);
    }
    
  }
  
  private int [][] generateData(int R, int C){
    int [][] arr = new int[R][C];
    for(int i = 0 ; i < R; i++){
      for(int j = 0 ; j < C; j++){
        arr[i][j] = ((int)(Math.random()* 100 ))%5;
      }
    }
    
    for(int i = 0 ; i < R; i++){
      for(int j = 0 ; j < C; j++){
        System.out.print(arr[i][j]+ " ");
      }
      System.out.print("\n");
    }
    return arr;
  }
  private Map<Integer, Set<Position>> getAllColorsSets (int [][] arr, int R, int C) {
    
    Map<Integer, Set<Position>> map = new HashMap();
    for(int i = 0; i < R; ++i){
      for(int j = 0 ; j<C; ++j){
        Set<Position> tmpSet = map.get(arr[i][j]);
        if(tmpSet == null){
          tmpSet = new HashSet();
          map.put(arr[i][j], tmpSet);
        }
        tmpSet.add(new Position (i,j));
      }
    }
    return map;
  }
  
  private class Position {
    int x;
    int y;
    Position(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public String toString () {
      return this.x + "::"+this.y;
    }
    public boolean equals (Object obj) {
      if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    Position other = (Position) obj;
      
      if(this.x == other.x && this.y == other.y ){
        return true;
      }
      return false;
    }
    public int hashCode() {
      String str = x+":"+y;
      return str.hashCode();
    }
  }
  private boolean isValidToAdd(int [][]visitedArr, Set<Position> sameColorPos, Position p) {
    // System.out.println("isValidToAdd Pos :: " + p);
    // System.out.println("isValidToAdd 1 :: " + visitedArr[p.x] [p.y]);
    // System.out.println("isValidToAdd 2 :: " + sameColorPos.contains(p));
    if(visitedArr[p.x] [p.y] == 0 && sameColorPos.contains(p)){
      return true;
    }
    return false;
  }
  
  private int addToRunningList(Queue<Position> runningList, int [][]visitedArr, Position p, int maxLen){
    runningList.add(p);
    visitedArr[p.x][p.y] = 1;
    // System.out.println("Adding to list :: "+ p);
    return maxLen+1;
  }
  
  
  
  
  private int findMaxLen (int R, int C, Set<Position> sameColorPos) {
    
    int [][] visitedArr = new int[R][C];
    int maxLen = Integer.MIN_VALUE;
    // System.out.println("Groups of");
    // for(Position pos : sameColorPos){
    //   System.out.print(pos + " ");
    // }
    
    for(Position pos : sameColorPos){
      Queue<Position> runningList = new LinkedList ();
      int len = 0;
      if(visitedArr[pos.x][pos.y] == 0){
        //Check 
        // System.out.println("Adding to list1 :: "+ pos);
        len = 1;
        runningList.add(pos);
        visitedArr[pos.x][pos.y] = 1;
        maxLen = Math.max(runningList.size(), maxLen);
      } else {
        continue;
      }
      while(!runningList.isEmpty()){
        //System.out.println("Inside contigious 1");
        Position tmp = runningList.remove();



        //check all neighbours

        //Right
        for(int i = tmp.x-1, counter = 0; counter<3; ++counter, ++i){
          for(int j = tmp.y-1, yc = 0; yc < 3; ++yc, ++j){
            //System.out.println("Inside contigious 2");
            if(i > -1 && i < R && j > -1 && j < C && !(i == pos.x && j == pos.y)){
              //System.out.println("Inside contigious 3");
              Position p = new Position(i,j);
              if(isValidToAdd(visitedArr, sameColorPos, p)){
                //System.out.println("Inside contigious 4");
                len = addToRunningList(runningList, visitedArr,p,len);
              }
            }
          
          }
        
        }
        
        
        
        
        
        
        


        //In the end check the size of list
        maxLen = Math.max(len, maxLen);
      }
      
    }
    
    return maxLen;
  }

}
