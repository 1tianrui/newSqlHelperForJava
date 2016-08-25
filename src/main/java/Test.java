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
        sqlContents.add(" select * from YY_SuccessBookingRecord where shop_id =  and booking_time >  ");
        List<String> ids = new ArrayList<String>();
        ids.add("insert");
        ids.add("select");
        Sql2Spring test = new Sql2Spring("CREATE TABLE `YY_SuccessBookingRecord` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',\n" +
                "  `shop_id` int(11) DEFAULT NULL COMMENT '商户编号',\n" +
                "  `bookingrecord_id` int(11) DEFAULT NULL COMMENT '订单编号',\n" +
                "  `created_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `booking_time` datetime DEFAULT NULL COMMENT '预订时间',\n" +
                "  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  `updated_by` varchar(50) DEFAULT NULL COMMENT '更新人',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `shop_id` (`shop_id`,`booking_time`),\n" +
                "  KEY `bookingrecord_id` (`bookingrecord_id`),\n" +
                "  KEY `created_time` (`created_time`),\n" +
                "  KEY `updatetime` (`updatetime`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;",sqlContents,ids);

    }
}
