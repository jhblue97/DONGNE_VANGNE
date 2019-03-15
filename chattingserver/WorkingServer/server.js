var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var ss = require('socket.io-stream');
var path = require('path');
var fs = require('fs');
var countUser=0;
app.get('/', function (req, res) {
    res.send('<h1>안녕하세요 "/" 경로 입니다.</h1>');
});

function getLocalTimeString(){
    var now = new Date();
    return now.toLocaleTimeString('en-US',{hour: '2-digit', minute: '2-digit', hour12: true});
}
////소켓연결/////
io.on('connection', function (socket) {
   var userId = null;
   
   ////소켓연결유저정보/////
    socket.on('send_user',function(data){
    	var name = data.id
    	var add = data.addr
    	var profile = data.pro
    	var roomId = add;
    	///////동네별채팅입장/////
		socket.join(roomId);
		io.to(roomId).emit('send_user_name',name)
		///////1:1채팅/////////
		socket.on("one_msg",function(msg){
			io.to(roomId).emit("one_msg",{a_user:msg.a_user,b_user:msg.b_user,ms:msg.ms,no:msg.no});
			
		})
		//////소켓해제/////
		socket.on('disconnect', function () {
		       console.log(name+'유저가 접속해제를 했습니다.');
		       io.to(roomId).emit('out_msg',name+'님이 접속해제를 했습니다.')
		});
		///////메세지전달/////////       
	    socket.on('send_msg', function (msg) {
		            //콘솔로 출력을 한다.
		            //다시, 소켓을 통해 이벤트를 전송한다.
		    console.log(msg)
		    var time = getLocalTimeString();
		    io.to(roomId).emit('send_msg',{id : name,ms : msg,pro : profile,rt : time});
		    
		});
	    //////////////////////
    });
});

http.listen(82, function () {
    console.log('채팅서버구동:port=82');
});