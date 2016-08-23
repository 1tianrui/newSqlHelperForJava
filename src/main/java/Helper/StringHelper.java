package Helper;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class StringHelper {
    public static  String convertToTuofeng(String name,boolean firstKey){
        boolean bottomPre = firstKey ;
        StringBuilder className = new StringBuilder();
        for(char a:name.toCharArray()){
            if(a == '\'' || a == '‘'){
                continue;
            }
            if(a !='_'&&(!bottomPre)){
                className.append(String.valueOf(a));
            }
            if(a=='_'){
                bottomPre=true;
                continue;
            }
            if(a!='_'&&bottomPre){
                className.append(String.valueOf(Character.toUpperCase(a)));
                bottomPre=false;
            }
        }
        return className.toString();
    }

    public static int getTargetWordStartIndex(String content ,String target){
        String goal = " "+target+" ";
        return   content.indexOf(goal) ;
    }
    public static int getTargetWordEndIndex(String content ,String target){
        return getTargetWordStartIndex(content,target)+target.length();
    }

    public static boolean containsWordWithSpace(String content ,String word){
        String target  = " "+word+" ";
        return content.contains(target);
    }
}
