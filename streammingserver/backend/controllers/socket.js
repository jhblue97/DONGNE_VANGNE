/**
 * Websocket handler
 * @param http
 */
module.exports = (server,Stream) => {
  const	io = require('socket.io').listen(server);
  let rooms = {};
  let roomId = null;
  let socketIds = {};
  let sockets = {};
  /** 
   * SocketId로 방을 탐색 합니다.
   * @param value
   * @returns {*}
   */
  function findRoomBySocketId(value) {
    const arr = Object.keys(rooms);
    console.log('findroom=='+arr);
    console.log('value=='+value);
    let result = null;

    for (let i=0; i < arr.length; i++) {
      if (rooms[arr[i]][value]) {
        result = arr[i];
        
        console.log('result==='+result);
        break;
      }
    }

    return result;
  }

  function finduserNameBySocketId(roomId,value) {
	  console.log('value=='+value);
	  console.log('roomId==='+roomId);
	
	    const arr = Object.keys(rooms);
	    var username = rooms[roomId][value];
	    return username;
	  }
  
  /**
   * 소켓 연결
   */
  
  io.on('connection', (socket) => {
  
	  var roomId = findRoomBySocketId(socket.id);
	  
	  
	  
	  socket.on('enter', (roomName, userId) => {
		   io.to(roomId).emit('jsp');         
	    	console.log('socket.js=userId=='+userId);
	      roomId = roomName;
	      socket.join(roomId);  // 소켓을 특정 room에 binding합니다.
	      console.log(rooms[roomId]);
	      // 룸에 사용자 정보 추가, 이미 룸이 있는경우
	      if (rooms[roomId]) {
	    	  if(roomId=='' ||roomId==null){
	    		  console.log('이건 한번만 떠야되는데에ㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔ');
	    	  }else{ 
	    			 if(Object.keys(rooms[roomId]).length!=0){
	    		    Stream.update({"streamer":roomId},{$inc:{"streamViewCount":+1}},function(err){
	    		    	   if(err){
	    		    		   console.log('조회수실패 왜지?ㅠㅠ'); 
	    		    	   }else{
	    	    		 console.log('조회수추가완료'+roomId); 
	    	    		  io.to(roomId).emit('streamViewCount','증가');        
	    		    	   }    	
	    		    	   
	    	    	   });//   Stream.update({"streamer":roomId},{$set:{"streamLikeCount":+1}});  
	    			 }
	    	 }
	        console.log('이미 룸이 있는 경우');
	  	  console.log('접속한 유저 소켓 아이디==========='+socket.id+'그사람의 룸 아이디========='+roomId); 
	        rooms[roomId][socket.id] = userId;
	    
	        // 룸 생성 후 사용자 추가
	      } else {
	        console.log('룸 추가');
	        rooms[roomId] = {};
	        rooms[roomId][socket.id] = userId;
	      }
	      thisRoom = rooms[roomId];
	      console.log('thisRoom', thisRoom);

	      // 유저 정보 추가
	      io.sockets.in(roomId).emit('join', roomId, thisRoom);
	      //console.log('ROOM LIST', io.sockets.adapter.rooms);
	      console.log('ROOM LIST', rooms);
	    }); 
	  
	  
	  
	  
	  socket.on('disconnect', () => {
   	//console.log('data가 뭐야'+data);
       console.log('a user disconnected', socket.id);
       const roomId = findRoomBySocketId(socket.id);
       console.log('찾은 roomId=='+roomId);
       if(roomId!=null){
      var name =  finduserNameBySocketId(roomId,socket.id);
      console.log('찾은 유저네임=='+name);
      if (roomId) {
          // 자신 제외 룸안의 유저ID 전달
          delete rooms[roomId][socket.id]; // 해당 유저 제거
        }
    if(roomId==name){
     	 console.log('같다');
     	 console.log('소켓들'+Object.keys(rooms[roomId]).length);
        Stream.findOneAndRemove({"streamer":name},function(err){
    		 console.log('삭제완료');
    	   });
     	    
     	 if(Object.keys(rooms[roomId]).length!=0){
     	 Object.keys(io.sockets.adapter.rooms[roomId].sockets).forEach(function(id) {
     		    console.log("ID:",id)  // socketId
     		    socket.broadcast.to(roomId).emit('leave',rooms[roomId][id]);
     		   //socket.leave(roomId);
     		  //  delete rooms[roomId][id]; 
     		})
     	 }
     	 
      }else{
    	 
    	  Stream.update({"streamer":roomId},{$inc:{"streamViewCount":-1}},function(err){
	    	   if(err){
	     	   }else{
	    		   io.to(roomId).emit('streamViewCount','감소');         
   	 console.log('조회수삭제완료'); }
  	 	   });	  
      } 
    
       }
     });
	    
	  socket.on('broadcaster',(roomName, userId) => {
		  console.log('broadcaster 연결'+roomName, userId); 
		    broadcaster = socket.id;
		    console.log('broadcaster socket id=='+socket.id)
		    socket.broadcast.emit('broadcaster');
		    
		    
		     roomId = roomName;
		      socket.join(roomId);  // 소켓을 특정 room에 binding합니다.

		      // 룸에 사용자 정보 추가, 이미 룸이 있는경우
		      if (rooms[roomId]) {
		        console.log('이미 룸이 있는 경우');
		        rooms[roomId][socket.id] = userId;
		        // 룸 생성 후 사용자 추가
		      } else {
		        console.log('룸 추가');
		        rooms[roomId] = {};
		        rooms[roomId][socket.id] = userId;
		        console.log('룸만들때 소켓 아이디=='+socket.id)
		      }
		      thisRoom = rooms[roomId];
		      console.log('thisRoom', thisRoom);
		      // 유저 정보 추가
		      io.sockets.in(roomId).emit('join', roomId, thisRoom);
		      //console.log('ROOM LIST', io.sockets.adapter.rooms);
		      console.log('ROOM LIST', rooms);
		  });	  
	  socket.on('watcher', function () {
		  console.log('watcher==='+broadcaster); 
		  console.log('watcher socket.id=='+socket.id);
		    broadcaster && socket.to(broadcaster).emit('watcher', socket.id);
		  });
		  socket.on('offer', function (id /* of the watcher */, message) {
			  
		    socket.to(id).emit('offer', socket.id /* of the broadcaster */, message);
		  });
		  socket.on('answer', function (id /* of the broadcaster */, message) {
		    socket.to(id).emit('answer', socket.id /* of the watcher */, message);
		  });
		  socket.on('candidate', function (id, message) {
		    socket.to(id).emit('candidate', socket.id, message);
		  });
			  //  broadcaster && socket.to(broadcaster).emit('bye', socket.id);
	    // 룸접속
    socket.on('enter', (roomName, userId) => {
    	console.log('socket.js=userId=='+userId);
    	console.log('enter접속 roomName'+roomName+'userId='+userId);
      roomId = roomName;
      socket.join(roomId);  // 소켓을 특정 room에 binding합니다.

      // 룸에 사용자 정보 추가, 이미 룸이 있는경우
      if (rooms[roomId]) {
        console.log('이미 룸이 있는 경우');
        rooms[roomId][socket.id] = userId;
        // 룸 생성 후 사용자 추가
      } else {
        console.log('룸 추가');
        rooms[roomId] = {};
        rooms[roomId][socket.id] = userId;
        console.log('룸만들때 소켓 아이디=='+socket.id)
      }
      thisRoom = rooms[roomId];
      console.log('thisRoom', thisRoom);
      // 유저 정보 추가
      io.sockets.in(roomId).emit('join', roomId, thisRoom);
      //console.log('ROOM LIST', io.sockets.adapter.rooms);
      console.log('ROOM LIST', rooms);
    });

    socket.on('send message', function(name,text,img,userNo){
    	console.log('send message=========도착'); 
    	console.log('text='+text+'userNo'+userNo+'img'+img+'name'+name); 
    	console.log(socket.id);
    	const roomId = findRoomBySocketId(socket.id); 
    	console.log('찾은 roomId=='+roomId); 
    	var profile = '<img src="http://localhost:8080/common/images/profile/'+img+'"style="height:30px; width:30px; border-radius:70px; display:inline; vertical-align:middle">';
    	var nameTag = '<a class="nameTag" data-userName="'+name+'" data-userNo="'+userNo+'">'+name+'</a>';
    	var msg = profile + nameTag + ' : ' + text;  
    	var checkNo = userNo; 
    	var userName = name;
    	console.log('sendmessage========================'+userName);
    	console.log(msg);  
 //   io.to(roomId).emit('receive message', msg,checkNo);
    	socket.broadcast.to(roomId).emit('receive message',msg,checkNo,userName); 
    });

    socket.on('ban', function(userNo,username,streamerId){
   	 const roomId = findRoomBySocketId(socket.id);
   	    console.log('찾은 roomId=='+roomId);
   	 var name =  finduserNameBySocketId(roomId,socket.id);
     console.log('찾은 유저네임=='+name);
      console.log('벤할예정입니당'+userNo+'스트리머==='+streamerId); 
      Object.keys(io.sockets.adapter.rooms[roomId].sockets).forEach(function(id) {
  	    var username2 = rooms[roomId][id];
  	    if(userNo==username2){
  	    	console.log("벤할놈 username:",username2,"ID:",id,"name :",name,"userName :",username);
  	    	socket.to(id).emit('realban',id); 
  	     io.to(roomId).emit('realbanMessage',username); 
	      Stream.update({"streamer":streamerId},{$push:{"banList":{"userNo":userNo}}},function(err){ 
	    	   if(err){ 
	     	   }else{
   		 console.log('벤리스트추가완료'); 
	    	   }    	
	    	   
   	   });  
  	       
  	    } 	  		
      });
      
    });
    
    socket.on('sponSpeech', function(userNo,sponPrice,userName,streamerId){ 
 	   console.log('sponSpeech socket.io============'+userNo+sponPrice+userName+streamerId);
    	    var msg = userName+'님이 '+sponPrice+'원을 후원하셨습니다!!';
   	    console.log(msg); 
   	 // io.to(streamerId).emit('sponSpeechMessage',msg);   
   	 socket.broadcast.to(streamerId).emit('sponSpeechMessage',msg);  
   	// }        	    
    });
    
    
    socket.on('videoChat', function(userName,userNo,applyuserNo,applyuserName){
      	 const roomId = findRoomBySocketId(socket.id);
      	    console.log('찾은 roomId=='+roomId);
      	    var usersocketid;
      	    var data = {
      	    	"applyuserNo" : applyuserNo 	
      	    };
         Object.keys(io.sockets.adapter.rooms[roomId].sockets).forEach(function(id) {
     	    var username2 = rooms[roomId][id];
     	    if(userNo==username2){
     	    	usersocketid = id;
     	    	console.log("채팅할놈 username:",username2,"ID:",id,"userName :",userName);
         	    	socket.to(id).emit('videoOk',data);
      	    }
      	  		
         });
         
       });
    
    
    
    
    
    
    
    
    
    
    socket.on('streamLikeCount', function (streamerNo,userNo,flag) {
	    console.log('streamLikeCount====socket.id='+socket.id+'streamerNo='+streamerNo+'userNo='+userNo+'flag=='+flag);
	    console.log('roomId=========='+roomId);
	    if(flag==0){
	    	console.log('0입니다'); 
	      Stream.update({"streamer":streamerNo},{$inc:{"streamLikeCount":+1}},function(err){
		    	   if(err){ 
		    		   console.log('조회수실패 왜지?ㅠㅠ'); 
		    	   }else{
	    		 console.log('좋아요추가완료'); 
	    
	    		  	    	socket.emit('like',flag);              
		    	   }    	
		    	   
	    	   });  
	    }else{
	    	console.log('1입니다'); 
		      Stream.update({"streamer":streamerNo},{$inc:{"streamLikeCount":-1}},function(err){
			    	   if(err){ 
			    		   console.log('조회수실패 왜지?ㅠㅠ'); 
			    	   }else{
		    		 console.log('좋아요추가완료'); 
		    
		    		  	socket.emit('like',flag);              
			    	   }    	
			    	   
		    	   });  
	    	
	    }
    
	  });

    /**
     * 메시지 핸들링
     */
    socket.on('message', (data) => {
      //console.log('message: ' + data);

      if (data.to === 'all') {
        // for broadcasting without me
        socket.broadcast.to(data.roomId).emit('message', data);
      } else {
        // for target user
        const targetSocketId = socketIds[data.to];
        if (targetSocketId) {
          io.to(targetSocketId).emit('message', data);
        }
      }
    });

  
 
  });
};