var Streaming = {
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
					Charts.map = new google.visualization.GeoChart(document.getElementById("chart_div"));
					Charts.map.draw(Datas.map, Options.map);
				}
				
				var hourExists = false;
				for (var y = 0, maxrows = Datas.hour.getNumberOfRows(); y < maxrows; y++){
					if (Datas.hour.getValue(y, 0) == data.date){
						hourExists = true;
						Datas.hour.setValue(y, 1, Datas.hour.getValue(y, 1) + 1);
					}
				}
				
				if (!hourExists){
					Datas.hour.addRow([data.date, 1]);
				}
				console.log(message.data);
				
				Charts.hour = new google.visualization.AreaChart(document.getElementById("chart_div1"));
				Charts.hour.draw(Datas.hour, Options.hour);
				$("#tweets").append(data.tweetMessage);
			};
		},
		
		initialize: function(hashtag){
			if (window.location.protocol == 'http:'){
				Streaming.connect('ws://' + window.location.host 
						+ '/audimetro/ws/streaming?hashtag=' 
						+ encodeURIComponent(hashtag));
			} else {
				Streaming.connect('wss://' + window.location.host 
						+ '/audimetro/ws/streaming?hashtag=' + encodeURIComponent(hashtag));
			}
		}
};

