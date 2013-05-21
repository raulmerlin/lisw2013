// Determine if this is still the same 20 second of observation
var getCurrentIndex = function(stringHour){
	var hour = stringHour.substr(0, 2);
	var minute = stringHour.substr(3, 2);
	var second = stringHour.substr(6);
	
	var currentDate = new Date();
	currentDate.setHours(hour, minute, second, 0);
	
	var oldDate = new Date();
	var oldHour = currentHour.substr(0, 2);
	var oldMinute = currentHour.substr(3, 2);
	var oldSecond = currentHour.substr(6);
	oldDate.setHours(oldHour, oldMinute, oldSecond, 0);
	
	var diff = oldDate - currentDate;
	diff = Math.abs(Math.round(diff / 1000));
	
	if (diff > 20){
		currentHour = stringHour;
		return true;
	}
	return false;
};

var Streaming = {
		hashtag: null,
		
		socket: null,
		
		connect: function(host){
			if ('WebSocket' in window) {
				Streaming.socket = new WebSocket(host);
			} else if ('MozWebSocket' in window) {
				Streaming.socket = new MozWebSocket(host);
			} else {
				console.log('Error: WebSocket is not supported by this browser.');
				return;
			}

			Streaming.socket.onopen = function(){
				console.log('Info: WebSocket connection opened.');
			};
			
			Streaming.socket.onclose = function(){
				console.log('Info: WebSocket closed.');
			};
			
			Streaming.socket.onmessage = function(message){
				var data = JSON.parse(message.data);
				
				/*if (data.locale != "" && data.locale != null){
					var localExists = false;
					for (var y = 0, maxrows = Datas.map.getNumberOfRows(); y < maxrows; y++){
						if (Datas.map.getValue(y, 0) == data.locale){
							localExists = true;
							Datas.map.setValue(y, 1, Datas.map.getValue(y, 1) + 1);
						}
					}
					
					if (!localExists){
						Datas.map.addRow([data.locale, 1]);
					}
					Charts.map.draw(Datas.map, Options.map);
				}
				
				var newIndex = getCurrentIndex(data.date);
				var maxRows = Datas.hour.getNumberOfRows();
				var index;
				if (newIndex){
					for (var y = 0, maxrows = Datas.hour.getNumberOfRows(); y < maxrows; y++){
						index = Datas.hour.getValue(y, 0);
						index -= 20;
						Datas.hour.setValue(y, 0, "" + index);
					}
					Datas.hour.addRow(["0", 1]);
				}
				else{
					for (var y = 0; y < maxRows; y++){
						if (Datas.hour.getValue(y, 0) == "0"){
							Datas.hour.setValue(y, 1, Datas.hour.getValue(y, 1) + 1);
						}
					}
					
				}
				
				if (maxRows > 6){
					Datas.hours.removeRow(0);
				}
				
				Charts.hour.draw(Datas.hour, Options.hour);
				
				for (var y = 0, maxrows = Datas.programs.getNumberOfRows(); y < maxrows; y++){
					console.log(Datas.programs.getValue(y, 0) + ": " 
							+ Datas.programs.getValue(y, 1) + "-" + Streaming.hashtag);
					
					if (Datas.programs.getValue(y, 0) === Streaming.hashtag){
						Datas.programs.setValue(y, 1, Datas.programs.getValue(y, 1) + 1);
					}
				}
				Charts.programs.draw(Datas.programs);
				
				console.log(message.data);
				$("#tweets").append("data.hour - " + data.tweetMessage + "<br />");
				$("#count").text($("#count").text() + 1);*/
				console.log(data);
				var maxRows = Datas.hour.getNumberOfRows();
				var index;
				for (var y = 0, maxrows = Datas.hour.getNumberOfRows(); y < maxrows; y++){
					index = Datas.hour.getValue(y, 0);
					index -= 20;
					Datas.hour.setValue(y, 0, "" + index);
				}
				Datas.hour.addRow(["0", data]);
				
				if (maxRows > 6){
					Datas.hour.removeRow(0);
				}
				Charts.hour.draw(Datas.hour, Options.hour);
			};
		},
		
		initialize: function(hashtag){
			Streaming.hashtag = hashtag;
			var url = window.location.href;
			url = url.substring(url.indexOf("/", 0) + 2);
			url = url.substring(url.indexOf("/", 0) + 1);
			url = url.substring(0, url.indexOf("/", 0));

			if (window.location.protocol == 'http:'){
				Streaming.connect('ws://' + window.location.host + "/" + url
						+ '/ws/streaming?hashtag=' 
						+ encodeURIComponent(hashtag));
			} else {
				Streaming.connect('wss://' + window.location.host + "/" + url
						+ '/ws/streaming?hashtag=' + encodeURIComponent(hashtag));
			}
		}
};