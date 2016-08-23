import java.io.*;

/**
 * Created by jnkmhbl on 16/8/23.
 */
public class FileWriter {
    public static boolean write(String filename ,String content){
        try {
            File file = new File(filename);
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file));
            writer.write(content.toCharArray());
            writer.flush();
            writer.close();
            return true ;
        }catch (Exception e){
            System.out.println("write error");
            return false ;
        }
    }
}
