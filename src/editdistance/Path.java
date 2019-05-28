
package editdistance;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Path {
    public int distance;
    public String editSequence;
    public String topAlignmentRow;
    public String bottomAlignmentRow;
    public int[][] arrange;
   
    public String str1;
    public String str2;  
    
    public Path(String str1, String str2){
        this.str1 = str1;
        this.str2 = str2;
        cost(this.str1, this.str2);
    }
    
    public void cost(String s1, String s2){
        s1 = "\u0000" + s1;
        s2 = "\u0000" + s2;

        final int[][] d = new int[s2.length() + 1][s1.length() + 1];
        final Map<Point, Point> parentMap = new HashMap<>();

        for (int i = 0; i < s2.length(); i++) {
            d[i][0] = i+1;
        }
        
        for (int i = 0; i < s1.length(); i++) {
            d[0][i] = i+1;
        }
        
        for (int j = 1; j <= s1.length(); j++) {
            for (int i = 1; i <= s2.length(); i++) {
                int dij = 0; 
                if(s1.charAt(j - 1) == s2.charAt(i - 1)){
                    dij = 0;
                }
                else{
                    dij = 1;
                }

                int tentativeDistance = d[i - 1][j] + 1;
                String S = "I";
                
                if (tentativeDistance > d[i][j - 1] + 1) {
                    tentativeDistance = d[i][j - 1] + 1;
                    S = "D";
                }

                if (tentativeDistance > d[i - 1][j - 1] + dij) {
                    tentativeDistance = d[i - 1][j - 1] + dij;
                    S = "S";
                }

                d[i][j] = tentativeDistance;
                if(S.equals("S")){
                    parentMap.put(new Point(i, j), new Point(i - 1, j - 1));
                }
                else if(S.equals("I")){
                    parentMap.put(new Point(i, j), new Point(i - 1, j));
                }
                else if(S.equals("D")){
                    parentMap.put(new Point(i, j), new Point(i, j - 1)); 
                }
            }
        }

        final StringBuilder topLineBuilder      = new StringBuilder(s1.length() + s2.length());
        final StringBuilder bottomLineBuilder   = new StringBuilder(s1.length() + s2.length());
        final StringBuilder editSequenceBuilder = new StringBuilder(s1.length() + s2.length());
        Point current = new Point(s2.length(), s1.length());

        while (true) {
            Point predecessor = parentMap.get(current);

            if (predecessor == null) {
                break;
            }
            if (current.x != predecessor.x && current.y != predecessor.y) {
                final char schar = s1.charAt(predecessor.y);
                final char zchar = s2.charAt(predecessor.x);

                topLineBuilder.append(schar);
                bottomLineBuilder.append(zchar);
                if(schar != zchar){
                    editSequenceBuilder.append("S");
                }
                else{
                     editSequenceBuilder.append("N");
                }
            } 
            else if (current.x != predecessor.x) {
                topLineBuilder.append("-");
                bottomLineBuilder.append(s2.charAt(predecessor.x));
                editSequenceBuilder.append("I");
            } 
            else {
                topLineBuilder.append(s1.charAt(predecessor.y)); 
                bottomLineBuilder.append("-");
                editSequenceBuilder.append("D");
            }
            current = predecessor;
        }
        
        topLineBuilder     .deleteCharAt(topLineBuilder.length() - 1);
        bottomLineBuilder  .deleteCharAt(bottomLineBuilder.length() - 1);
        editSequenceBuilder.deleteCharAt(editSequenceBuilder.length() - 1);

        topLineBuilder.reverse();
        bottomLineBuilder.reverse();
        editSequenceBuilder.reverse();
                              
        this.distance           = d[s2.length()][s1.length()];
        this.editSequence       = editSequenceBuilder.toString();
        this.topAlignmentRow    = topLineBuilder.toString();
        this.bottomAlignmentRow = bottomLineBuilder.toString();
        this.arrange = d;
    }
}