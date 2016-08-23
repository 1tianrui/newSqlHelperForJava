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
        Sql2Spring test = new Sql2Spring("CREATE TABLE `YS_FIN_NewMonthlyBill` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `shop_id` int(11) DEFAULT NULL COMMENT '',\n" +
                "  `bill_year` int(11) DEFAULT NULL,\n" +
                "  `bill_month` int(11) DEFAULT NULL,\n" +
                "  `pay_rule_id` int(11) DEFAULT NULL COMMENT '',\n" +
                "  `discount` int(11) DEFAULT NULL COMMENT ',0-',\n" +
                "  `origin_paid` decimal(10,2) DEFAULT NULL COMMENT '应扣',\n" +
                "  `actual_paid` decimal(10,2) DEFAULT NULL COMMENT '',\n" +
                "  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '',\n" +
                "  `record_total_cnt` int(11) DEFAULT NULL COMMENT '',\n" +
                "  `record_needpaid_cnt` int(11) DEFAULT NULL COMMENT '',\n" +
                "  `record_onlinepay_cnt` int(11) DEFAULT NULL COMMENT '',\n" +
                "  `status` int(11) DEFAULT NULL COMMENT '',\n" +
                "  `created_time` datetime DEFAULT NULL,\n" +
                "  `create_by` varchar(50) DEFAULT NULL,\n" +
                "  `updatetime` datetime DEFAULT NULL,\n" +
                "  `updated_by` varchar(50) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `shop_id` (`shop_id`,`bill_year`,`bill_month`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;",sqlContents,ids);

    }
}
