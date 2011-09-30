import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import sun.tools.tree.NewArrayExpression;

//import com.wutianyi.pool.concurrent.CountTime;

/**
 * 
 */

/**
 * @author wutianyi
 * 
 */
public class PingFenTest {

	public static void main(String[] args) throws SQLException {

		MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
		final HttpClient httpClient = new HttpClient(manager);
		Connection conn =
				DriverManager.getConnection("jdbc:mysql://121.9.221.205:3306/gamecenter_opt",
						"yypriv", "3edfr46yg7");
		final MessageFormat messageFormat =
				new MessageFormat(
						"http://127.0.0.1:18080/j/ajax!gradeScore.action?privilegeId={0}&star={1}");
		Statement sm = conn.createStatement();
		ResultSet rs = sm.executeQuery("SELECT id FROM gamecenter_privilege WHERE STATUS =1");

		final List<Integer> privilegeIds = new ArrayList<Integer>();
		while(rs.next()) {
			privilegeIds.add(rs.getInt(1));
		}
		rs.close();
		sm.close();
		conn.close();
		final int maxPrivilegeId = privilegeIds.size();

//		CountTime countTime = new CountTime(new Runnable() {
//
//			Random	random	= new Random();
//
//			public void run() {
//				for(int i = 0; i < 100; i ++) {
//					
//					int privilegeId = privilegeIds.get(random.nextInt(maxPrivilegeId));
//					int star = random.nextInt(5) + 1;
//					String url = messageFormat.format(new Object[]{privilegeId, star});
//					HttpMethod method = new PostMethod(url);
//					try {
//						httpClient.executeMethod(method);
//					}catch(HttpException e) {
//						e.printStackTrace();
//					}catch(IOException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		}, 100);

		System.out.println(privilegeIds);

	}
}
