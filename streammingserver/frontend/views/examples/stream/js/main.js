/*!
 *
 * WebRTC Lab
 * @author dodortus (dodortus@gmail.com / codejs.co.kr)
 *
 */
$(function() {
  console.log('Loaded Main');

  let roomId;
  let userId;
  let remoteUserId;
  let isOffer;

  const socket = io();
  const mediaHandler = new MediaHandler();
  const peerHandler = new PeerHandler({
    send: send
  });
  const animationTime = 500;
  const isSafari = DetectRTC.browser.isSafari;
  const isMobile = DetectRTC.isMobileDevice;
  const mediaOption = {
    audio: true,
    video: {
      mandatory: {
        maxWidth: 1920,
        maxHeight: 1080,
        maxFrameRate: 30
      },
      optional: [
        {googNoiseReduction: true}, // Likely removes the noise in the captured video stream at the expense of computational effort.
        {facingMode: "user"}        // Select the front/user facing camera or the rear/environment facing camera if available (on Phone)
      ]
    }
  };

  // DOM
  const $body = $('body');
  const $createWrap = $('#create-wrap');
  const $waitWrap = $('#wait-wrap');
  const $videoWrap = $('#video-wrap');
  const $uniqueToken = $('#unique-token');

  /**
   * 입장 후 다른 참여자 발견시 호출
   */
  var offer;
  

  /**
   * 참석자 핸들링
   * @param roomId
   * @param userList
   */
  function onJoin(roomId, userList) {
    console.log('onJoin', userList);

  }
  function setChildValue(name){
	 // alert(name);

}
  var filterWords = ["바보","멍청"]; 
  var rgx = new RegExp(filterWords.join("|"), "gi");
  function wordFilter(str) {            
      return str.replace(rgx, "♡♡");             
  }
   
  /**
   * 이탈자 핸들링
   * @param userId
   */
 function onLeave(userId) {
    console.log('onLeave', arguments);

   //(remoteUserId === userId) {
      $('#local-video').remove();
    //}
  } 
  /**
   * 소켓 메세지 핸들링
   * @param data
   */
  function onMessage(data) {
    console.log('onMessage', arguments);

    if (!remoteUserId) {
      remoteUserId = data.sender;
    }

    if (data.sdp || data.candidate) {
      peerHandler.signaling(data);
    } else {
      // etc
    }
  }

  /**
   * 소켓 메시지 전송
   * @param data
   */
  function send(data) {
    console.log('send', arguments);

    data.roomId = roomId;
    data.sender = userId;
    socket.send(data);
  }

  /**
   * 방 고유 접속 토큰 생성
   */
  function setRoomToken() {
    const hashValue = (Math.random() * new Date().getTime()).toString(32).toUpperCase().replace(/\./g, '-');

    if (location.hash.length > 2) {
      $uniqueToken.attr('href', location.href);
    } else {
      location.hash = '#' + hashValue;
    }
  }

  /**
   * 클립보드 복사
   */
  function setClipboard() {
    $uniqueToken.click(function(){
      const link = location.href;

      if (window.clipboardData){
        window.clipboardData.setData('text', link);
        alert('Copy to Clipboard successful.');
      } else {
        window.prompt("Copy to clipboard: Ctrl+C, Enter", link); // Copy to clipboard: Ctrl+C, Enter
      }
    });
  }

  /**
   * 로컬 스트림 핸들링
   * @param stream
   */
  function onLocalStream(stream) {
  
  }

  /**
   * 상대방 스트림 핸들링
   * @param stream
   */
  function onRemoteStream(stream) {
    console.log('onRemoteStream',stream);

  }
 
   
  
  /**
   * 초기 설정
   */
 
   
  function socketban(){
	 alert('main.js'); 
  }
  

  function sponimg2(str) {
	   $('#'+str).fadeOut(5000); 
	  }
  
  function initialize() {
	  
	  var objDiv = document.getElementById("chatLog");
	  objDiv.scrollTop = objDiv.scrollHeight; 

	     var modal = document.querySelector(".modal");
		    var trigger = document.querySelector(".trigger");
		    var closeButton = document.querySelector(".close-button");

		    function toggleModal() {
		        modal.classList.toggle("show-modal");
		    }

		    function windowOnClick(event) {
		        if (event.target === modal) {
		            toggleModal();
		        }
		    }
		    
		
		    

		    trigger.addEventListener("click", toggleModal);
		    closeButton.addEventListener("click", toggleModal);
		    window.addEventListener("click", windowOnClick);
		    
		    
		    
	  var streamerId = $('#streamer').val();
	  var userNo = $('#userNo').val();
	  var sponSpeech = $('#sponSpeech').val();
	  var sponPrice = $('#sponPrice').val(); 
	  var userName = $('#userName').val(); 
	  var userProfile = $('#userProfile').val();  
	  if(sponSpeech=='1'){
			  socket.emit('sponSpeech',userNo,sponPrice,userName,streamerId); 	 
			  window.setInterval(window.close,100); 
	  }  
	  socket.on('sponSpeechMessage',function(msg){
		//  alert(msg); 
	    	//alert('realbanMessage'+username);  
	    	var conversationPanel = document.getElementById('chatLog');   
	    	var left = document.getElementById('left-box');  
	    	 var sponimg = document.createElement('img'); 
	    	 var clock = new Date();
	    	 var id = clock.getMinutes()+"분";
	    	 id+= clock.getSeconds()+"초";
	    	// alert(id);
	    	 
	    	 sponimg.setAttribute("src","http://localhost:8080/common/images/stream/rabbit3.gif")		  
		     sponimg.setAttribute("height","300");
	    	 sponimg.setAttribute("width","300");
			 sponimg.setAttribute("id",id+"1"); 
			 sponimg.className = 'sponimggif';
			 
			 var div = document.createElement('p'); 
			 	div.setAttribute("id",id);
				 div.className = 'sponMessage';     			  
				 div.innerHTML =  msg+'<a class="hide">우와아 더주세요</a>'; 
				 var br = document.createElement('br'); 
				 conversationPanel.appendChild(div); 
				 conversationPanel.appendChild(br);
				 left.appendChild(sponimg);
				 doTTS(id); 
				 sponimg2(id+"1"); 
	    });
	  
	  
    roomId = location.href.replace(/\/|:|#|%|\.|\[|\]/g, '');
    userId = Math.round(Math.random() * 99999);
    setRoomToken();
    setClipboard();

    socket.emit('enter',streamerId,userNo);
    socket.on('join', onJoin);
    socket.on('leave', onLeave);
    socket.on('message', onMessage);

    

    
    
    $(document).on("click", '.banname', function() {
        var usernum = $(this).data("param2");
        var username = $(this).data("param3");
    //    alert(username); 
        swal({
			  title: "강퇴하기",
			  text: username+"님을 강퇴 정말 하시겠습니까?",
			  icon:"success",
			  buttons: ["취소", "강퇴"]
			})
			.then((willDelete) => {
			  if (willDelete) {
				  socket.emit('ban',usernum,username,streamerId); 
			  }
			});
        
    });
    
    $(document).on("click", '.nameTag', function() {
        var userName = $(this).data("username");
        var userNo = $(this).data("userno");
        var applyuserName = $('#userName').val() 
         var applyuserNo = $('#userNo').val() 
       // alert(userName); 
      //  alert(userNo);  
        var result = confirm("1:1화상채팅 신청 하시겠습니까??");    
		  if(result){
 window.open("https://localhost/stream/videochat?applyuserNo="+applyuserNo+"&userName="+applyuserName,"_blank", "width=400, height=300, scrollbars=no");  
		 socket.emit('videoChat',userName,userNo,applyuserNo,applyuserName);   
		  }else{ 
		  }  
    });
    
    socket.on('videoOk',function(data){ 
    	 
    	 
    	var zz = $('#userName').val() 
    	var yy = data.applyuserNo;
   window.open("https://localhost/stream/videochat?applyuserNo="+yy+"&userName="+zz,"_blank", "width=400, height=300, scrollbars=no");  
  
        	    });
    
    
    socket.on('realban',function(e){
   
    	   swal("강퇴되었습니다.......","", "error");
    		
   	window.close();
    });
      
    
    
    $(document).on("click", '#zz', function() {
    	 var userNo = $('#userNo').val();
         var streamerNo = $('#streamer').data("param3");
 self.location="http://localhost:8080/stream/spon?userNo="+userNo+"&streamerNo="+streamerNo
    });
    $(document).on("click", '.modal', function() {
    	 $('.modal').show(); 
    });
    $(document).on("click", '#inicispay', function() {
     
    	 var price = $('#price').val();
         var userNo = $('#userNo').val();
         var userName = $('#userName').val();
         //alert(userName);
         var streamerNo = $('#streamer').data("param3");
        // alert(price+userNo+streamerNo);
    	
    	var IMP = window.IMP; // 생략가능
		var username = '주문명:결제테스트';
		IMP.init('imp76516341'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
			 
		IMP.request_pay({
		    pg : 'inicis', // version 1.1.0부터 지원.
		    pay_method : 'card',
		    merchant_uid : 'merchant_' + new Date().getTime(),
		    name : '후원',
		    amount : price,
		    buyer_email : '',
		    buyer_name : userName,
		    buyer_tel : '010-1234-5678',
		    buyer_addr : '서울특별시 강남구 삼성동',
		    buyer_postcode : '123-456',
		    m_redirect_url : 'https://www.yourdomain.com/payments/complete'
		}, function(rsp) {
		    if ( rsp.success ) {
		    	//alert(rsp.success); 
		    	 $.ajax({
		             url : '/stream/inicisStream', 
		             method : 'post',
		             data : {  
		                 "price" : price,
		                 "userNo" : userNo,
		                 "streamerNo" : streamerNo,
		                "paymentNo" : "1"
		             }, 	 
		             success : function (data) {
		            // alert(data); 
		            	 $('.modal').hide(); 
		               window.open(data,"_blank", "width=400, height=300, scrollbars=no");  
		                 //    alert(data.url);
		             //    alert(data.streamerNo);
		             //    alert(data.userNo);
		            //  self.location=data.url; 
		             },
		             error : function (err) {
		             	alert('실패 ㅠㅠ');
		             	// alert(data.userNo); 
		                 }
		         });
		    	 
		    } else {
		        var msg = '결제에 실패하였습니다.';
		        msg += '에러내용 : ' + rsp.error_msg;
		    }
		  
		    //alert(msg);
		}); 
    
    });
    

    
    
  
    
 
 
    $(document).on("click", '#kakaopay', function() {
    	toggleModal();
        var price = $('#price').val();
        var userNo = $('#userNo').val();
        var streamerNo = $('#streamer').data("param3");
        
        
        
  //     alert('금액'+price+'보내는유저넘버'+userNo+'받는스트리머넘버'+streamerNo);
    /*    $.ajax({
            url : '/stream/kakaoStream',
            method : 'post',
            data : { 
                "price" : price,
                "userNo" : userNo,
                "streamerNo" : streamerNo
            }, 	 
            success : function (data) {
               // alert(data); 
                window.open(data, "_blank", "width=400, height=300, scrollbars=no");
               // var newWindow = window.open("about:blank");
               // newWindow.location.href = data;

              //  alert(data.url); 
            //    alert(data.streamerNo);
            //    alert(data.userNo);
            // self.location=data.url; 
            },
            error : function (err) {
            	alert('실패 ㅠㅠ');
                } 
        });*/
 window.open("http://localhost:8080/stream/json/start2?price="+price+"&userNo="+userNo+"&streamerNo="+streamerNo, "_blank", "width=400, height=500, scrollbars=no, status=no,toolbar=no");
  
  // self.location="http://192.168.0.12:8080/stream/json/start2?price="+price+"&userNo="+userNo+"&streamerNo="+streamerNo
 /* $("#myModal")
  .html('<object width="100%" height="100%" type="text/html" data="http://localhost:8080/stream/json/start2?price='+price+'"&userNo="'+userNo+'"&streamerNo="'+streamerNo+'></object>').foundation("open");
*/
        
    }); 
    

    socket.on('streamViewCount',function(state){
    	//alert(state); 
    	$.ajax({
            url : '/stream/streamViewCount?streamer='+streamerId,
            method : 'get', 
            success : function (data) { 
              //alert('조회수 값=='+data);   
        $('#streamViewCount').html('<img class="streamViewCount" src="http://localhost:8080/common/images/stream/view.png"style="height:40px; width:40px;">'+data); 
           	   
             //self.location=data.url;
            },
            error : function (err) {
            	alert('실패 ㅠㅠ');
            }
        });
    });
    
 
 
     
    
    
    
    
    $(document).on("click", '#streamLikeCount', function() {
        var userNo = $('#userNo').val();
        var streamerNo = $('#streamer').data("param3");
        var flag = $('#streamLikeCount').data("param5"); 
        
    //    alert(flag); 
        socket.emit('streamLikeCount',streamerNo,userNo,flag);    
    });  
    
    
    socket.on('like',function(flag){
    			if(flag==0){
    		    var flag = $('#streamLikeCount').data("param5",1); 
     $('#streamLikeCount').html('<img class="streamLikeCount" src="http://localhost:8080/common/images/stream/heart2.png"style="height:40px; width:40px;">'); 
	
    		}else{
    			   var flag = $('#streamLikeCount').data("param5",0); 
    			  $('#streamLikeCount').html('<img class="streamLikeCount" src="http://localhost:8080/common/images/stream/heart3.png"style="height:40px; width:40px;">'); 
    		}
       });
    
    
    socket.on('realbanMessage',function(username){
    	var conversationPanel = document.getElementById('chatLog');       	 
		 var div = document.createElement('p'); 
			 div.className = 'ban';     			  
			 div.innerHTML =  username+'님이 강퇴당하셨습니다.'; 
			 var br = document.createElement('br'); 
			 conversationPanel.appendChild(div); 
			 conversationPanel.appendChild(br);   
    });
    
   
    
   
    $('#chat').on('submit', function(e){	
    	
    	
 		var conversationPanel = document.getElementById('chatLog');       	 
		 var message =  $('#message').val();
		
		 var result = wordFilter(message);
		 var p = document.createElement('p'); 
		 var span = document.createElement('span'); 
		 	p.className = 'memessage';     		 	  
		 	p.innerHTML =  result;        
		 	
			 var br = document.createElement('br'); 
			 conversationPanel.appendChild(p); 
			 conversationPanel.appendChild(br);    
			 conversationPanel.scrollTop = conversationPanel.scrollHeight;  
    		var streamerProfile = $('#streamerProfile').data("param")+"";
    		if(streamerId==userNo){
    		   socket.emit('send message', $('#userName').val(),result,streamerProfile,userNo);
    		}else{ 
    			   socket.emit('send message', $('#userName').val(), result,userProfile,userNo); 		    		
    		} 
    	        $('#message').val("");
    	        $("#message").focus();
    	        e.preventDefault();
    
      }); 
    
    
      socket.on('receive message', function(msg,checkNo,userName){
    	if(streamerId==userNo){
    		if(streamerId==checkNo){	
    			alert('streamerId'+streamerId+'checkNo'+checkNo); 
    			var conversationPanel = document.getElementById('chatLog');
    			 var div = document.createElement('span');
       			 div.className = 'usermessage';  
       			 div.innerHTML =  msg;
       			 conversationPanel.appendChild(div); 
       			 conversationPanel.scrollTop = conversationPanel.scrollHeight;  
       			}else{   
       			var conversationPanel = document.getElementById('chatLog');
    			 var div = document.createElement('p');
    				 var img = document.createElement('img');	
    			 var br = document.createElement('br');	 
    			 img.setAttribute("src","http://localhost:8080/common/images/stream/ban.png")		  
    				 img.setAttribute("class","banname");
    			 img.setAttribute("name","banname");
    			 img.setAttribute("data-param2",checkNo);
    			 img.setAttribute("data-param3",userName);
    			 img.setAttribute("height","20"); 
      			 img.setAttribute("width","20");   
    			 div.className = 'usermessage';   
    			 div.innerHTML = '<img src="http://localhost:8080/common/images/stream/ban.png" class="banname" name="banname" data-param2="'+checkNo+'" data-param3="'+userName+'" height="20" width="20">'+msg+'</br>';   
    			 conversationPanel.appendChild(div); 
    				 conversationPanel.appendChild(br);  
    			 conversationPanel.scrollTop = conversationPanel.scrollHeight;  
    					}  
    		}else{  
    		var conversationPanel = document.getElementById('chatLog');
    		$('#chatLog').animate({scrollTop: $('#chatLog').prop("scrollHeight")}, 500); 
   			 var div = document.createElement('p'); 
   			 var br = document.createElement('br');	 
   			 div.className = 'usermessage';
   			 div.innerHTML =  msg;  
   			 conversationPanel.appendChild(div);	
   			 conversationPanel.appendChild(br);	
   			 conversationPanel.scrollTop = conversationPanel.scrollHeight;   
   	   		}
      });
    
    // Peer 관련 이벤트 바인딩
    peerHandler.on('addRemoteStream', onRemoteStream);

    peerHandler.getUserMedia(mediaOption, onLocalStream);
    

    
    $('#btn-camera').click(function() {
      const $this = $(this);
      $this.toggleClass('active');
      mediaHandler[$this.hasClass('active') ? 'pauseVideo' : 'resumeVideo']();
    });

    $('#btn-mic').click(function() {
      const $this = $(this);
      $this.toggleClass('active');
      mediaHandler[$this.hasClass('active') ? 'muteAudio' : 'unmuteAudio']();
    });
  }

  $(document).on("click", '#mic', function() {
      var flag = $(this).data("mic"); 
   if(flag==0){
		sr.start();
$('.micClass').html('<img class="mic" id="mic" src="http://localhost:8080/common/images/stream/pause.png"style="height:30px; width:30px;">'); 
$('#mic').data("mic",1);
   }else{
	   sr.stop();
  	 $('.micClass').html('<img class="mic" id="mic" src="http://localhost:8080/common/images/stream/mic2.png"style="height:30px; width:30px;">'); 
  	 $('#mic').data("mic",0);
  	  	 
  	 
   }
   
  });  
  
  
  
  // Get the modal
 
  var output = document.getElementById("message"); 
		/** --- **/
	
	var sr = new SpeechRecognition();
	
	/** Events **/
	sr.on("starting",  function() { console.log("[SpeechRecognition]", "Starting..."); });
	sr.on("started",   function() { button.value = "Stop"; console.log("[SpeechRecognition]", "Started."); });
	sr.on("stopping",  function() { console.log("[SpeechRecognition]", "Stopping..."); });
	sr.on("stopped",   function() { button.value = "Start"; console.log("[SpeechRecognition]", "Stopped."); });
	
	sr.on("optionschanged",  function() { console.log("[SpeechRecognition]", "Options changed."); });
	/** Events **/
	
	/** Set options **/
	sr.set("language", "ko-KR");
	sr.set({
		language: "ko-KR",
		continuous: true, 		
		interimResults: false
	});
	
	/** Error & Result Events **/
	sr.on("error", function(e) {
		console.log("[SpeechRecognition]", "Error:", e);
	});
	
	sr.on("result", function(evt) {
		console.log("[SpeechRecognition]", "Result:", evt);
		
		for (var i = evt.resultIndex; i < evt.results.length; ++i) {
		  if (evt.results[i].isFinal) {
			output.value += evt.results[i][0].transcript;
		  } else {
			//evt.results[i][0].transcript; <-- Not fully recognized (alias: interim script)!
		  }
		}
		
	});
	
	/** Start & Stop **/
	sr.stop();  
  
  
  initialize();
});
 