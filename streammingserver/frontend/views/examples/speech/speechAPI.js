var textSpeech = document.getElementsByName('text-speech')[0];
var fianlTextSpeech = document.getElementsByName('final-text-speech')[0];

if('webkitSpeechRecognition' in window) 
{
    var recognition = new webkitSpeechRecognition();
    recognition.continuous = true;
    recognition.interimResults = true;

    
    recognition.onstart = function (){
        recognizing = true;
    }
    
    recognition.onend = function () {
        recognition = false;
    }
    
    recognition.onerror = function(event) {
        if (event.error == 'no-speech') {
            alert('info_no_speech');
            ignore_onend = true;
        }
        if (event.error == 'audio-capture') {
            alert('info_no_microphone');
            ignore_onend = true;
        }
        if (event.error == 'not-allowed') {
            if (event.timeStamp - start_timestamp < 100) {
                alert('info_blocked');
            } else {
                alert('info_denied');
            }
            ignore_onend = true;
        }
    }
    
    recognition.onresult = function (event) {
        var text = '';
        var finalText = '';
        for (var i = event.resultIndex; i < event.results.length; i++) {
            console.log(event.results[i][0]);
            if(event.results[i].isFinal){
                finalText += event.results[i][0].transcript;
                finalText = replaceAll(finalText, 'pontuação ponto', '.');
                finalText = replaceAll(finalText, 'quebrar linha', '\n');
                setFinalTextSpeech(finalText);                
            } else {
                text += event.results[i][0].transcript;
                setTextSpeech(text);
            }
        }
    }
    
}
else
{
    alert('This application runs only at Chrome Browser');
}


var button = document.getElementsByName('start-stop-speech')[0];
button.onclick = startButton;

function startButton(event) {
    if(event.target.value == 'Start'){
        recognition.lang = 'pt-BR';
        recognition.start();
        event.target.value = 'Stop'
    } else {
        event.target.value = 'Start'
        recognition.stop();
    } 
}

function setTextSpeech(value){
    textSpeech.value = value;
}

function setFinalTextSpeech(value){
    fianlTextSpeech.value += value;
}

function replaceAll(string, token, newtoken) {
	while (string.indexOf(token) != -1) {
 		string = string.replace(token, newtoken);
	}
	return string;
}