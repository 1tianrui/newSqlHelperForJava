package sql;

import Helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class SqlContent {
    public static int SELECT = 1;
    public static int UPDATE = 2;
    public static int INSERT = 3 ;
    private String sql ;
    private String id ; //to java method , to ibatis id
    private int type ;
    private List<Integer> setList ;
    private WhereCondition whereCondition;
    private boolean pageAble ;
    private boolean isNullJudge = false ;

    public boolean isNullJudge() {
        return isNullJudge;
    }

    public void setIsNullJudge(boolean isNullJudge) {
        this.isNullJudge = isNullJudge;
    }

    public boolean isPageAble() {
        return pageAble;
    }

    public void setPageAble(boolean pageAble) {
        this.pageAble = pageAble;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getSetList() {
        return setList;
    }

    public void setSetList(List<Integer> setList) {
        this.setList = setList;
    }

    public WhereCondition getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(WhereCondition whereCondition) {
        this.whereCondition = whereCondition;
    }

    public static List<SqlContent> init(List<String> sqls , TableEntity tableEntity,List<String>sqlsId){
        List<SqlContent> sqlContents = new ArrayList<SqlContent>();
        int i = 0;
        for(String sql :sqls){
             SqlContent sqlContent = buildSqlContent(sql,tableEntity);
            if(sqlContent == null){
                System.out.println("sqlContent null");
            }
             sqlContents.add(sqlContent);
            sqlContent.setId(sqlsId.get(i));
            i++;
        }
        return sqlContents ;
    }

    public static SqlContent buildSqlContent(String sql,TableEntity tableEntity){
        SqlContent content = new SqlContent();
        if(StringHelper.containsWordWithSpace(sql,"insert")){
            content.setType(INSERT);
            return content;
        }

        if(StringHelper.containsWordWithSpace(sql,"select")){
            content.setType(SELECT);
            content.setWhereCondition(whereCondition(sql, tableEntity));
            if(StringHelper.containsWordWithSpace(sql,"limit")){
                content.setPageAble(true);
            }else{
                content.setPageAble(false);
            }
            return content ;
        }

        if(StringHelper.containsWordWithSpace(sql,"update")){
            content.setType(UPDATE);
            content.setWhereCondition(whereCondition(sql, tableEntity));
            content.setSetList(setList(sql,tableEntity));
            return content ;
        }
        return null ;
    }

    public static WhereCondition whereCondition(String sql ,TableEntity table){
        int endWhereIndex = StringHelper.getTargetWordEndIndex(sql,"where");
        String subString = sql.substring(endWhereIndex - 1, sql.length() - 1);
         return   buildWhere(subString,table);
    }

    private static WhereCondition buildWhere(String content ,TableEntity entity){
         List<TableColumn> columns = entity.getColumns();
        WhereCondition condition = new WhereCondition();
         for(int i=0;i < columns.size() ;i++){
             TableColumn column = columns.get(i);
             String name =  column.getName();
             if(StringHelper.containsWordWithSpace(content,name)){
                  int startIndex = StringHelper.getTargetWordEndIndex(content,name);
                  String sub = content.substring(startIndex,startIndex+4);
                  if(sub.contains("in")){
                    condition.getInListColumnIndexes().add(i);
                  }else{
                      condition.getJudgeColumnIndexes().add(i);
                      String operator = "";
                      if(sub.contains(">="))
                          operator=">=";
                      else if(sub.contains("<="))
                          operator="<=";
                      else if(sub.contains("="))
                          operator="=";
                      else  if(sub.contains("<"))
                          operator ="<";
                      else if(sub.contains(">"))
                          operator =">";
                      else
                          System.out.println("error operator :index ="+i);
                      condition.getJudgeOperators().add(operator);
                  }
             }
         }
        return condition;
    }

    public static List<Integer> setList(String sql ,TableEntity table){
         int end = StringHelper.getTargetWordStartIndex(sql, "where");
         int start = StringHelper.getTargetWordStartIndex(sql ,"set");
         return indexList(sql.substring(start,end),table);
    }

    private static List<Integer> indexList(String content ,TableEntity tableEntity){
        List<Integer> whereIndexList = new ArrayList<Integer>() ;

        List<TableColumn> columns = tableEntity.getColumns();
        int i=0;
        for(TableColumn column: columns){
            if(StringHelper.containsWordWithSpace(content,column.getName())){
                whereIndexList.add(i);
            }
            i++;
        }
        return whereIndexList ;
    }


}
