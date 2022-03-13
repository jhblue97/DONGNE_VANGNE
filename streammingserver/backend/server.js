/*!
 *
 * WebRTC Lab

 * @author dodortus (dodortus@gmail.com / codejs.co.kr)
 *
 */
const request = require('request');

let broadcaster;
const	express = require('express');
const	app = express();
const ejs = require('ejs');

const root = `${__dirname}/../`;
const path = {
  frontend: `${root}/frontend`
	
};  console.log(path);
const fs = require('fs');
var https = require('https');

const bodyParser = require('body-parser');

var Client = require('node-rest-client').Client;
var client = new Client();


var url = require('url');
const queryString = require('query-string');

var mongoose = require('mongoose');

var config2 = require('./MyModules/config.js');
 
mongoose.connect(config2.dbUrl(),{useMongoClient: true});

var db = mongoose.connection;
db.on('error', console.error.bind(console,'connection error'));
db.once('open',function(){
	console.log("we're connected!");
});
var Stream = require('./controllers/stream.js');

app.set('views', path.frontend + '/views');
app.engine('ejs', ejs.renderFile); 
app.use(express.static(path.frontend + '/contents'));
app.use(express.static(path.frontend + '/views/examples'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended : true}));
var server = https.createServer({
	key : fs.readFileSync('./controllers/rootCA.key'),
	cert : fs.readFileSync('./controllers/rootCA.crt')
},app).listen(443,function(){
	console.log('성공');
}) 

// Routes ======================================================================
require('./controllers/route.js')(app,Stream,request,client,url,queryString);

//Socket.io ======================================================================
require('./controllers/socket.js')(server,Stream);



