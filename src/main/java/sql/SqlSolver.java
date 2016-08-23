package sql;

import java.util.List;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class SqlSolver {

   private static final  SqlSovleHelper helper = new SqlSovleHelper();
    public static TableEntity solveTableEntity(String sql){
        String tableName =  helper.resolveTableName(sql);
        List<TableColumn> tableColumns = helper.resolveColumn(sql);
        TableEntity tableEntity = new TableEntity();
        tableEntity.setColumns(tableColumns);
        tableEntity.setTableName(tableName);
        return tableEntity ;
    }


}
