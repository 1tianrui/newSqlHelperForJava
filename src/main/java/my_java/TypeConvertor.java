package my_java;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jnkmhbl on 16/8/22.
 */
public class TypeConvertor {
    public static Class convert(String type) throws  Exception{
        if(type.contains("int")){
            return int.class ;
        }
        if(type.contains("text") || type.contains("char")){
            return String.class ;
        }
        if(type.contains("decimal")){
            return BigDecimal.class;
        }
        if(type.contains("date")){
            return Date.class ;
        }
        throw new Exception("没有匹配类型") ;
    }
}
