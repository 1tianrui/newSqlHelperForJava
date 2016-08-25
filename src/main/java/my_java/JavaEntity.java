package my_java;

import Helper.StringHelper;
import sql.TableColumn;
import sql.TableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class JavaEntity {
    private String location ;
    private String className ;
    private List<JavaColumn> columns ;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<JavaColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<JavaColumn> columns) {
        this.columns = columns;
    }


    public static  JavaEntity createFromTableEntity(TableEntity tableEntity) throws Exception{
        JavaEntity classEntity = new JavaEntity();
        classEntity.setClassName(StringHelper.convertToTuofeng(tableEntity.getTableName(), true));
        List<JavaColumn> columnEntryList = new ArrayList<JavaColumn>();

        List<TableColumn> columnMap = tableEntity.getColumns();
        for(TableColumn entry : columnMap){
            String attr = StringHelper.convertToTuofeng(entry.getName(), false);
            Class type = TypeConvertor.convert(entry.getType());
            JavaColumn tmpEntry = new JavaColumn();
            tmpEntry.setType(type);
            tmpEntry.setName(attr);
            columnEntryList.add(tmpEntry);
        }
        classEntity.setColumns(columnEntryList);
        return classEntity;
    }

    public String getJavaContent(){
        StringBuilder builder = new StringBuilder();
        builder.append("public class "+this.getClassName()+"PO {\r\n");
        for(JavaColumn column : columns){
            builder.append("   private "+column.getType().getName()+"  "+column.getName()+";\r\n");
        }
        for(JavaColumn column : columns){
            builder.append("public void set"+StringHelper.convertToTuofeng(column.getName(),true)+"("+column.getType().getName()+" "+column.getName()+")\r\n");
            builder.append("{\r\nthis."+column.getName()+" = "+column.getName()+";\r\n  }\r\n");
            builder.append("public "+column.getType().getName()+"  get"+StringHelper.convertToTuofeng(column.getName(),true)+"(){\r\n");
            builder.append("return this."+column.getName()+" ;\r\n  }\r\n");
        }
        builder.append("\r\n}");
        return builder.toString();
    }
}
