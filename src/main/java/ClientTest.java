import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * 
 */

/**
 * @author wutianyi
 *
 */
public class ClientTest {
	public static void main(String[] args) throws HttpException, IOException {

		MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
		final HttpClient httpClient = new HttpClient(manager);
		HttpMethod method = new GetMethod("http://127.0.0.1:18080/j/ajax!gradeScore.action?privilegeId=739&star=5");
		httpClient.executeMethod(method);
		System.out.println(method.getResponseBodyAsString());
	}
}
