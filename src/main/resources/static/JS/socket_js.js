const url = 'http://127.0.0.1:8080';
let stompClient;
let gameId;
let playerType;
let localgame;

function connectToSocket(gameId) {
    let socket = new SockJS(url + "/gameplay");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/game-progress/" + gameId, function (response) {
            let data = JSON.parse(response.body);
            console.log(data);
            gameOn=true;
            displayResponse(data);
        })
    })
}

function createGame(){
let player1=document.getElementById("player1").value;
    if(player1==null || player1==''){
        alert("Please Enter Username First");
    }else{
        $.ajax({
            url:url+"/game/startgame",
            type:"POST",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify({
            "playerName":player1
            }),
            success:(data)=>{
                localgame=data;
                gameId=data.gameId;
                playerType='X';
                reset();
                connectToSocket(gameId);
                gameOn=true;
                alert(gameId);
            },error:(error)=>{
                console.log(error);
            }
        });
    }
}

function connectToRandom() {
    let player2 = document.getElementById("player1").value;
    if (player2 == null || player2 === '') {
        alert("Please enter UserName First");
    } else {
        $.ajax({
            url: url + "/game/connect/random",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "playerName": player2
            }),
            success:(data)=>{
                localgame=data;
                gameId = data.gameId;
                playerType = 'O';
                reset();
                connectToSocket(gameId);
                alert("Congrats you're playing with: " + data.player1.playerName);
            },
            error: (error)=>{
                console.log(error);
            }
        })
    }
}

function connectToSpecificGame() {
    let player2 = document.getElementById("player1").value;
    if (player2 == null || player2 === '') {
        alert("Please enter UserName First");
    } else {
        let gameId = document.getElementById("game_id").value;
        if (gameId == null || gameId === '') {
            alert("Please enter game id");
        }
        $.ajax({
            url: url + "/game/connect",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "player": {
                    "playerName": player2
                },
                "game_id": gameId
            }),
            success:function(data){
                localgame=data;
                gameId = data.gameId;
                playerType = 'O';
                reset();
                connectToSocket(gameId);
                alert("Congrats you're playing with: " + data.player1.playerName);
            },
            error: (error)=> {
                console.log(error);
            }
        })
    }
}