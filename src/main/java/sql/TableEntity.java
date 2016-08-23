package sql;

import java.util.List;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class TableEntity {
    private String tableName ;
    private List<TableColumn> columns ;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }
}
