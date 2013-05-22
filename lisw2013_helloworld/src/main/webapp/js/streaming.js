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
				
				var localExists;
				var nbTweets = 0;
				if (data[0]["type"] === "tweetCount"){
					for (var i = 0; i < data.length; i++){
						localExists = false;
						if (data[i]["locale"] != null && data[i]["locale"] != ""){
							for (var y = 0, maxrows = Datas.map.getNumberOfRows(); y < maxrows; y++){
								if (Datas.map.getValue(y, 0) == data[i]["locale"]){
									localExists = true;
									Datas.map.setValue(y, 1, Datas.map.getValue(y, 1) + data[i]["nbTweets"]);
								}
							}
							
							if (!localExists){
								Datas.map.addRow([data[i]["locale"], data[i]["nbTweets"]]);
							}
							Charts.map.draw(Datas.map, Options.map);
						}
						nbTweets += data[i]["nbTweets"];
					}
					
					
					var maxRows = Datas.hour.getNumberOfRows();
					var index;
					for (var y = 0, maxrows = Datas.hour.getNumberOfRows(); y < maxrows; y++){
						index = Datas.hour.getValue(y, 0);
						index -= 2;
						Datas.hour.setValue(y, 0, "" + index);
					}
					Datas.hour.addRow(["0", nbTweets]);
					
					if (maxRows > 60){
						Datas.hour.removeRow(0);
					}
					Charts.hour.draw(Datas.hour, Options.hour);
				}
				
				if (data[0]["type"] === "Top5"){
					for (var i = 0; i < data.length; i++){
						var programExist = false;
						for (var y = 0, maxrows = Datas.programs.getNumberOfRows(); y < maxrows; y++){
							if (Datas.programs.getValue(y, 0) === data[i]["hashtag"]){
								Datas.programs.setValue(y, 1, data[i]["nbTweets"]);
								programExist = true;
							}
						}
						
						if (!programExist){
							Datas.programs.addRow([data[i]["hashtag"], data[i]["nbTweets"]]);
						}
					}
					Charts.programs.draw(Datas.programs);
				}
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