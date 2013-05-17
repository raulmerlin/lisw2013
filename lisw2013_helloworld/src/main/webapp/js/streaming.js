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
				
				if (data.locale != "" && data.locale != null){
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
					//Charts.map = new google.visualization.GeoChart(document.getElementById("chart_div"));
					Charts.map.draw(Datas.map, Options.map);
				}
				
				var hourExists = false;
				var hour = data.date.substring(0, data.date.indexOf(":"));
				for (var y = 0, maxrows = Datas.hour.getNumberOfRows(); y < maxrows; y++){
					if (Datas.hour.getValue(y, 0) == hour){
						hourExists = true;
						Datas.hour.setValue(y, 1, Datas.hour.getValue(y, 1) + 1);
					}
				}
				
				if (!hourExists){
					Datas.hour.addRow([hour, 1]);
				}
				
				var minuteExists = false;
				for (var y = 0, maxrows = Datas.minutes.getNumberOfRows(); y < maxrows; y++){
					if (Datas.minutes.getValue(y, 0) == data.date){
						minuteExists = true;
						Datas.minutes.setValue(y, 1, Datas.minutes.getValue(y, 1) + 1);
					}
				}
				
				if (!minuteExists){
					Datas.minutes.addRow([data.date, 1]);
				}
				if ($("#switchScale").text() === "Ver los últimos minutos"){
					Charts.hour.draw(Datas.hour, Options.hour);
				} else {
					Charts.hour.draw(Datas.minutes, Options.hour);
				}
				
				
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

$(document).ready(function(){
	$("#switchScale").click(function(){
		if ($(this).text() === "Ver los últimos minutos"){
			$(this).text("Ver el último día");
			Charts.hour.draw(Datas.minutes);
			
		} else{
			$(this).text("Ver los últimos minutos");
			Charts.hour.draw(Datas.hour);
		}
	});
});

