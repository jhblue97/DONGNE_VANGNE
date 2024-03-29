/*global socket, video, config*/
let peerConnection;
var streamerId = $('#streamer').text();
//alert(streamerId);
var userNo = $('#userNo').val();
//alert(streamerId);
socket.emit('enter',streamerId,userNo)

socket.on('offer', function(id, description) {
	peerConnection = new RTCPeerConnection(config);
	peerConnection.setRemoteDescription(description)
	.then(() => peerConnection.createAnswer())
	.then(sdp => peerConnection.setLocalDescription(sdp))
	.then(function () {
		socket.emit('answer', id, peerConnection.localDescription);
	});
	peerConnection.onaddstream = function(event) {
		video.srcObject = event.stream;
	};
	peerConnection.onicecandidate = function(event) {
		if (event.candidate) {
			socket.emit('candidate', id, event.candidate);
		}
	};
});

socket.on('candidate', function(id, candidate) {
  peerConnection.addIceCandidate(new RTCIceCandidate(candidate))
  .catch(e => console.error(e));
});

socket.on('connect', function() {
	socket.emit('watcher');
});

socket.on('broadcaster', function() {
	console.log('watch broadcaster===');
  socket.emit('watcher');
});

socket.on('bye', function() {
	peerConnection.close();
});