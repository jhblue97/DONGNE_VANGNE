/**
 * Router
 * @param app
 */

module.exports = (app,Stream,request,client,url,server,queryString) => {
	  
  app.get('/', (req, res) => {
    res.render('index.ejs', {
      title: ""
    });
  }).post('/stream/inicisStream',(req,res) => {
	  console.log('/stream/inicisStream');
	  var price = req.body.price;
	  var streamerNo = req.body.streamerNo;
	  var userNo = req.body.userNo;
	console.log("route.js==========="+price+streamerNo+userNo);
	let inputData = {"price":price,"streamerNo":streamerNo,"userNo":userNo};
var input = {
		data:inputData, 
		headers: { "Content-Type": "application/json" }
	}; 
client.post("http://192.168.0.43:8080/stream/json/inicisStream", input, function (data, response) {
	  // js object로 파싱된 객체  
	  console.log(data.userNo); 
	  // 응답 객체
	 // console.log(response);
	  res.send(data.userNo);
	}); 
  }).get('/stream/stream', (req, res) => {
	 // var streamerNo = req.body.streamerNo; 
//	 var query = url.prase(req.url,true).query;
	//  console.log(JSON.stringify(query));
	 // var userNo = req.query.userNo; 
	  console.log(req.url);   
	//	console.log(streamerNo+userNo);
    res.render('examples/broadcast/index.ejs', { 
    });
  }).post('/stream/kakaoStream', (req, res) => { 
	  var price = req.body.price;
	  var streamerNo = req.body.streamerNo;
	  var userNo = req.body.userNo;
	console.log(price+streamerNo+userNo);
	//let inputData = {"price":price,"streamerNo":streamerNo,"userNo":userNo};
var input = {
		data:{"price":price,
			"streamerNo":streamerNo,
			"userNo":userNo
	}, 
		headers: { "Content-Type": "application/json",
			  		"Accept": "application/json"}
	};
client.post("http://192.168.0.43:8080/stream/json/start", input, function (data,response,body) {
	  // js object로 파싱된 객체 
	 //console.log('data======================='+data); 
	 //console.log('data.next======================='+data.next_redirect_pc_url);  
	//console.log('stringify================='+JSON.stringify(data.next_redirect_pc_url)); 
//	console.log(JSON.parse(data)); 
     
	// console.log(data.body);
	// console.log(body); 
	 // console.log('data================>'+data+'response=========>'+response);
	 res.json(data.next_redirect_pc_url);   
	  // 응답 객체
	 // console.log(response);
	});

	
  }).get('/stream/join', (req, res) => {
	  var streamer = req.query.streamer;
	  var userNo = req.query.userNo;
	  var userProfile = req.query.userProfile;
	  var userName = req.query.userNickname;
	  var offer = req.query.offer;
	  Stream.find({"streamer":streamer},function(err,streams){
		  if(err) return res.status(500).send({error:'database failure'});
		  console.log('stream스케마'+streams);
		  console.log(streams[0].streamTitle);
		  console.log(streams[0].streamerProfile);
		  console.log(streams[0].streamContent);
		  console.log(streams[0].streamNickname);
		  res.render('examples/stream/index.ejs',{ 
				  "streamer":streamer,	
				  "userNo" : userNo,
				  "streamNickname":streams[0].streamNickname,
				  "userName":userName,
				  "userProfile":userProfile,
			      "streamerProfile":streams[0].streamerProfile,
			      "streamTitle":streams[0].streamTitle,
			      "streamContent":streams[0].streamContent, 
			      "streamLikeCount":streams[0].streamLikeCount,
			      "streamViewCount":streams[0].streamViewCount,	  
			      "url":"false",
			      "testUser" : "0",
			      "sponSpeech" :"0",
			       "sponPrice"  :"0"
			    });
	   });
  }).get('/stream/add', (req, res) => {
	  var streamer = req.query.streamer;
	  var userNo = req.query.userNo; 
	  Stream.find({"streamer":streamer},function(err,streams){
		  if(err) return res.status(500).send({error:'database failure'});
		  console.log('stream스케마'+streams);
		  console.log(streams[0].streamTitle);
		  console.log(streams[0].streamerProfile);
		  console.log(streams[0].streamContent);
		  console.log(streams[0].streamNickname);
		  res.render('examples/stream/index.ejs',{
				  "streamer":streamer,		
				  "userNo" : userNo,
				  "userName":streams[0].streamNickname,
				  "userProfile":"null",
				  "streamNickname":streams[0].streamNickname, 
			      "streamerProfile":streams[0].streamerProfile,
			      "streamTitle":streams[0].streamTitle,
			      "streamContent":streams[0].streamContent, 
			      "streamLikeCount":streams[0].streamLikeCount,
			      "streamViewCount":streams[0].streamViewCount,
			      "url":"true",
			      "testUser" : "0",
			      "sponSpeech" :"0",
			       "sponPrice"  :"0"
			    });
	   });
     }).get('/conference', (req, res) => {

	  var streamer = req.query.streamer;
	  var userName = req.query.userName;
	  var offer = req.query.offer;
	  
	  var stream = new Stream({
		  streamer:"US10003",
		  streamerProfile:"default.jpg",
		  streamNickname:"user03",
		  streamTitle:"특집방송",
		  streamContent:"오늘레전드",
		  streamLikeCount:0,
		  streamViewCount:0
		  });
	  stream.save(function(err){
		  if(err){
			  console.error(err);
			  res.json({result:0});
			  return;
		  }
		  
		  res.render('examples/conference/index.ejs',{
			    //res.render('examples/conference/index.ejs?streamer='+streamer+'&userName='+userName, {
				//  res.send('streamer='+streamer+'&userName='+userName, {
				  "streamer" : streamer,
				  "userName" : userName,
				  "offer" : offer,
			      title: "- 1:1 화상회의 만들기",
			    "streams" : "하"
			    });
	  //alert(streamer)  //alert(userName);
	  });
  }).get('/stream/streamViewCount', (req, res) => {
	  var streamer = req.query.streamer;
	  Stream.find({"streamer":streamer},function(err,streams){
		  if(err) return res.status(500).send({error:'database failure'});
		  console.log('stream스케마'+streams);		   
		  res.json(streams[0].streamViewCount);   
	   });
  }).get('/stream/videochat', (req, res) => {
	  var applyuserNo = req.query.applyuserNo;
	  var userName = req.query.userName;
    res.render('examples/videochat/index.ejs', { 
    	"applyuserNo" : applyuserNo, 
		 "userName" : userName  
    });
  }).get('/stream/last', (req, res) => {
		  res.render('examples/last/broadcast.ejs',{	   
			    });
  }).get('/stream/last2', (req, res) => {
		  res.render('examples/last/index.ejs',{ 
				
	   }); 
  }).get('/stream/speech', (req, res) => {
    res.render('examples/speech/index.ejs', {
      title: "- 다이나믹 레졸루션"
    });
  });
  
  app.get('/stream/mine', function(req,res){
	  res.render('examples/mine/index.ejs', {
	    });
  });

  // GET SINGLE BOOK
  app.get('/stream/speech3', function(req, res){
	  res.render('examples/speech3/index.ejs', {
	    });
  });

  // GET BOOK BY AUTHOR
  app.get('/stream/speech4', function(req, res){
	  res.render('examples/speech4/index.ejs', {
	    });
  });
  
  // GET BOOK BY AUTHOR
  app.get('/stream/sponSpeech', function(req, res){
	
	  var streamer = req.query.streamer;
	  var userNo = req.query.userNo;
	  var userProfile = req.query.userProfile;
	  var userName = req.query.userNickname;
	  var price = req.query.price;
	    var msg = userName+'님이'+price+'원을 후원하셨습니다!!우와~~~';
  	    console.log(msg); 
  	    console.log('route========================='+userName);
 	  Stream.find({"streamer":streamer},function(err,streams){
		  if(err) return res.status(500).send({error:'database failure'});
		  console.log('stream스케마'+streams);
		  console.log(streams[0].streamTitle);
		  console.log(streams[0].streamerProfile);
		  console.log(streams[0].streamContent);
		  console.log(streams[0].streamNickname);
		  res.render('examples/stream/index.ejs',{ 
				  "streamer":streamer,	
				  "userNo" : userNo,
				  "streamNickname":streams[0].streamNickname,
				  "userName":userName,
				  "userProfile":userProfile,
			      "streamerProfile":streams[0].streamerProfile,
			      "streamTitle":streams[0].streamTitle,
			      "streamContent":streams[0].streamContent, 
			      "streamLikeCount":streams[0].streamLikeCount,
			      "streamViewCount":streams[0].streamViewCount,	  
			      "url":"false",
			      "testUser" : "0",
			      "sponSpeech" :"1",
			      "sponPrice" : price
			    });
	   });  
  });
   

  // CREATE BOOK
  app.get('/stream/sponSpeech2', function(req, res){
	  var streamer = req.query.streamer;
	  var userNo = req.query.userNo;
	  var userProfile = req.query.userProfile;
	  var userNickname = req.query.userNickname; 
	  var price = req.query.price;
	  var testUser = 'USTest'; 
	  console.log(req.url);
	  console.log(decodeURIComponent(req.url));
	  console.log(req.query.userNickname);
	  console.log(req.query.userNickname);
	  console.log(decodeURI(req.query.userNickname));
	//  var parsedUrl = url.parse(request.url);
	//  console.log(parseUrl);
//	  var parsedQuery = queryString.parse(parsedUrl.query,'&','=');
	//  console.log(parsedQuery);
	  
	  
	   console.log('route========================='+userNickname);
	   console.log('route========================='+price)
	 	 
		  Stream.find({"streamer":streamer},function(err,streams){
		  if(err) return res.status(500).send({error:'database failure'});
		  console.log('stream스케마'+streams);
		  console.log(streams[0].streamTitle);
		  console.log(streams[0].streamerProfile);
		  console.log(streams[0].streamContent);
		  console.log(streams[0].streamNickname);
		  res.render('examples/stream/index2.ejs',{ 
				  "streamer":streamer,	
				  "userNo" : testUser,  
				  "streamNickname":streams[0].streamNickname,
				  "userName":userNickname, 
				  "userProfile":userProfile,
			      "streamerProfile":streams[0].streamerProfile,
			      "streamTitle":streams[0].streamTitle,
			      "streamContent":streams[0].streamContent, 
			      "streamLikeCount":streams[0].streamLikeCount,
			      "streamViewCount":streams[0].streamViewCount,	  
			      "url":"false",
			      "testUser" : testUser,
			      "sponSpeech" :"1",
			      "sponPrice" : price
			    });
	   });  
  });

  // UPDATE THE BOOK
  app.get('/stream/textSpeech', function(req, res){
	  res.render('examples/textSpeech/index.ejs',{ 
	
	    });
  });

  // DELETE BOOK
  app.delete('/api/books/:book_id', function(req, res){
      res.end();
  });
  
};

