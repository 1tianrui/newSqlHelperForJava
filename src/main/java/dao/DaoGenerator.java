package dao;

import sql.SqlContent;
import sql.TableEntity;

import my_java.JavaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class DaoGenerator {

    public String generator(List<SqlContent> contents ,JavaEntity javaEntity, TableEntity tableEntity){
        StringBuilder builder = new StringBuilder(1024);
        builder.append(" public class "+javaEntity.getClassName()+"DAO{\r\n");

        for(SqlContent content : contents){
            if(content.getType() == SqlContent.INSERT){
                builder.append(" public int "+content.getId()+"( "+javaEntity.getClassName()+"PO  po)throws SQLException {\r\n");
                builder.append("return insert(\""+javaEntity.getClassName()+"."+content.getId()+"\",po) ;\r\n}\r\n");
                continue ;
            }
            boolean useMap = false ;
            if( content.getWhereCondition().getJudgeColumnIndexes().size() >5){
                useMap = true ;
            }
            if(content.getType() == SqlContent.UPDATE){
                if(useMap) {
                    builder.append(" public boolean " + content.getId() + " (Map<String,Object> param){\r\n");
                    builder.append("return update(\"" + javaEntity.getClassName() + "." + content.getId() + "\",param);\r\n}\r\n");
                }else {
                    builder.append(" public boolean "+ content.getId()+"(");
                    List<Integer> list = content.getSetList();
                    for(int i =0; i<list.size() ;i++){
                        builder.append(javaEntity.getColumns().get(list.get(i)).getType().getName()+" "+javaEntity.getColumns().get(list.get(i)).getName());
                        if(i!=list.size()-1){
                            builder.append(",");
                        }
                    }
                    list = content.getWhereCondition().getJudgeColumnIndexes();
                    if(list.size()>0){
                        builder.append(",");
                    }
                    for(int i =0; i<list.size() ;i++){
                        builder.append(javaEntity.getColumns().get(list.get(i)).getType().getName()+" "+javaEntity.getColumns().get(list.get(i)).getName());
                        if(i!=list.size()-1){
                            builder.append(",");
                        }
                    }

                    list = content.getWhereCondition().getInListColumnIndexes();
                    if(list.size()>0){
                        builder.append(",");
                    }
                    for(int i =0; i<list.size() ;i++){
                        builder.append(javaEntity.getColumns().get(list.get(i)).getType().getName()+" "+javaEntity.getColumns().get(list.get(i)).getName());
                        if(i!=list.size()-1){
                            builder.append(",");
                        }
                    }
                }
                builder.append("){\r\n");

                List<Integer> idList = new ArrayList<Integer>();
                idList.addAll(content.getSetList());
                idList.addAll(content.getWhereCondition().getJudgeColumnIndexes());
                idList.addAll(content.getWhereCondition().getInListColumnIndexes());

                builder.append(" Map<String,Object> param = new HashMap<String,Object> ();\r\n");
                for(int id : idList){
                    builder.append(" param.put(\""+javaEntity.getColumns().get(id).getName()+"\","+javaEntity.getColumns().get(id).getName()+");\r\n");
                }
                builder.append("return update("+javaEntity.getClassName()+"."+content.getId()+",param);\r\n");
                builder.append("}\r\n");
                continue ;
            }
            if( content.getType() == SqlContent.SELECT){
                builder.append(" public List<"+javaEntity.getClassName()+"PO> "+content.getId()+"(");
                List<Integer> list = new ArrayList<Integer>();
                list = content.getWhereCondition().getJudgeColumnIndexes();

                for(int i =0; i<list.size() ;i++){
                    builder.append(javaEntity.getColumns().get(list.get(i)).getType().getName()+" "+javaEntity.getColumns().get(list.get(i)).getName());
                    if(i!=list.size()-1){
                        builder.append(",");
                    }
                }

                list = content.getWhereCondition().getInListColumnIndexes();
                if(list.size()>0){
                    builder.append(",");
                }
                for(int i =0; i<list.size() ;i++){
                    builder.append(javaEntity.getColumns().get(list.get(i)).getType().getName()+" "+javaEntity.getColumns().get(list.get(i)).getName());
                    if(i!=list.size()-1){
                        builder.append(",");
                    }
                }
                builder.append("return select("+javaEntity.getClassName()+"."+content.getId()+",param);\r\n");
            }
            builder.append("){\r\n");

            List<Integer> idList = new ArrayList<Integer>();
            if(content.getSetList()!=null)
            idList.addAll(content.getSetList());
            idList.addAll(content.getWhereCondition().getJudgeColumnIndexes());
            idList.addAll(content.getWhereCondition().getInListColumnIndexes());

            builder.append(" Map<String,Object> param = new HashMap<String,Object> ();\r\n");
            for(int id : idList){
                builder.append(" param.put(\""+javaEntity.getColumns().get(id).getName()+"\","+javaEntity.getColumns().get(id).getName()+");\r\n");
            }
            builder.append("}\r\n");
            continue ;
            }
        builder.append("\r\n}\r\n");
            return builder.toString();
    }
}
