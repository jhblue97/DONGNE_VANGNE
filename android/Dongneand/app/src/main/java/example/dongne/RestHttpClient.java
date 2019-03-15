package example.dongne;




import com.zagle.service.domain.Board;
import com.zagle.service.domain.Comment;
import com.zagle.service.domain.Local;
import com.zagle.service.domain.Mypage;
import com.zagle.service.domain.Page;
import com.zagle.service.domain.SearchBoard;
import com.zagle.service.domain.User;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
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
    ///Field
    ///Constructor
    public RestHttpClient(){
    }
    ///Method
    //1.1 Http Protocol GET Request : JsonSimple 3rd party lib 사용
    public User getJsonUser01(String userId) throws Exception{
         // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();

        String url= "http://192.168.0.43:8080/user/json/getUser2/"+userId.trim();
        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        //System.out.println("[ Server 에서 받은 Data 확인 ] ");
        String serverData = br.readLine();
        System.out.println("JSON : "+ serverData);

        if( serverData == null){
            return null;
        }

        //==> 내용읽기(JSON Value 확인)
        JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
        System.out.println("JSON Simple Object : " + jsonobj);

       // User user = new User();
      //  user.setUserNo(jsonobj.get("userNo").toString());
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonobj.toString(), User.class);
        System.out.println(user);
        return user;
    }


    //1.2 Http Protocol GET Request : JsonSimple + codehaus 3rd party lib 사용
    public User  getJsonUser02(String userId) throws Exception{
        System.out.println("겟유저 2 입니다 sns 넘버 가져올꺼에요"+userId);
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();

        String url= 	"http://192.168.0.43:8080/user/json/getUser/"+userId.trim();

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        //==> 다른 방법으로 serverData 처리
        //System.out.println("[ Server 에서 받은 Data 확인 ] ");
        //String serverData = br.readLine();
        //System.out.println(serverData);

        //==> API 확인 : Stream 객체를 직접 전달
        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        if( jsonobj == null){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonobj.toString(), User.class);
        System.out.println(user);

        CookieStore cookieStore1 =  ((DefaultHttpClient) httpClient).getCookieStore();
        cookieStore1.getCookies();
        System.out.println("쿠키 뭐뭠있니"+((DefaultHttpClient) httpClient).getCookieStore());
        for(Cookie cookie : cookieStore1.getCookies()){
            System.out.println("쿠키들======"+cookie.getName()+"쿠키 벨류"+cookie.getValue());
        }
        return user;
    }

    public User getJsonUser03(String userName) throws Exception{
        System.out.println("겟유저3 입니다 유저네임 가져올꺼에요"+userName);
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();

        String url= 	"http://192.168.0.43:8080/user/json/getUser3/"+userName.trim();

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        //==> 다른 방법으로 serverData 처리
        //System.out.println("[ Server 에서 받은 Data 확인 ] ");
        //String serverData = br.readLine();
        //System.out.println(serverData);

        //==> API 확인 : Stream 객체를 직접 전달
        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        if( jsonobj == null){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonobj.toString(), User.class);
        System.out.println(user);
        return user;
    }


    public User getJsonUserSession() throws Exception{
        System.out.println("겟유저 세션입니다 세션 정보 가져올거에요 ");
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();

        String url= "http://192.168.0.43:8080/user/json/getUserSession";

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        //==> 다른 방법으로 serverData 처리
        //System.out.println("[ Server 에서 받은 Data 확인 ] ");
        //String serverData = br.readLine();
        //System.out.println(serverData);

        //==> API 확인 : Stream 객체를 직접 전달
        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        if( jsonobj == null){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonobj.toString(), User.class);
        System.out.println("과연세션값이 나올지...."+user);

        return user;
    }



    public String getJsonUserNicknameCheck(String userId) throws Exception{
        System.out.println("겟유저 닉넴체크입니다"+userId);
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();


        String url= "http://192.168.0.43:8080/user/checkNickname2/"+userId.trim();

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        //==> 다른 방법으로 serverData 처리
        System.out.println("[ Server 에서 받은 Data 확인 ] ");
        String serverData = br.readLine();
        System.out.println(serverData);

        //==> API 확인 : Stream 객체를 직접 전달
      //  JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
       // System.out.println("JSON Simple Object : " + jsonobj);

      //  if( jsonobj == null){
       //     return null;
      //  }

        ObjectMapper objectMapper = new ObjectMapper();
       // String result = objectMapper.readValue(jsonobj.toString(), String.class);
        System.out.println("과연닉네임체크값이 나올지...."+serverData);

        return serverData;
    }


    public User getJsonUserUpdate(User user) throws Exception{
        System.out.println("겟유저 업데이트입니다"+user);
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();


        String url= "http://192.168.0.43:8080/user/json/userUpdate";

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        //json.put("userId","test");
        json.put("userBirth",user.getUserBirth());
        json.put("userNickname",user.getUserNickname());
        json.put("userName",user.getUserName());

        HttpEntity requestHttpEntity = new StringEntity(json.toString(),"utf-8");
        httpPost.setEntity(requestHttpEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println(httpResponse);
        System.out.println();

        HttpEntity responseHttpEntity = httpResponse.getEntity();

        InputStream is = responseHttpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

       // String serverData = br.readLine();
       // System.out.println(serverData);

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);

        ObjectMapper objectMapper = new ObjectMapper();
        User user1 = objectMapper.readValue(jsonobj.toString(),User.class);

        return user1;
    }

    public String getJsonTrade(String userId) throws Exception{
        System.out.println("겟유저 닉넴체크입니다"+userId);
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();


        String url= "http://192.168.0.43:8080/trade/json/listTrade/"+userId.trim();

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        //==> 다른 방법으로 serverData 처리
        System.out.println("[ Server 에서 받은 Data 확인 ] ");
        String serverData = br.readLine();
        System.out.println(serverData);

        //==> API 확인 : Stream 객체를 직접 전달
        //  JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        // System.out.println("JSON Simple Object : " + jsonobj);

        //  if( jsonobj == null){
        //     return null;
        //  }

        ObjectMapper objectMapper = new ObjectMapper();
        // String result = objectMapper.readValue(jsonobj.toString(), String.class);
        System.out.println("과연닉네임체크값이 나올지...."+serverData);

        return serverData;
    }

    public static Map<String,Object> listBoard_JsonSimple() throws Exception {

        HttpClient httpClient = new DefaultHttpClient();

        String url = "http://192.168.0.43:8080/board/json/listBoard";

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
        Page resultPage = objectMapper.readValue(jsonobj.get("resultPage").toString(), Page.class);
        SearchBoard searchBoard = objectMapper.readValue(jsonobj.get("searchBoard").toString(), SearchBoard.class);
        JSONArray jsonArrayBoard = (JSONArray) jsonobj.get("boardList");
        JSONArray jsonArrayLocal = (JSONArray) jsonobj.get("list");

        List<Board> boards = new ArrayList<Board>();
        List<Local> locals = new ArrayList<Local>();
        for (int i=0;i<jsonArrayBoard.size();i++){
            boards.add(objectMapper.readValue(jsonArrayBoard.get(i).toString(),Board.class));
        }
        for (int i=0;i<jsonArrayLocal.size();i++){
            locals.add(objectMapper.readValue(jsonArrayLocal.get(i).toString(),Local.class));
        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("resultPage",resultPage);
        map.put("searchBoard",searchBoard);
        map.put("listLocal",locals);
        map.put("listBoard",boards);
        return map;
    }


    public static Map<String,Object> listMyBoard_JsonSimple(String userNo) throws Exception {


        HttpClient httpClient = new DefaultHttpClient();

        String url = "http://192.168.0.43:8080/mypage/json/listMyBoard/"+userNo;

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");

        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        System.out.println(httpResponse);
        System.out.println();

        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String serverData = br.readLine();
        System.out.println("서버에서온데이터 :"+serverData);

        JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
        System.out.println("서버데이터제이슨옵젝 :"+jsonobj);

        ObjectMapper objectMapper = new ObjectMapper();
/*        Page resultPage = objectMapper.readValue(jsonobj.get("resultPage").toString(), Page.class);
        SearchBoard searchBoard = objectMapper.readValue(jsonobj.get("searchBoard").toString(), SearchBoard.class);*/
        JSONArray jsonArrayBoard = (JSONArray) jsonobj.get("bdList");
      /*  JSONArray jsonArrayLocal = (JSONArray) jsonobj.get("list");*/
        //JSONObject jsonObject = (JSONObject)

        List<Mypage> listMyPage = new ArrayList<Mypage>();
        List<Board> listMyBoard = new ArrayList<Board>();
        //  Board board = new Board();
        //  List<Local> locals = new ArrayList<Local>();
        for (int i=0;i<jsonArrayBoard.size();i++){

            listMyBoard.add(objectMapper.readValue(jsonArrayBoard.get(i).toString(),Board.class));

            //listMyPage.add(objectMapper.readValue(jsonArrayBoard.get(i).toString(),Mypage.class));
        }
        /*for (int i=0;i<listMyPage.size();i++){
            listMyBoard.add(listMyPage.get(i).getBoard());
        }*/

        /*for (int i=0;i<jsonArrayLocal.size();i++){
            locals.add(objectMapper.readValue(jsonArrayLocal.get(i).toString(),Local.class));
        }*/

        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("resultPage",resultPage);
//        map.put("searchBoard",searchBoard);
      //  map.put("listLocal",locals);
        System.out.println("restHttpClient"+listMyBoard);
        map.put("listMyBoard",listMyBoard);
        return map;
    }

    public static Map<String,Object> listMyScrap_JsonSimple(String userNo) throws Exception {


        HttpClient httpClient = new DefaultHttpClient();

        String url = "http://192.168.0.43:8080/mypage/json/listLike/"+userNo;

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");

        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        System.out.println(httpResponse);
        System.out.println();

        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String serverData = br.readLine();
        System.out.println("서버에서온데이터 :"+serverData);

        JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
        System.out.println("서버데이터제이슨옵젝 :"+jsonobj);

        ObjectMapper objectMapper = new ObjectMapper();
/*        Page resultPage = objectMapper.readValue(jsonobj.get("resultPage").toString(), Page.class);
        SearchBoard searchBoard = objectMapper.readValue(jsonobj.get("searchBoard").toString(), SearchBoard.class);*/
        JSONArray jsonArrayBoard = (JSONArray) jsonobj.get("bdList");
        /*  JSONArray jsonArrayLocal = (JSONArray) jsonobj.get("list");*/

        List<Board> listMyBoard = new ArrayList<Board>();
        //  List<Local> locals = new ArrayList<Local>();
        for (int i=0;i<jsonArrayBoard.size();i++){
            listMyBoard.add(objectMapper.readValue(jsonArrayBoard.get(i).toString(),Board.class));

        }
        /*for (int i=0;i<jsonArrayLocal.size();i++){
            locals.add(objectMapper.readValue(jsonArrayLocal.get(i).toString(),Local.class));
        }*/

        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("resultPage",resultPage);
//        map.put("searchBoard",searchBoard);
        //  map.put("listLocal",locals);
        System.out.println("restHttpClient"+listMyBoard);
        map.put("listMyBoard",listMyBoard);
        return map;
    }


    public static Map<String,Object> listMyComment_JsonSimple(String userNo) throws Exception {


        HttpClient httpClient = new DefaultHttpClient();

        String url = "http://192.168.0.43:8080/mypage/json/listComment/"+userNo;

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");

        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        System.out.println(httpResponse);
        System.out.println();

        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String serverData = br.readLine();
        System.out.println("서버에서온데이터 :"+serverData);

        JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
        System.out.println("서버데이터제이슨옵젝 :"+jsonobj);

        ObjectMapper objectMapper = new ObjectMapper();
/*        Page resultPage = objectMapper.readValue(jsonobj.get("resultPage").toString(), Page.class);
        SearchBoard searchBoard = objectMapper.readValue(jsonobj.get("searchBoard").toString(), SearchBoard.class);*/
        JSONArray jsonArrayBoard = (JSONArray) jsonobj.get("bdList");
        /*  JSONArray jsonArrayLocal = (JSONArray) jsonobj.get("list");*/

        List<Board> listMyBoard = new ArrayList<Board>();
        //  List<Local> locals = new ArrayList<Local>();
        for (int i=0;i<jsonArrayBoard.size();i++){
            listMyBoard.add(objectMapper.readValue(jsonArrayBoard.get(i).toString(),Board.class));

        }
        /*for (int i=0;i<jsonArrayLocal.size();i++){
            locals.add(objectMapper.readValue(jsonArrayLocal.get(i).toString(),Local.class));
        }*/

        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("resultPage",resultPage);
//        map.put("searchBoard",searchBoard);
        //  map.put("listLocal",locals);
        System.out.println("restHttpClient"+listMyBoard);
        map.put("listMyBoard",listMyBoard);
        return map;
    }

    public String message() throws Exception{
        System.out.println("메세지입니다");
        // HttpClient : Http Protocol 의 client 추상화
      HttpClient httpClient = new DefaultHttpClient();


        String url= "https://fcm.googleapis.com/fcm/send";

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "key=AAAAAKaJZX0:APA91bFXItHbN2Etsx8FscsI_qn8-iIPb-xSR0fskxXh0AynyFWjKpYKjc0pD_3nzishAfONi9E-fD72GKGXV-9206FSaDKWHW3W94Nrb8s_zlvfOYeh-LeQrSyUT6FXuVPrNTOkgp8b");
        JSONArray jsonArray = new JSONArray();

        /*JSONObject jsonObj= new JSONObject();
        jsonObj.put("title", "Test");
        jsonObj.put("message", "Test");
        jsonArray.add(jsonObj);*/


        JSONObject json = new JSONObject();
        //json.put("userId","test");
        json.put("to","/topics/weather");
        json.put("body","Test");
      //  json.put("userName",user.getUserName());






        HttpEntity requestHttpEntity = new StringEntity(json.toString(),"utf-8");
        httpPost.setEntity(requestHttpEntity);

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
        //JSONObject jsonobj = (JSONObject)JSONValue.parse(br);

        //ObjectMapper objectMapper = new ObjectMapper();
       // User user1 = objectMapper.readValue(jsonobj.toString(),User.class);

        return serverData;




      /*  HttpClient httpClient = new DefaultHttpClient();


        String url= "https://fcm.googleapis.com/v1/projects/dongne-2a5d8/messages:send";

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer AAAAAKaJZX0:APA91bFXItHbN2Etsx8FscsI_qn8-iIPb-xSR0fskxXh0AynyFWjKpYKjc0pD_3nzishAfONi9E-fD72GKGXV-9206FSaDKWHW3W94Nrb8s_zlvfOYeh-LeQrSyUT6FXuVPrNTOkgp8b");
        JSONObject json2 = new JSONObject();
        //json.put("userId","test");
        json2.put("body","Test");
        json2.put("title","Test");


        JSONObject json = new JSONObject();
        //json.put("userId","test");
        json.put("topic","weather");
        json.put("notification",json2);


        JSONObject jsonObj= new JSONObject();
        jsonObj.put("message",json);

System.out.println("제이슨 데이터"+jsonObj.toString());
        //  json.put("userName",user.getUserName());

        HttpEntity requestHttpEntity = new StringEntity(jsonObj.toString(),"utf-8");
        httpPost.setEntity(requestHttpEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println(httpResponse);
        System.out.println();

        HttpEntity httpEntity = httpResponse.getEntity();

        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        while((br.readLine())!= null) {
            String serverData = br.readLine();
            System.out.println("서버에서온데이터 :"+serverData);

        }

        String serverData = br.readLine();
        System.out.println("서버에서온데이터 :"+serverData);

        JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
        System.out.println("서버데이터제이슨옵젝 :"+jsonobj);
        //JSONObject jsonobj = (JSONObject)JSONValue.parse(br);

        //ObjectMapper objectMapper = new ObjectMapper();
        // User user1 = objectMapper.readValue(jsonobj.toString(),User.class);

        return serverData;*/
    }

    public static Board getBoard(String boardNo) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();
        System.out.println("서버보드:"+boardNo);
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
        System.out.println("보드 1"+board);
        List<Comment> comments = new ArrayList<Comment>();
        comments = RestHttpClient.getCommentList(board.getBoardNo());
        board.setListComment((ArrayList<Comment>) comments);
        System.out.println("보드 : "+board);
        return board;
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


    public static String getCheckLike(String userNo,String boardNo) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();

        String url = "http://192.168.0.43:8080/mypage/json/checkLike/"+userNo+"/"+boardNo;

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse httpResponse = httpClient.execute(httpGet);

        System.out.println(httpResponse);
        System.out.println();

        HttpEntity httpEntity = httpResponse.getEntity();

        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String serverData = br.readLine();
        System.out.println("값 나와줘라 ㅅ"+serverData);

       return serverData;
    }
}