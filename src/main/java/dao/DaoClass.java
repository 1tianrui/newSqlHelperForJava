package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jnkmhbl on 16/8/25.
 */
public class DaoClass {
    private String className ;
    private List<DaoMethod> methodList;
    private List<Class>  importClasses ;
    private Map<String,Object>  importMap ;
    private String extendsClass = "BaseDao" ;
    public DaoClass(String className){
        this.className = className;
        methodList = new ArrayList<DaoMethod>();
        importClasses = new ArrayList<Class>();
        importMap = new HashMap<String, Object>();
    }


    public void addMethod(DaoMethod method){
        methodList.add(method);
        if(method.getAttributes()!=null)
        for(DaoAttribute attribute : method.getAttributes()){
            if(!importMap.containsKey(attribute.getType().getName())){
                importClasses.add(attribute.getType());
                importMap.put(attribute.getType().getName(),new Object());
            }
        }
    }
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<DaoMethod> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<DaoMethod> methodList) {
        this.methodList = methodList;
    }

    public List<Class> getImportClasses() {
        return importClasses;
    }

    public void setImportClasses(List<Class> importClasses) {
        this.importClasses = importClasses;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
       for(Class cl :importClasses){
           builder.append("import ").append(cl.getName()).append(";\r\n");
       }
        builder.append("public class ").append(className).append("{\r\n");
        for(DaoMethod method: methodList){
            builder.append(method.toString());
        }
        builder.append("\r\n}");
        return builder.toString();
    }

}
