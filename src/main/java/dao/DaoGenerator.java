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

    public DaoClass generator(List<SqlContent> contents ,JavaEntity javaEntity, TableEntity tableEntity) {
        DaoClass daoClass = new DaoClass(javaEntity.getClassName() + "DAO");

        StringBuilder builder = new StringBuilder(1024);

        for (SqlContent content : contents) {
            DaoMethod method = new DaoMethod();
            method.setMethodName(content.getId());
            method.setMethodType(content.getType());
            method.setSqlId(javaEntity.getClassName() + "." + content.getId());
            String returnType = getReturnType(content.getType(),javaEntity);
            method.setReturnType(returnType);
            addAttr(method,content,javaEntity);
            daoClass.addMethod(method);
        }
        return daoClass;
    }

        private String getReturnType(int type ,JavaEntity entity){
            if(type == SqlContent.UPDATE){
                return "boolean";
            }
            if(type == SqlContent.INSERT){
                return "int";
            }
            if(type == SqlContent.SELECT){
                return "List<"+entity.getClassName()+"PO>";
            }
            System.out.println("get return type error") ;
            return "error";
        }
        private void addAttr(DaoMethod method , SqlContent content , JavaEntity javaEntity){
            List<Integer> setList = content.getSetList();
            List<Integer> inList = null ;
            List<Integer> judgeList = null ;
            if(content.getWhereCondition() !=null) {
                inList = content.getWhereCondition().getInListColumnIndexes();
                judgeList = content.getWhereCondition().getJudgeColumnIndexes();
            }
            List<Integer> attrIndexs = new ArrayList<Integer>();
            if(setList != null ){
               attrIndexs.addAll(setList);
           }
            if(judgeList != null){
            attrIndexs.addAll(judgeList);
        }
        if(inList != null){
            attrIndexs.addAll(inList);
        }
        for(int index : attrIndexs){
            DaoAttribute attribute = new DaoAttribute();
            attribute.setName(javaEntity.getColumns().get(index).getName());
            attribute.setType(javaEntity.getColumns().get(index).getType());
            method.addAttribute(attribute);
        }
    }
}
