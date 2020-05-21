package PCClient.Http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import PCClient.JavaSwing.RemainMessageFrame;
import PCClient.Module.*;
import PCClient.Module.Shutdown;
import PCModel.PC;

public class PCGet {
	private PCGet() {

	}

	private static PCGet instance;

	public static PCGet getInstance() {
		if (instance == null) {
			instance = new PCGet();
		}
		return instance;
	}

	public void GetMethod(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.208.19/pc/"+pc.getId()+"/message/" + pc.getRemainTime());
		System.out.println("GetMethod URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(new HttpGet(uri));
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		System.out.println("GetMethod Response = " + content);
		try {
			JsonElement jsonElement = JsonParser.parseString(content);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			if (jsonObject.get("msg").isJsonNull() || jsonObject.get("msg").isJsonNull()) { // PC가 연장 신청 했을 때 혹은 다른 경우
				System.out.println("-----Get Long-Polling Response is null-----");
				// pc.setRemainTime("30");
			} else { // 메시지가 왔을 때 처리
				String id = jsonObject.get("id").getAsString();
				String message = jsonObject.get("msg").getAsString();
				String endTime = jsonObject.get("endTime").getAsString();
				if (message.equals("extension")) {
					String nowTime = GetNowTime.getInstance().getNowTime();
					System.out.println("nowTime = " + nowTime);
					int remainTime = (int)Double.parseDouble(TimeDifference.getInstance().calc(nowTime, endTime));
					System.out.println("remainTime = " + remainTime);
					if(remainTime<=0) {
						pc.setPower_status("OFF");
						pc.setRemainTime("-1");
						System.exit(0);
					}
					else if (remainTime < 5 && remainTime > 0) { // 1~5분 남았을 때
						pc.setRemainTime("0");
					} else if (remainTime >= 5 && remainTime < 10) {
						pc.setRemainTime("5");
					} else if (remainTime >= 10 && remainTime < 30) {
						pc.setRemainTime("10");
					} else if (remainTime >= 30) {
						pc.setRemainTime("30");
					}
					pc.setEnd_time(endTime);
				} else {
					if (pc.getRemainTime().equals("30")) {
						pc.setRemainTime("10");
					} else if (pc.getRemainTime().equals("10")) {
						pc.setRemainTime("5");
					} else if (pc.getRemainTime().equals("5")) {
						message+="분 남았습니다.";
						System.out.println(message);
						RemainMessageFrame.getInstance().show(message);
						pc.setRemainTime("0");
					} else if (pc.getRemainTime().equals("0")) {
						//Shutdown.getInstance().shutdown("300");
						pc.setPower_status("OFF");
						pc.setRemainTime("-1");
						System.exit(0);
					}
					if (!pc.getRemainTime().equals("0")) {
						message+="분 남았습니다.";
						System.out.println(message);
						RemainMessageFrame.getInstance().show(message);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Get Long-Polling Error");
			e.printStackTrace();
		}
	}
}
