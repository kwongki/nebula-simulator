package com.vantiq.simulatornebula;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@RestController
public class GeneralController {

	@PostMapping("/api/json")
	public JSONObject general(@RequestBody JSONObject params) {
		JSONObject data = new JSONObject();

		if(params.get("msg_id").equals("257")) {
			data = login();
		}else if(params.get("msg_id").equals("1028")) {
			data = getPotrait();
		}else if(params.get("msg_id").equals("1051")) {
			data = createFace();
		}else if(params.get("msg_id").equals("1281")) {
			data = getInfo();
		}else if(params.get("msg_id").equals("1301")) {
			data = getDevice();
		}else if(params.get("msg_id").equals("1286")) {
			data = getKey();
		}

		return data;
	}

	public JSONObject login() {
		JSONObject data = new JSONObject();

		data.put("code", 0);
    	data.put("data", "15066077926396217608");
		data.put("msg", "");

		return data;
	}

	public JSONObject getKey() {
		
		JSONObject data = new JSONObject();
		JSONObject user = new JSONObject();

		user.put("key", "1591148010_1");

		data.put("code", 0);
    	data.put("data", user);
		data.put("msg", "");

		return data;
	}

	public JSONObject getPotrait() {
		
		JSONObject data = new JSONObject();
		JSONObject image1 = new JSONObject();
		JSONObject image2 = new JSONObject();

		image1.put("lib_id", 3);
		image1.put("lib_name", "Visitor");
		image1.put("lib_type", 1);
		image1.put("picture_no", 0);
		image1.put("create_time", "2021-05-14 12:31:12");
		image1.put("update_time", "2021-05-14 12:31:12");

		image2.put("lib_id", 2);
		image2.put("lib_name", "Staff");
		image2.put("lib_type", 1);
		image2.put("picture_no", 4);
		image2.put("create_time", "2021-05-14 12:31:12");
		image2.put("update_time", "2021-05-14 12:31:12");

		JSONArray images = new JSONArray();
		images.add(image1);
		images.add(image2);

		data.put("code", 0);
    	data.put("data", images);
		data.put("msg", "");

		return data;
	}

	public JSONObject createFace() {
		
		JSONObject data = new JSONObject();
		JSONObject image = new JSONObject();

		image.put("img_id", "150661622565837_3077926396217608");

		data.put("code", 0);
    	data.put("data", image);
		data.put("msg", "");
		return data;
	}

	public JSONObject getInfo() {
		
		JSONObject data = new JSONObject();
		JSONObject device = new JSONObject();

		device.put("device_id", "ID");
		device.put("serial_id", "NMASX08MVXHC30094");
		device.put("version", "SenseNebula-AIE-V2.1.3-20210415");
		device.put("web_version", "SenseNebula-AIE-Web-V2.1.3-20210415");

		data.put("code", 0);
    	data.put("data", device);
		data.put("msg", "");

		return data;
	}

	public JSONObject getDevice() {
		
		JSONObject data = new JSONObject();
		JSONObject device = new JSONObject();

		device.put("device_id", "ID");

		data.put("code", 0);
    	data.put("data", device);
		data.put("msg", "");

		return data;
	}
}