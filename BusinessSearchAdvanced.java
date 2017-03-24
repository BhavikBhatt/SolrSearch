import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BusinessSearchAdvanced {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
				
		URL solr = new URL(createQuery(args[0]));
		HttpURLConnection file = (HttpURLConnection) solr.openConnection();
		file.setDoOutput(true);
		file.setDoInput(true);
		file.setRequestMethod("GET");
		file.connect();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		while((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();	
	}

	public static String createQuery(String search) throws IOException {
		String[] strings = search.split(" ");
		String s = strings[0];
			for(int i = 1; i < strings.length; i++) {
				s += "+" + strings[i];
			}
		
		
		String q = "q=" + s;
		String query = "http://localhost:8983/solr/gettingstarted/select?indent=on&wt=json&" + q + "&defType=dismax";
		try {
			int number = Integer.parseInt(s);
			query += "&qf=zip^20.0+name^1.0+desc^1.0+cat^1.0";	
		} catch (Exception e) {
			String filename = "/Users/Bhavik/bhavik/officework/software/search/solr-6.0.1/server/solr/configsets/data_driven_schema_configs/conf/synonyms.txt";
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuffer str = new StringBuffer();
			String line;
			boolean stateFound = false;
			while((line = br.readLine()) != null) {
				str.append(line);
				if(line.contains(search)) {
					stateFound = true;
				}
			}
			
			if(stateFound) {
				query += "&qf=state^20.0+city^5.0+zip^2.0+name^1.0+desc^1.0+cat^1.0";
			} else {
				query += "&qf=city^5.0+zip^2.0+state^1.0+name^1.0+desc^1.0+cat^1.0";
			}
		}
		return query;
	}
	
}
