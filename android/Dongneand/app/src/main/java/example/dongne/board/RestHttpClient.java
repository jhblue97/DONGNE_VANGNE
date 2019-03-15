package example.dongne.board;

import com.zagle.service.domain.Board;
import com.zagle.service.domain.Comment;
import com.zagle.service.domain.Local;
import com.zagle.service.domain.Page;
import com.zagle.service.domain.SearchBoard;
import com.zagle.service.domain.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestHttpClient {


	public static boolean login(String userId) throws Exception {
		Boolean loginCheck=false;
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.38:8080/user/json/getUser2/"+userId;

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		}catch (Exception e){
			e.printStackTrace();
		}


		String serverData = br.readLine();
		System.out.println(serverData);

		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		if (jsonobj!=null){
			if (jsonobj.get("userNo").toString().equals(userId)){
				loginCheck=true;
			}
		}

		return loginCheck;
	}

	public static int addLike(String userNo,String boardNo) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/board/json/addLike/"+userNo+"/"+boardNo;

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));


		System.out.println("애드라이크!");
		return 1;
	}
	public static int updateLike(String userNo,String boardNo,String checkLike) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/board/json/updateLike/"+userNo+"/"+boardNo+"/"+checkLike;

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		int result=0;

		if(checkLike.equals("1")) {
			result=2;
		}else if(checkLike.equals("2")) {
			result=1;
		}

		return result;
	}

	public static User getUser(String userNo) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/user/json/getUser2/"+userNo;

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		JSONObject jsonObject = (JSONObject) JSONValue.parse(br);
		System.out.println("서버보드:"+jsonObject);

		ObjectMapper objectMapper = new ObjectMapper();

		User user = new User();
		user = objectMapper.readValue(jsonObject.toString(),User.class);
		System.out.println("유저 : "+user);
		return user;
	}
	public static Board getBoard(String boardNo) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/board/json/getBoard/"+boardNo;

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		JSONObject jsonObject = (JSONObject) JSONValue.parse(br);
		System.out.println("서버보드:"+jsonObject);

		ObjectMapper objectMapper = new ObjectMapper();

		Board board = new Board();
		board = objectMapper.readValue(jsonObject.toString(),Board.class);
		System.out.println("보드 : "+board);
		return board;
	}

	public static void addComment(String userNo,String boardNo,String detailText) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/board/json/addCommentOne?userNo="+userNo+"&boardNo="+boardNo;

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("detailText",detailText);
		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		System.out.println("등록완료~");
	}

	public static List<Comment> getCommentList(String boardNo) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/board/json/listComment/"+boardNo;

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		JSONArray jsonArray = (JSONArray) JSONValue.parse(br);
		System.out.println("서버댓글리스트:"+jsonArray);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Comment> listComment = new ArrayList<Comment>();

		for (int i=0;i<jsonArray.size();i++){
			listComment.add(objectMapper.readValue(jsonArray.get(i).toString(),Comment.class));
		}
		System.out.println("리스트커맨트 : "+listComment);
		return listComment;
	}

	public static Map<String,Object> listBoard_JsonSimple(String userNo) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://192.168.0.43:8080/board/json/listBoard?userNo="+userNo;

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		String serverData = br.readLine();
		System.out.println("서버에서온데이터 :"+serverData);

		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println("서버데이터제이슨옵젝 :"+jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		JSONArray jsonArrayBoard = (JSONArray) jsonobj.get("boardList");
		JSONArray jsonArrayLocal = (JSONArray) jsonobj.get("list");

		List<Board> boards = new ArrayList<Board>();
		List<Local> locals = new ArrayList<Local>();
		List<Comment> comments = new ArrayList<Comment>();
		Board board = new Board();
		for (int i=0;i<jsonArrayBoard.size();i++){
			board = objectMapper.readValue(jsonArrayBoard.get(i).toString(),Board.class);
			comments = RestHttpClient.getCommentList(board.getBoardNo());
			board.setListComment((ArrayList<Comment>) comments);
			boards.add(board);
		}
		for (int i=0;i<jsonArrayLocal.size();i++){
			locals.add(objectMapper.readValue(jsonArrayLocal.get(i).toString(),Local.class));
		}

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("listLocal",locals);
		map.put("listBoard",boards);
		return map;
	}

}