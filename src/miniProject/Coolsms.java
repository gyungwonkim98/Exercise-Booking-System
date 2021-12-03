package miniProject;

import java.util.HashMap;
import java.util.Random;

import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class Coolsms {
	public String createKey(){ 
		int certNumLength = 6;
        Random random = new Random(System.currentTimeMillis());
        
        int range = (int)Math.pow(10,certNumLength);
        int trim = (int)Math.pow(10, certNumLength-1);
        int result = random.nextInt(range)+trim;
         
        if(result>range){
            result = result - trim;
        }
        return String.valueOf(result);
	}
	
	public void sendSms(String tel, String msg){ 
		final String api_key = "NCSX1OBG3T3QGWYO"; 
		final String api_secret = "7UPTKYMRPB3QCZ5M1I0WHCURLCHDGNGP"; 
		
		Message coolsms = new Message(api_key, api_secret); 
		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("to", tel); 
		params.put("from", "01084623477"); 
		params.put("type", "SMS"); 
		params.put("text", msg); 
		params.put("app_version", "test app 1.2");
		
		try { 
			JSONObject obj = (JSONObject) coolsms.send(params); 
			System.out.println(obj.toString()); //전송 결과 출력 
		} catch (CoolsmsException e) { 
			System.out.println(e.getMessage()); 
			System.out.println(e.getCode()); 
		} 
	}
}
