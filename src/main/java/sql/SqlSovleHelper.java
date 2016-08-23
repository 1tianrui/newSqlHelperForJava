package sql;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class SqlSovleHelper {


    public String resolveTableName(String sql){
        List<String> elements = new ArrayList<String>();
        boolean tableReaded = false ;
        StringBuilder builder =  new StringBuilder();
        int index =0 ;
        boolean tableNameReaded =false ;
        while(!tableNameReaded){
            char tmp =sql.charAt(index);
            index ++;
            if(tmp == ' '){
                if(builder.length() !=0 ){
                    String content =builder.toString();
                    if(tableReaded){
                        filtInvalidCharTwoSides(content.toString());
                        return filtInvalidCharTwoSides(content.toString());
                    }
                    builder = new StringBuilder();
                    if(content.equalsIgnoreCase("table")){
                        tableReaded = true;
                    }
                }
            }else{
                builder.append(tmp);
            }
        }
        return  filtInvalidCharTwoSides(toString());
    }


    public List<TableColumn>  resolveColumn(String sql){
        String content = sql.substring(sql.indexOf('(')+1,sql.lastIndexOf(')')-1);
        List<TableColumn> columnEntries = new ArrayList<TableColumn>();
        String[] columns = content.split(",");
        int newIndex = 0;
        //将可能被,号隔离开的行重新凑到一起
        String [] newColumns = new String[columns.length];
        for(int i =0 ;i<columns.length;i++){
            String tmp = columns[i];
            for(int tmpIndex = tmp.length()-1;tmpIndex >= 0 ; tmpIndex -- ){
                char code = tmp.charAt(tmpIndex);
                if(code == ')'){
                    newColumns[newIndex] = tmp;
                    newIndex ++;
                    break ;
                }
                if(code == '(' && i != columns.length -1){
                    newColumns[newIndex] = columns[i]+columns[i+1];
                    newIndex ++;
                    i++;
                    break;
                }
                if(tmpIndex == 0){
                    newColumns[newIndex] = columns[i];
                    newIndex ++;
                    break;
                }
            }
        }
        for(String column : newColumns){
            if(!isColumnContent(column)){
                continue;
            }
            //取出字段名称和类型
            String [] eles = SqlSovleHelper.splitBlank(column);

            //todo 已经支持自动生成column name  & type
            try {
                TableColumn entry = new TableColumn();
                entry.setName(filtInvalidCharTwoSides(eles[0]));
                entry.setType(eles[1]);
                columnEntries.add(entry);
            }catch (Exception e){
                System.out.println("hehe");
            }
        }
        return columnEntries;
    }


    public static String[]  splitBlank(String content){
        String [] contents = content.split(" ");
        List<String> s =  new ArrayList();
        for(String c : contents){
            if(c == null || c.trim().length() == 0)
                continue;
            s.add(c);
        }
        String [] result = new String[s.size()];
        for(int i=0;i<s.size() ;i++){
            result[i] = s.get(i);
        }
        return  result;
    }


    //判断是否是行
    private boolean isColumnContent(String content){
        if(content == null || content.trim().length()==0){
            return false;
        }
        if((content.contains("text")||content.contains("decimal")||content.contains("int(") || content.contains("datetime")
                ||content.contains("char"))){
            return true;
        }
        return false;
    }
    //取出表名两侧可能存在的符号
    private String filtInvalidCharTwoSides(String tableName){
        if(tableName == null || tableName.trim().length() == 0){
            return  "";
        }
        int start =0;
        int end =tableName.length();
        while(true) {
            if (tableName.charAt(start) < 'A' || tableName.charAt(start) > 'z' ||( tableName.charAt(start)< 'a' &&  tableName.charAt(start) >'Z' ) ) {
                start = start + 1;
            }else{
                break;
            }
        }
        while(true) {
            if (tableName.charAt(end-1) < 'A' || tableName.charAt(end-1) > 'z' ||( tableName.charAt(end-1)< 'a' &&  tableName.charAt(end-1) >'Z' ) ) {
                end = end - 1;
            }else{
                break;
            }
        }
        return tableName.substring(start, end);
    }

}
