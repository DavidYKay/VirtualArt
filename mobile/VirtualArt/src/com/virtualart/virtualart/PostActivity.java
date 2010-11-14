package com.virtualart.virtualart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.virtualart.virtualart.model.ArtModel;
import com.virtualart.virtualart.model.ArtSingleton;
import com.virtualart.virtualart.util.RestClient;

/**
 * The graffiti/fingerpainting activity.
 */
public class PostActivity extends Activity {
	public static final String pathToOurFile = "/sdcard/my_pix/ipad.png";
	public static final String urlServer = "http://davidykay.com:80/~dk/post.php";

	private DefaultHttpClient httpClient = new DefaultHttpClient();

	private GLSurfaceView mGLSurfaceView;

	private TextView mCodeView;
	private TextView mContentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);

		mCodeView    = (TextView)this.findViewById(R.id.code);
		mContentView = (TextView)this.findViewById(R.id.content);

		final Button postButton  = (Button)this.findViewById(R.id.post_button);
		postButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//postFile();
				postFileWithRequest();
			}
		});
		
        final Button getButton  = (Button)this.findViewById(R.id.get_button);
		getButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//getFile();
				getFileWithRequest();
			}
		});

	}

	//private void testPost() {
	//	Drawable image = getResources().getDrawable(R.raw.stevenson);

	//	HttpConnection conn = null;
	//	String url = "http://davidykay.com:8080/test/see_request/";
	//	String agent = "Mozilla/4.0";

	//	String rawData = "userid=joe&password=guessme";
	//	//String type = "application/x-www-form-urlencoded";

	//	String encodedData = encode( rawData ); // user-supplied

	//	try {
	//		conn = (HttpConnection) Connector.open( url );
	//		conn.setRequestMethod( HttpConnection.POST );
	//		conn.setRequestProperty( "User-Agent", agent );
	//		conn.setRequestProperty( "Content-Type", type );
	//		conn.setRequestProperty( "Content-Length", 
	//				encodedData.length() );

	//		OutputStream os = conn.openOutputStream();
	//		os.write( encodedData.getBytes() );

	//		int rc = conn.getResponseCode();
	//		... // process it
	//	}
	//	catch( IOException e ){
	//		// handle the error here
	//	}
	//	
	//}

	private void updateTextViews(
			int responseCode,
			String message
	) {
		mCodeView.setText("" + responseCode);
		mContentView.setText(message);
	}

	/**
	 * Raw old school method
	 */
	public void postFile() {
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;

		//String pathToOurFile = "/data/file_to_send.mp3";
		//String urlServer = "http://davidykay.com:8080/test/see_request/";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();
			Log.v("postFile", "opened url connection");

			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// Enable POST method
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			outputStream = new DataOutputStream( connection.getOutputStream() );
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
			outputStream.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// Read file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			Log.v("postFile", "Begin read bytes");

			while (bytesRead > 0) {
				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			Log.v("postFile", "End read bytes");

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			Log.v("postFile", "wrote bytes");

			// Responses from the server (code and message)
			int serverResponseCode = connection.getResponseCode();
			String serverResponseMessage = connection.getResponseMessage();

			updateTextViews(
					serverResponseCode,
					serverResponseMessage
			);

			Log.v("postFile", "closing streams");
			fileInputStream.close();
			outputStream.flush();
			outputStream.close();
			Log.v("postFile", "closed streams");
		} catch (Exception ex) {
			//Exception handling
		}
	}

	public void postFileWithRequest() {
		HttpPost post = new HttpPost(urlServer);
		String contentType = "image/png";
		//FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );
		FileEntity fileEntity = new FileEntity(
				new File(pathToOurFile),
				contentType
		);
		post.setEntity(
			fileEntity
		);
		try {
			HttpResponse response = httpClient.execute(post);

			HttpEntity entity = response.getEntity();
			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = RestClient.convertStreamToString(instream);
				//                Log.i("REST－Raw",result);
				//                result = trimResult(result);
				Log.i("REST", result);
				updateTextViews(
						response.getStatusLine().getStatusCode(),
						result
				);

				instream.close();
			}
		} catch (Exception e) {
			//eMpty
		} finally {

		}
	}
	
    public void getFileWithRequest() {
    	ArtSingleton.initSingleton(this);
		ArtModel model = ArtSingleton.getInstance().getArtModel();
		model.getNewArt();
    }
}
