/**
 * http://usejsdoc.org/
 */
var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var streamSchema = new Schema({
	streamer : String,
	streamerProfile : String,
	streamNickname : String,
	streamTitle : String,
	streamContent : String,
	streamLikeCount : Number,
	streamViewCount : Number,
	banList : [{userNo : String}] 
});
console.log('streamSchema 정의함');
module.exports = mongoose.model('stream',streamSchema);
console.log('streamModel정의함');