var app = require('express')();
var socketIO = require('socket.io');
var ss = require('socket.io-stream');
var path = require('path');
var fs = require('fs');

var server = app.listen(53322, function() {
    console.log('server is working at port 53322');
});

var io = socketIO.listen(server);

io.on('connection', function (socket) {
    ss(socket).on('file', function(stream, data) {
        console.log(data);
        var filename = path.basename(data.name);
        var filepath = path.join('./uploads', filename);
        var ws = fs.createWriteStream(filepath);
        stream.pipe(ws);
    });

});