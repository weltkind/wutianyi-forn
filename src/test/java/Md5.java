import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;



public class Md5
{
    public static void main(String[] args)
    {
        String[] datas = new String[]{"10.130.23.83", "POST", "-", "-","-", "open_kingroot.html", "1", "-", "-", "-", "-", "-", "-", "-", "-",
                "-"};
        
        System.out.println(StringUtils.join(datas, " "));
    }
}
