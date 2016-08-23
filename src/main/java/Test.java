import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianrui on 16/8/23.
 *
 */
public class Test {
    public static void main(String args[]){
        List<String> sqlContents = new ArrayList<String>();
        //sql 两边多留点空格 符号两边也要有空格
        sqlContents.add(" insert    ");
        sqlContents.add(" select * from YS_FIN_NewMonthlyBill where shop_id = and bill_month = and bill_year =    ");
        sqlContents.add(" update YS_FIN_NewMonthlyBill set actual_paid = and discount = where shop_id =     ");
        List<String> ids = new ArrayList<String>();
        ids.add("insert");
        ids.add("select");
        ids.add("update");
        Sql2Spring test = new Sql2Spring("sql from sequel Pro",sqlContents,ids);

    }
}
