import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class BusinessSearch {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		URL solr = new URL("http://localhost:8983/solr/gettingstarted/select?indent=on&q=" + args[0] + "&wt=json");
		HttpURLConnection file = (HttpURLConnection) solr.openConnection();
		file.setDoOutput(true);
		file.setDoInput(true);
		file.setRequestMethod("GET");
		file.connect();

		StringBuffer s = new StringBuffer("");

		BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		while((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();		
	}
}
