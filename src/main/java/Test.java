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
        sqlContents.add(" select * from YS_Shop_PayRule where new_pay_mode in  and valid =    ");
        List<String> ids = new ArrayList<String>();
        //对应每个sql的id
        ids.add("update");

        //当参数列表太过复杂时，daoMethod.generateMapClass()可以帮你生成参数类
        //这里的create table语句建议从sequel 工具中拷出来，
        Sql2Spring test = new Sql2Spring("CREATE TABLE `YS_Shop_PayRule` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `shop_id` int(11) DEFAULT NULL COMMENT '商户编号',\n" +
                "  `new_pay_mode` int(11) DEFAULT NULL COMMENT '新增收费模式,20按单收费,30按月收费',\n" +
                "  `avg_consumption` decimal(10,2) DEFAULT NULL COMMENT '人均消费',\n" +
                "  `city_id` int(11) DEFAULT NULL COMMENT '城市编号',\n" +
                "  `valid` int(11) DEFAULT NULL COMMENT '是否有效 0无效 10有效',\n" +
                "  `created_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  `updated_by` varchar(50) DEFAULT NULL COMMENT '更新人',\n" +
                "  `created_by` varchar(50) DEFAULT NULL COMMENT '创建人',\n" +
                "  `charge` decimal(10,2) DEFAULT NULL COMMENT '收费',\n" +
                "  `effect_time` datetime DEFAULT NULL COMMENT '生效日期',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `shop_id` (`shop_id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;",sqlContents,ids);

    }
}
