import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianrui on 16/8/23.
 *
 */
public class Test {
    public static void main(String args[]){
        List<String> sqlContents = new ArrayList<String>();
        //sql 两边多留点空格 符号两边也要有空格 ,如果sql是update 默认会更新updatetime
        sqlContents.add(" update  YY_SuccessBookingRecord set record_type =  where bookingrecord_id =     ");
        List<String> ids = new ArrayList<String>();
        //对应每个sql的id
        ids.add("update");

        //当参数列表太过复杂时，daoMethod.generateMapClass()可以帮你生成参数类
        //这里的create table语句建议从sequel 工具中拷出来，
        Sql2Spring test = new Sql2Spring("CREATE TABLE `YY_SuccessBookingRecord` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',\n" +
                "  `shop_id` int(11) DEFAULT NULL COMMENT '商户编号',\n" +
                "  `bookingrecord_id` int(11) DEFAULT NULL COMMENT '订单编号',\n" +
                "  `created_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `booking_time` datetime DEFAULT NULL COMMENT '预订时间',\n" +
                "  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  `updated_by` varchar(50) DEFAULT NULL COMMENT '更新人',\n" +
                "  `record_type` int(11) DEFAULT NULL COMMENT '订单类型 0普通订单 10团购订单',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `shop_id` (`shop_id`,`booking_time`),\n" +
                "  KEY `bookingrecord_id` (`bookingrecord_id`),\n" +
                "  KEY `created_time` (`created_time`),\n" +
                "  KEY `updatetime` (`updatetime`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;",sqlContents,ids);

    }
}
