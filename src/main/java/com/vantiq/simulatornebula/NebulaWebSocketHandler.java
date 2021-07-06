package com.vantiq.simulatornebula;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.json.*;

import java.io.IOException;
import java.lang.Runnable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Configuration
@PropertySource(name = "myProperties", value = "app.properties")
public class NebulaWebSocketHandler extends AbstractWebSocketHandler {

  // expecting input.file in app.properties or from commandline argument --input.file="xxxx"
  @Value("${input.file}")
  private String inputFileName;

  // # of records to send in a batch
  @Value("${output.batchSize:1}")
  private int batchSize; 

  // # of seconds to wait in between batches
  @Value("${output.wait:1}")
  private int wait;

  // max # of records to replay from input file
  @Value("${output.maxSize:0}")
  private int maxSize;

  //@Autowired
	//private Environment env;
  
  LoadContentsRunnable loadThread = null;
  
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
    String msg = message.getPayload();
    //System.out.println(msg);
    JSONObject msgJson = new JSONObject(msg);
    String msg_id = msgJson.getString("msg_id");
    System.out.println("Got this for input.file = " + inputFileName);
    //System.out.println(env.toString());
    //System.out.println(env.getProperty("testProp"));

    if (msg_id.equals("776")) {
      System.out.println("Got msg_id = 776 --> Start sending messages");
      if (inputFileName != null) {
        loadThread = new LoadContentsRunnable(session, inputFileName, batchSize, wait, maxSize); //"/Users/kwongki/work/PrimusTech_COC/recombinedMsg.json");
        Thread thread = new Thread(loadThread);
        thread.start();
      }
      

      /**
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
      **/
      //System.out.println(data);

      //TextMessage reply = new TextMessage(data.toString());
      //session.sendMessage(reply);
      /** 
      while (isRunning) {
        try{
          session.sendMessage(reply);
          Thread.sleep(5000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
      } **/
    } else if (msg_id.equals("9999")) {
      System.out.println("Got msg_id = 9999 --> Stop sending messages");
      if (loadThread != null) {
        loadThread.stopRunning();
      }
    }
  }

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
      System.out.println("New Binary Message Received");
      session.sendMessage(message);
  }

  class LoadContentsRunnable implements Runnable {

    private boolean isRunning = false;
    WebSocketSession _session = null;
    String _file = "";
    int _batchsize = 1;
    int _waitsecs = 1;
    int _maxsize = 0;

    public LoadContentsRunnable(WebSocketSession session, String inputFile, int batchSize, int waitSecs, int maxSize) {
      this._session = session;
      this._file = inputFile;
      this._batchsize = batchSize;
      this._waitsecs = waitSecs;
      this._maxsize = maxSize;
    }

    public synchronized void stopRunning() {
      isRunning = false;
    }

    public synchronized boolean isRunning() {
      return isRunning;
    }

    @Override
    public void run() {
      this.isRunning = true;
      
      StringBuilder contentBuilder = new StringBuilder();

      try (Stream<String> stream = Files.lines( Paths.get(this._file), StandardCharsets.UTF_8))
      {
        stream.forEach(s -> contentBuilder.append(s).append("\n"));
      } catch (IOException e) {
        e.printStackTrace();
      }

      JSONArray results = new JSONArray(contentBuilder.toString());

      try {
        int batchCount = 0;
        int i = 0;
        for (i = 0; i<results.length() && isRunning() && i<this._maxsize; i++) {
          JSONObject curObj = results.getJSONObject(i);
          TextMessage reply = new TextMessage(curObj.toString());
          this._session.sendMessage(reply);
          batchCount++;
          if (batchCount == this._batchsize) {
            Thread.sleep(this._waitsecs * 1000);
            batchCount = 0;
          }
        }
        System.out.println(".... Done replaying events (sent #"+ i +")....");

      } catch (InterruptedException e1) {
        e1.printStackTrace();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
      
    }
  }
  
}
