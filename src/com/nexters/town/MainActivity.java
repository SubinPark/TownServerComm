package com.nexters.town;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.townl.R;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity implements OnClickListener{

	private Context mContext = this;
	TextView responseText;
	Button requestBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		requestBtn = (Button) findViewById(R.id.requestBtn);
		requestBtn.setOnClickListener(this);
		
		responseText = (TextView) findViewById(R.id.responseView);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.requestBtn) {
			Toast.makeText(mContext, "Request Sent!", Toast.LENGTH_SHORT).show();
			
			String relUrl = "null";
			String cd = "1102052010001";
			String req_svc = "LC0001";
			
			try {
				relUrl = "house_gateway/?JSONData=" +    
				        URLEncoder.encode("{\"req_data\":[{\"_cd\":\"" + cd +
								"\"}],\"req_svc\":\"" + req_svc +
								"\"}", "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
	        RequestClient.get(relUrl, null, new JsonHttpResponseHandler() {
	        	
	            @Override
	            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
	            	try {
	            		System.out.println(response);
	            		
	            		
	            		//JSONArray -> JSONObejct -> individual Object -> String
						JSONArray res_data = response.getJSONArray("res_data");
						JSONObject component = res_data.getJSONObject(0);
						
						String cdValue = component.get("_cd").toString();
						String cntValue = component.get("_cnt").toString();
						String itemValue = component.get("_item").toString();
						
						String result = "cd = " + cdValue + ", cnt = " + cntValue + ", item = " + itemValue;
						responseText.setText(result);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            
	            @Override
	            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
	            }
	        });
		}
		
	}
	
	
}
