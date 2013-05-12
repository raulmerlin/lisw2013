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
				for (var y = 0, maxrows = Datas.hour.getNumberOfRows(); y < maxrows; y++){
					if (Datas.hour.getValue(y, 0) == data.date){
						hourExists = true;
						Datas.hour.setValue(y, 1, Datas.hour.getValue(y, 1) + 1);
					}
				}
				
				if (!hourExists){
					Datas.hour.addRow([data.date, 1]);
				}
				//Charts.hour = new google.visualization.AreaChart(document.getElementById("chart_div1"));
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
			};
		},
		
		initialize: function(hashtag){
			Streaming.hashtag = hashtag;
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

