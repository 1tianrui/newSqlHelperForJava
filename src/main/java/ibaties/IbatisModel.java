package ibaties;


import sql.SqlContent;
import sql.TableColumn;
import sql.TableEntity;
import sql.WhereCondition;

import my_java.JavaColumn;
import my_java.JavaEntity;

import java.util.List;

/**
 * Created by tianrui03 on 16/8/22.
 */
public class IbatisModel {
    private StringBuilder builder  = new StringBuilder(1024);
    private TableEntity tableEntity ;
    private JavaEntity  javaEntity ;
    private String paramClass ;
    private String resultMap ;
     public IbatisModel(TableEntity tableEntity , JavaEntity javaEntity){
         this.javaEntity = javaEntity;
         this.tableEntity = tableEntity;
         paramClass = javaEntity.getClassName()+"PO";
         resultMap = javaEntity.getClassName()+"Mapping";
     }

    public String build(List<SqlContent> contents){
          builder.append(header());
        for(SqlContent content : contents){
            if(content.getType() == SqlContent.INSERT){
                builder.append(buildInsert(content));
            }else if(content.getType() == SqlContent.SELECT){
                builder.append(buildSelect(content));
            }else if(content.getType() == SqlContent.UPDATE){
                builder.append(buildUpdate(content));
            }else{
                System.out.println("unkown sqlcontent type");
            }
        }
        builder.append(tail());
        return builder.toString();
    }

    private String header(){
        StringBuilder headBuilder = new StringBuilder();
        headBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n" +
                "<sqlMap namespace=\""+javaEntity.getClassName()+"\">\r\n");
        headBuilder.append(" <typeAlias alias=\""+javaEntity.getClassName()+"PO\"" +
                "               type=\"类的全部路径\" />\n");
        headBuilder.append("<resultMap id=\""+resultMap+"\"  class=\""+paramClass+"\">\r\n");
        for( int i= 0 ;i<javaEntity.getColumns().size();i++){
            headBuilder.append("<result column=\""+tableEntity.getColumns().get(i).getName()+"\" property=\""+javaEntity.getColumns().get(i).getName()+"\" />\r\n");
        }
        headBuilder.append("</resultMap>\r\n");
        return headBuilder.toString();
    }

    private String tail(){
        return "</sqlMap>";
    }
    private String buildUpdate(SqlContent sqlContent){
        sqlContent.setIsNullJudge(true);
        if(sqlContent.getSetList().size()>4){
            sqlContent.setIsNullJudge(true);
        }
        StringBuilder builder = new StringBuilder() ;
        builder.append("\r\n<update id =\""+sqlContent.getId()+"\" parameterClass =\"map\">\r\n");
        builder.append("UPDATE " + tableEntity.getTableName() + "  set \r\n");

        List<Integer> setList = sqlContent.getSetList();
        if(sqlContent.isNullJudge()){
            for(int id : setList){
                builder.append("<isNotNull prepend = \",\" property = \""+tableEntity.getColumns().get(id).getName()+"\"> "+tableEntity.getColumns().get(id).getName()+"= #"+javaEntity.getColumns().get(id).getName()+"#\n" +
                        "        </isNotNull>\r\n");
            }
        }else{
            for(int id :setList){
                builder.append(" set "+tableEntity.getColumns().get(id).getName()+" = #"+javaEntity.getColumns().get(id).getName()+"#\r\n");
            }
        }

        builder.append(createWhere(sqlContent.getWhereCondition()));
        builder.append("\r\n</update>\r\n");
        return builder.toString();
    }
    private String buildInsert(SqlContent content){
        StringBuilder insertBuilder = new StringBuilder();
        insertBuilder.append("\r\n<insert id=\""+content.getId()+"\" parameterClass=\""+paramClass+"\">\r\n");
        insertBuilder.append("INSERT INTO "+tableEntity.getTableName()+"(");
        List<TableColumn> keys = tableEntity.getColumns();
        for(int i=1;i<keys.size();i++){
            String columnName = keys.get(i).getName() ;
            if(!columnName.equalsIgnoreCase("id")) {
                insertBuilder.append(columnName);
            }else{
                continue ;
            }
            if(i < keys.size() -1){
                insertBuilder.append(",");
            }
        }
        insertBuilder.append(")\r\n");
        insertBuilder.append("VALUES (");
        List<JavaColumn> classEntries = javaEntity.getColumns();
        for(int i=1;i<keys.size();i++){
            String javaEntry = classEntries.get(i).getName();
            if(!javaEntry.equalsIgnoreCase("id")) {
                insertBuilder.append("#" + javaEntry + "#");
            }else{
                continue;
            }
            if(i < keys.size() -1){
                insertBuilder.append(",");
            }
        }
        insertBuilder.append(")\r\n");
        insertBuilder.append("</insert>");
        return insertBuilder.toString();
    }


    private String  buildSelect(SqlContent content){
        StringBuilder builder = new StringBuilder(1024);
        builder.append("\r\n<select id=\""+content.getId()+"\" parameterClass=\"map\" resultMap=\"" + resultMap + "\">\r\n");
        builder.append("SELECT * from "+tableEntity.getTableName()+"\r\n");
        builder.append(createWhere(content.getWhereCondition())+"\r\n");
        builder.append("</select>");
        return builder.toString();
    }
    private String createWhere(WhereCondition condition){

        List<Integer> compareIndexes = condition.getJudgeColumnIndexes();
        List<String> compartor =  condition.getJudgeOperators();
        List<Integer> inListIndexes = condition.getInListColumnIndexes();
        if((compareIndexes==null||compareIndexes.size()==0)&&(compartor==null || compartor.size()==0)&&(inListIndexes==null ||inListIndexes.size() == 0)){
            return "";
        }
        List<TableColumn> tableColumns = tableEntity.getColumns();
        List<JavaColumn> classColumns = javaEntity.getColumns();
        StringBuilder builder = new StringBuilder();
        builder.append("where"+"\r\n");
        boolean addAnd = false;
        if(compareIndexes!=null && compareIndexes.size()>0)
            for(int i=0;i<compareIndexes.size();i++){
                int index = compareIndexes.get(i);
                builder.append(" "+tableColumns.get(index).getName()+""+compartor.get(i)+"#"+classColumns.get(index).getName()+"# ");
                if(i<(compareIndexes.size()-1)){
                    builder.append(" AND "+"\r\n");
                }
                addAnd = true;
            }
        builder.append("\r\n");
        if(inListIndexes!=null && inListIndexes.size() > 0)
            for(int i=0;i<inListIndexes.size();i++){
                if(addAnd){
                    builder.append("AND    ");
                }
                int index =  inListIndexes.get(i);
                builder.append("<iterate propertry=\""+classColumns.get(index).getName()+"List"+"\""+" open=\"(\" close=\")\" conjunction=\",\">"
                        +"#"+tableColumns.get(index).getName()+"List[]#"+
                        "</iterate>"+"\r\n");
            }
        return builder.toString();
    }
}
