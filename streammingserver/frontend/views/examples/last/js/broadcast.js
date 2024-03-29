/*global socket, video, config*/
const peerConnections = {};

/** @type {MediaStreamConstraints} */

const constraints = {
	// audio: true,
	video : {facingMode: "user"},
	audio : true
};
var streamerId = $('#streamer').text();
// alert(streamerId);
 var userNo = $('#userNo').val();
//alert(streamerId); 
navigator.mediaDevices.getUserMedia(constraints)
.then(function(stream) {
	video.srcObject = stream;
	socket.emit('broadcaster',streamerId,userNo); 
}).catch(error => console.error(error));

socket.on('answer', function(id, description) {
	peerConnections[id].setRemoteDescription(description);
});

socket.on('watcher', function(id) {
	console.log('watcher==='+id);
	const peerConnection = new RTCPeerConnection(config);
	peerConnections[id] = peerConnection;
	peerConnection.addStream(video.srcObject);
	peerConnection.createOffer()
	.then(sdp => peerConnection.setLocalDescription(sdp))
	.then(function () {
		socket.emit('offer', id, peerConnection.localDescription);
	});
	peerConnection.onicecandidate = function(event) {
		if (event.candidate) {
			socket.emit('candidate', id, event.candidate);
		}
	};
});
socket.on('candidate', function(id, candidate) {
	peerConnections[id].addIceCandidate(new RTCIceCandidate(candidate));
});
socket.on('bye', function(id) {
	peerConnections[id] && peerConnections[id].close();
	delete peerConnections[id];
});