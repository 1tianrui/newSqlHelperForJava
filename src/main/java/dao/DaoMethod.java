package dao;

import sql.SqlContent;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by jnkmhbl on 16/8/25.
 */
public class DaoMethod {
    private String returnType ;
    private String methodName ;
    private String sqlId ;
    private String exception ="SQLException";
    private List<DaoAttribute> attributes;
    private static final String whiteSpace ="  ";
    private static final String nextLine ="\r\n";
    private int methodType ;

    public static String getWhiteSpace() {
        return whiteSpace;
    }

    public static String getNextLine() {
        return nextLine;
    }

    public int getMethodType() {
        return methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<DaoAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DaoAttribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(DaoAttribute attribute){
        if(attributes == null){
            attributes = new ArrayList<DaoAttribute>();
        }
        attributes.add(attribute);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder(1024);
        builder.append(nextLine).append("public").append(whiteSpace).append(returnType)
                .append(whiteSpace).append(methodName).append("(");
        if(attributes == null || attributes.size() == 0){
            //默认是insert方法
            builder.append("PO类型 po){").append(nextLine).append(whiteSpace)
                    .append("return").append(whiteSpace).append("(").append(returnType+")").append(getSqlClientMethod()).append("(\"")
            .append(sqlId).append("\",").append("po);").append(nextLine).append("  }");
            return builder.toString();

        }else {
           for( int i=0;i<attributes.size() ;i++){
                System.out.println("public void set"+ attributes.get(i).getName()+"("+attributes.get(i).getType()+" "+attributes.get(i).getName()+"){");
                System.out.println("param.put(\""+attributes.get(i).getName()+"\","+attributes.get(i).getName()+");");
                System.out.println("}");
                DaoAttribute attribute = attributes.get(i);
                builder.append(attribute.getType()).append(whiteSpace).append(attribute.getName());
                if(i != attributes.size()-1){
                    builder.append(",");
                }
           }
            String content = builder.toString().replace("class ","");
            builder = new StringBuilder(content);
            builder.append("){").append(nextLine).append(whiteSpace);
            builder.append("Map<String,Object> param = new HashMap<String,Object>();").append(nextLine);
            for(DaoAttribute attribute:attributes){
                builder.append(whiteSpace).append("param.put(\"").append(attribute.getName())
                        .append("\",").append(attribute.getName()).append(");").append(nextLine);
            }
            builder.append(whiteSpace).append("return").append(whiteSpace).append("("+returnType+")").append(getSqlClientMethod()).append("(\"")
                    .append(sqlId).append("\",param);").append(nextLine);
            builder.append("}");
        }
        return builder.toString();
    }


    public String getSqlClientMethod(){
        if(methodType == SqlContent.INSERT){
            return "insert";
        }else if(methodType == SqlContent.UPDATE){
            return "update";
        }else if( methodType ==SqlContent.SELECT){
            return "queryForList";
        }
        return "error";
    }

    public String generateMapClass(){
        StringBuilder builder = new StringBuilder();
        builder.append("public class 参数类名字 {\r\n");
        builder.append("private Map<String,Object> param = new HashMap<String,Object>() ;");
        builder.append("public Map<String,Object> getParam(){\r\n return param ;\r\n }");

        for(DaoAttribute attribute : attributes){
            String name =attribute.getName();
            builder.append("public void set"+name+"("+getClassType(attribute.getType())+" "+name+"){\r\n param.put(\""+name+"\","+name+");\r\n}\r\n");
        }
        return builder.toString();
    }
    private String getClassType(Class type){
        String classType =type.getName().replace("class ","");
        return classType;
    }
}
