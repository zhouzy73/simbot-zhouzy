package love.simbot.example.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {

    public static void download(String urlStr, String path, String filename) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            File file = new File(path + filename);
            FileOutputStream fos = new FileOutputStream(file);
            while ((len = is.read(bs)) != -1) {
                fos.write(bs, 0, len);
            }
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
