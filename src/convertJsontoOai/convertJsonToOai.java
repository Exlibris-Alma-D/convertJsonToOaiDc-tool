package convertJsontoOai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class convertJsonToOai {
	
	private static final String XML_TO_OAI_XSL = "xml2oai.xsl";
	private static String jsonContent = "";
	private static String jsonInXMLFormat;
	public static void main(String[] args) {
		
		try{
			System.out.println("Converter-Tool json to OAI DC XML format start");
			getJson(args[0]);
			convertJsonToxml();
			convertToOAIFormat();
			System.out.println("Converter-Tool json to OAI DC XML format end");
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		

	}

	private static void convertToOAIFormat() throws TransformerException {
		// Convert to OAI using xsl
		StreamResult out = new StreamResult(new File("oai_dc_format.xml"));
		StreamSource in = new StreamSource("xmlFormat.xml");
		StreamSource xslStream = new StreamSource(XML_TO_OAI_XSL);
		 Transformer transformer = TransformerFactory.newInstance().newTransformer(xslStream);
		transformer.transform(in, out);
		
	}
	
	public static TransformerFactory getTransformerFactory() throws TransformerFactoryConfigurationError {
		TransformerFactory factory;
		String factoryClassName = "net.sf.saxon.TransformerFactoryImpl";
		try {
			factory = TransformerFactory.newInstance(factoryClassName, Thread.currentThread().getContextClassLoader());
		} catch (Exception e) {
			System.out.println("Failed creating TransformerFactory instance with factoryClassName: " + factoryClassName);
			factory = TransformerFactory.newInstance();
		}
		return factory;
	}

	private static void convertJsonToxml() throws JSONException, IOException {
		JSONObject json;
		try {
			json = new JSONObject(jsonContent);					
			jsonInXMLFormat =XML.toString(json,"root");
			OutputStream metsOutputStream = new FileOutputStream("xmlFormat.xml");
			IOUtils.write(jsonInXMLFormat, metsOutputStream,"UTF-8");
		} catch (JSONException e) {
			System.out.println("Failed to convert to XML format");
			throw e;
		}
		
	}
	private static void getJson(String sketchUrl) throws IOException {
		
		URL url = new URL(sketchUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
        	jsonContent += inputLine;
        }
        in.close();
		
	}

}
