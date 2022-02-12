'use strict';

let putDownLetter = function ($square, letter, wildcard, classes) {
    $square.html('<div data-letter="'+letter+'" class="letter ' + classes + (wildcard ? ' wildcard' : '') + '">' + letter + '</div>');
}

let renderBoard = function () {
    let $board = $('#board');
    let rows = SCRABBLE.board.split('\n');
    for (let i = 0; i < SCRABBLE.boardSize; i++) {
        let $row = $($('tr', $board)[i])
        for (let j = 0; j < SCRABBLE.boardSize; j++) {
            let contents = rows[i][j];
            let $square = $($('td', $row)[j]);
            let wildcard = contents === SCRABBLE.wildcardChar;
            if (wildcard) {
                j++;
                contents = rows[i][j];
            }
            if (contents === ' ') {
                $square.empty();
            } else {
                putDownLetter($square, contents, wildcard, '');
            }
        }
    }
}

let dealWithResponse = function (response) {
    $('#button-group').removeClass("loading");
    let $results = $('#results');
    $results.empty();
    if (response.results) {
        $('#results-form .add').show();
        $.each(response.results, function (index, value) {
            $results.append($('<input>', value).addClass('btn-check').attr("id", "result" + index).attr("name", "result").attr("type", "radio"));
            $results.append($('<label>', {for: "result" + index}).addClass('btn  btn-outline-success').text(value.letters + " (" + value.score + ")"));
        });
    } else {
        $('#results-form .add').hide();
    }
    SCRABBLE.board = response.board;
    SCRABBLE.remaining = response.remaining;
    renderBoard();
}

let putDownLetters = function (attrs) {
    let formData = {
        board: SCRABBLE.board,
        add: attrs
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        url: "/add-word",
        data: JSON.stringify(formData),
        dataType: "json"
    }).done(dealWithResponse).fail(function (data) {
        console.log(data);
        $("#error").html(
            '<div class="alert alert-danger">' + data.responseText + '</div>'
        );
    });
}

let showWord = function (element) {
    $("#board .fake").remove();
    let $possibility = $(element);
    let across = $possibility.attr("direction") === "ACROSS";
    let letters = $possibility.attr("letters");
    let x = $possibility.attr("x");
    let y = $possibility.attr("y");
    let wildcard = false;
    for (let i = 0; i < letters.length; i++) {
        if (letters[i] === SCRABBLE.wildcardChar) {
            wildcard = true;
            continue;
        }
        let $square = $(".square[x='" + x + "'][y='" + y + "']");
        let $letter = $('.letter', $square);
        if ($letter.length === 0) {
            putDownLetter($square, letters[i], wildcard, 'fake');
        } else {
            i--;
        }
        if (across) {
            x++;
        } else {
            y++;
        }
        wildcard = false;
    }
}

let addWord = function (element) {
    let attrs = {};
    Object.values(($(element))[0].attributes).map(attr => attrs[attr.name] = attr.value);
    putDownLetters(attrs);
}

$(document).ready(function () {
    $("#board").on('click', '.square', function () {
        let $this = $(this);
        $("#add-word-letters").val('');
        $("#add-word-x").val($this.attr("x"));
        $("#add-word-y").val($this.attr("y"));
        $('#add-word').modal('show');
        $('#add-word-letters').focus();
    });

    $("#results").on('click', 'input', function () {
        showWord($(this));
    });

    $("#results-form").submit(function (event) {
        let $this = $(this);
        let selected = $("input[name='result']:checked", $this);
        addWord(selected);
        event.preventDefault();
    });

    $('#add-word-add').click(function () {
        $("#add-word-form").submit();
    });

    $("#add-word-form").submit(function (event) {
        $('#add-word').modal('hide');
        putDownLetters({
            x: $("#add-word-x").val(),
            y: $("#add-word-y").val(),
            direction: $("input[name='add-word-direction']:checked", $(this)).val(),
            letters: $("#add-word-letters").val()
        });
        event.preventDefault();
    });

    $("#tray").submit(function (event) {
        $('#button-group').addClass("loading");
        let formData = {
            board: SCRABBLE.board,
            secondsToWait: $("#secondsToWait").val(),
            numberOfPossibilities: $("#numberOfPossibilities").val(),
            letters: $("#letters").val()
        };

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/find-words",
            data: JSON.stringify(formData),
            dataType: "json"
        }).done(dealWithResponse).fail(function (data) {
            console.log(data);
            $("#error").html(
                '<div class="alert alert-danger">' + data.responseText + '</div>'
            );
        });

        event.preventDefault();
    });
});
