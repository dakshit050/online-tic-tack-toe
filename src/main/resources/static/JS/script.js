var turns = [["#", "#", "#"], ["#", "#", "#"], ["#", "#", "#"]];
var turn = "";
var gameOn = false;


$(".tic").click(function () {
    var slot = $(this).attr('id');
    playerTurn(turn, slot);
});

function playerTurn(turn, id) {
console.log(localgame);
    if (gameOn) {
        var spotTaken = $("#" + id).text();
        if (spotTaken === "#") {
            makeAMove(playerType, id.split("_")[0], id.split("_")[1]);
        }
    }
}

function makeAMove(type, xCoordinate, yCoordinate) {
    $.ajax({
        url: url + "/game/gameplay",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "type": type,
            "cor_x": xCoordinate,
            "cor_y": yCoordinate,
            "gameId": gameId
        }),
        success:function(data){
            gameOn = false;
            displayResponse(data);
        },
        error:function(error){
            console.log(error);
        }
    })
}

function displayResponse(data) {
    gameId=data.gameId;
    localgame=data;
    let board = data.game_board;
    for (let i = 0; i < board.length; i++) {
        for (let j = 0; j < board[i].length; j++) {
            if (board[i][j] === 1) {
                turns[i][j] = 'X'
            } else if (board[i][j] === 2) {
                turns[i][j] = 'O';
            }
            let id = i + "_" + j;
            $("#" + id).text(turns[i][j]);
        }
    }
    if (data.winner != null) {
        alert("Winner is " + data.winner);
        reset();
    }
}

function reset() {
    turns = [["#", "#", "#"], ["#", "#", "#"], ["#", "#", "#"]];
    $(".tic").text("#");
}

$("#reset").click(function () {
    reset();
});