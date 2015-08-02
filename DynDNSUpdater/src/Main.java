import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Main
{

	public static void main(String[] args)
	{
		Properties prop = new Properties();
		InputStream input = null;

		try
		{

			input = new FileInputStream("config.properties");

			// load a properties file from class path, inside static method
			prop.load(input);

			// get the property value and print it out
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			boolean ssl = Boolean.valueOf(prop.getProperty("ssl"));
			String apiurl = prop.getProperty("apiurl");

			// Get public ip
			String publicIP = getPublicIp(ssl, apiurl);

			String url = "https://carol.selfhost.de/update?username=" + username + "&password=" + password + "&myip=" + publicIP;

			URL selfhostURL = new URL(url);
			URLConnection conn = selfhostURL.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String answer = null;
			answer = in.readLine();
			in.close();

			if (answer != "status=200")
				System.out.println("Everything worked fine. IP was updated with ip: " + publicIP);
			else
				System.out.println("Something went wrong, selfhost.de answers: " + answer);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static String getPublicIp(boolean useHttps, String apiURL) throws IOException
	{
		URL url = useHttps ? new URL("https://" + apiURL) : new URL("http://" + apiURL);
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String ip = null;
		ip = in.readLine();
		in.close();
		return ip;
	}
}
