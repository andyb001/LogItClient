package com.whittle.logit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.whittle.logit.dto.ItemDTO;
import com.whittle.logit.dto.ItemTypeDTO;

public class Service {

	public Service() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<ItemTypeDTO> getItemTypes() {
		List<ItemTypeDTO> itemTypeDTOList = new ArrayList<>();
		try {
			HttpClient client = new DefaultHttpClient();
			//HttpGet request = new HttpGet("http://192.168.1.108:8085/admin/get-item-types");
			HttpGet request = new HttpGet("http://localhost:8085/admin/get-item-types");
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
			  sb.append(line);
			}
			JSONArray array = new JSONArray(sb.toString());
			ItemTypeDTO itemTypeDTO;
			for (int i = 0; i < array.length(); i++) {
				itemTypeDTO = new ItemTypeDTO();
				itemTypeDTO.setDescription(array.getJSONObject(i).getString("description"));
				itemTypeDTO.setId(array.getJSONObject(i).getString("id"));
				itemTypeDTO.setName(array.getJSONObject(i).getString("name"));
				itemTypeDTOList.add(itemTypeDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemTypeDTOList;
	}
	
	public static int saveItem(ItemDTO itemDTO) throws ClientProtocolException, IOException {
		JSONObject jsonObj = new JSONObject( itemDTO );
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:8085/admin/save-item");
		System.out.println(jsonObj.toString());
		StringEntity entity = new StringEntity(jsonObj.toString());
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	    HttpResponse response = client.execute(httpPost);
	    return response.getStatusLine().getStatusCode();
	}
	
	public static void main(String[] args) {
		
		System.out.println(getItemTypes());
		
	}
}
