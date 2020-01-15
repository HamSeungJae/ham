package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Kyo {
	public static void main(String[] args) {
		
		Kyo kyo = new Kyo();
		kyo.run();
	}
	
	private void run() {

		Scanner sc = new Scanner(System.in);
		String params = "{"
				+ "\"appGrpKey\":"+"\"f5174c63d1d44a3297fb\""
				+ ",\"page\":"+"\"1\""
				+ ",\"limit\":"+"\"1\""
				+ "}";
		Set<String> allMembers = new HashSet<String>();
		Set<String> pushYMembers = new HashSet<String>();		
		apiPingTest("https://openapi.pushpia.cn:444/camp/allTarget",params);
		int totalCnt = 7924;
		int limitSize = 5000;
		
		Set<String> spiltedPushYMembers;
		
		for(int l=0; l<10; l++) {
			
			totalCnt=7924;
			pushYMembers.clear();
			System.out.println("########### l : "+l+"#########");

			System.out.println("  %%  "+pushYMembers);
			for( int i=1; totalCnt>0 ; totalCnt-=limitSize,i++) {
//				System.out.println(totalCnt);
				params = "{"
						+ "\"appGrpKey\":"+"\"f5174c63d1d44a3297fb\""
						+ ",\"page\":"+"\""+i+"\""
						+ ",\"limit\":"+"\""+limitSize+"\""
						+ "}";
				spiltedPushYMembers = new HashSet<String>(Arrays.asList(apiPingTest("https://openapi.pushpia.cn:444/camp/allTarget",params)));

				System.out.println(spiltedPushYMembers);
				pushYMembers.addAll(spiltedPushYMembers);
			}	
			System.out.println("  %%  "+pushYMembers);
			System.out.println("  %%  size:"+pushYMembers.size());
		}
		
//		long startTime = System.currentTimeMillis();
		
//		for(int i=0; i<1000000;i++) {
//			allMembers.add("1810990"+String.format("%06d", i));
//		}
		
//		for(int i=0; i<10000;i++) {
//			pushYMembers.add("1810990"+String.format("%06d", i));
//		}
		
//		System.out.println(apiPingTest("https://openapi.pushpia.cn:444/camp/allTarget",params));
		
//		long elapsedTime = System.currentTimeMillis()-startTime;
		
//		System.out.println("elapsedTime : "+elapsedTime);
		
		
//		do {
//			
////			startTime = System.currentTimeMillis();
//			Set<String> rsMember = intersection(allMembers,pushYMembers);
////			elapsedTime = System.currentTimeMillis()-startTime;
////			System.out.println("elapsedTime : "+elapsedTime);
//
////			startTime = System.currentTimeMillis();
//			List<String> lList = new ArrayList<String>(rsMember);
////			elapsedTime = System.currentTimeMillis()-startTime;
////			System.out.println("elapsedTime : "+elapsedTime);
//
//			System.out.print("계속하려면 1이아닌 수를 입력하십시오");
//		}while(sc.nextInt()!=1);

	}
	
	private <T> Set<T> intersection(Set<T> list1, Set<T> list2){
		Set<T> smaller;
		Set<T> bigger;
		
		Set<T> list = new HashSet<T>();
		 
		if(list1.size()<= list2.size()) {
			smaller = list1;
			bigger = list2;
		}else {
			smaller = list2;
			bigger = list1;
		}
		
	    for (T t : smaller) {
	        if (bigger.contains(t)) {
	            list.add(t);
	        }
	    }
	    return list;
	}
	
	private  String[] apiPingTest(String reqUrl, String sendData) {
		
		HttpURLConnection httpURLConnection = null;
		InputStream in = null;
		OutputStream out = null;
		
		try {
			
			// url 생성
			URL sendUrl = new URL(reqUrl);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();

			// 커넥션 timeout 셋팅
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.setRequestProperty("X-PUSHPIA-API-KEY", "f0182c53ae6f0d27e2de04055b6e638f908422fbe1a8b6078ff51f4fd41ab512");
			httpURLConnection.setRequestMethod("POST");

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes("UTF-8"));
			out.flush();
			
//			return httpURLConnection.getResponseCode();
			
			in = httpURLConnection.getInputStream();
			
			String inputLine = null;
	        StringBuffer return_str = new StringBuffer();
			BufferedReader httpReader = new BufferedReader(new InputStreamReader(in));
			while ((inputLine = httpReader.readLine()) != null) {
        		return_str.append(inputLine).append("\n");
	        }
			
	        httpReader.close();
	        
	        return return_str.substring(60,return_str.length()-2).split(",");

		} catch (Exception e) {
			
			// 실패 시 999 리톤
			System.out.println("[" + reqUrl + "]"+e);
			return null;
		} finally {
			
			// 커넥션 close
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
			
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in=null;
			}
			
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out=null;
			}
		}
	}
}
