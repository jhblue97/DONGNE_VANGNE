/**
 * http://usejsdoc.org/
 */
var io = require('socket.io-client');
var ss = require('socket.io-stream');
var fs = require('fs');

var socket = io.connect('http://localhost:82/');
var stream = ss.createStream();
var filename = 'image.png';

ss(socket).emit('profile-image', stream, {name: filename});

fs.createReadStream(filename).pipe(stream);