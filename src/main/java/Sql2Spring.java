import dao.DaoClass;
import dao.DaoGenerator;
import ibaties.IbatisModel;
import sql.SqlContent;
import sql.SqlSolver;
import sql.TableEntity;

import my_java.JavaEntity;

import java.util.List;

/**
 * Created by tianrui03 on 16/8/22.
 */
public class Sql2Spring {
    private String javaContent;
    private String mapperContent;
    private String daoContent;
    public Sql2Spring(String sql ,List<String> sqls,List<String> sqlsId){
        try {
            TableEntity tableEntity = SqlSolver.solveTableEntity(sql);
            JavaEntity  javaEntity  = JavaEntity.createFromTableEntity(tableEntity);
            //不包含insert语句
            List<SqlContent>  sqlContents = SqlContent.init(sqls,tableEntity,sqlsId);
            IbatisModel model = new IbatisModel(tableEntity,javaEntity);
            DaoGenerator generator = new DaoGenerator();
            mapperContent = model.build(sqlContents);
            javaContent = javaEntity.getJavaContent();
            DaoClass daoClass =  generator.generator(sqlContents, javaEntity, tableEntity);

           System.out.println(daoClass.toString());

        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }


}
