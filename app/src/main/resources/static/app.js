var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#message").html("");
}

function connect() {
  var socket = new SockJS('/websocket'); // WebSocket通信開始
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    // /receive/messageエンドポイントでメッセージを受信し、表示する
    stompClient.subscribe('/receive/message', function (response) {
      showMessage(JSON.parse(response.body));
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendMessage() {
  // stompClientがnullであれば、まずconnect()を呼び出して接続を確立する
  if (stompClient === null || !stompClient.connected) {
    connect();
  }
  // 接続が確立されている場合、メッセージを送信する
  else {
    stompClient.send("/send/message", {}, JSON.stringify({
      'userId': $("#userId").val(),
      'channelId': $("#channelId").val(),
      'text': $("#text").val(),
      'content': $("#content").val()
    }));
    $("#userId").val('');
    $("#channelId").val('');
    $("#text").val('');
    $("#content").val('');
  }
}

function showMessage(message) {
  // 受信したメッセージを整形して表示
  $("#message").append(
    "<tr><td>" + message.userId + ": " + message.channelId + ": " + message.text + ": " + message.content + "</td></tr>");
}

$(function () {
  // ページが別のディレクトリに移動したときやページを閉じたときにdisconnect()を実行
  $(window).on('beforeunload unload', function () {
    disconnect();
  });

  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $("#connect").click(function () {
    connect();
  });
  $("#disconnect").click(function () {
    disconnect();
  });
  $("#send").click(function () {
    sendMessage();
  });
});


setTimeout("connect()", 3000);