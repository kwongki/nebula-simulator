package com.vantiq.simulatornebula;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.json.*;



import java.io.IOException;

public class NebulaWebSocketHandler extends AbstractWebSocketHandler {
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

    /*String msg = message.getPayload();
    JSONObject msgJson = new JSONObject(msg);
    msgJson.get("msg_id")*/ 

    JSONObject data = new JSONObject();
    JSONObject detail1 = new JSONObject();
		JSONObject detail2 = new JSONObject();

    detail2.put("person_addr", "");
    detail2.put("obj_label", 1);
    detail2.put("event_type", 0);
    detail2.put("lib_name", "Staff");
    detail2.put("pos_left", 1242);
    detail2.put("wander_channels", "");
    detail2.put("alarm_type", 0);
    detail2.put("alive_type", 0);
    detail2.put("lib_id", 2);
    detail2.put("similarity", 47);
    detail2.put("stranger_appear_channel", 0);
    detail2.put("wander_thresHold", 30);
    detail2.put("snap_frame", "----- REDACTED -----");
    detail2.put("wander_trigger", "");
    detail2.put("camera_name", "Cam-03");
    detail2.put("lib_type", 1);
    detail2.put("person_age", "");
    detail2.put("person_idcard", "1001");
    detail2.put("snap_path", "record/channel3/face/1623040030_278.jpg");
    detail2.put("img_path", "facebase/libid2/2_1620975347_1.png");
    detail2.put("ranking", 1);
    detail2.put("position", "");
    detail2.put("appear_count", 0);

    detail1.put("msg", "收到实时抓拍图片");
    detail1.put("code", 0);
    detail1.put("data", detail2);
    detail1.put("msg_id", 777);

    data.put("timeStamp", "2021-06-07T12:27:10.782");
    data.put("source_msg_uid", "1fe0ce02-289c-4fca-babf-966f7a98a702");
		data.put("messageType", "SN_event");
    data.put("data", detail1);
    data.put("parts_count", 3);

    System.out.println(data);

    TextMessage reply = new TextMessage(data.toString());
    session.sendMessage(reply);
    
    while (true) {
      try{
        session.sendMessage(reply);
        Thread.sleep(5000);
      }
      catch(InterruptedException e){
          e.printStackTrace();
      }
    }

  }

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
      System.out.println("New Binary Message Received");
      session.sendMessage(message);
  }
}
