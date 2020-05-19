fetch ("/games").then (rest => rest.json () ) .then (data => console.log (data))

  function createTable(player) {
      var prova = 0;
      var l = 0;
      var gridLabel;
      var gridId;
      if (player == "p1_") {
          gridLabel = $('<p class="gridLabel">Self grid</p>');
          gridId = "#p1Grid";
      } else {
          gridLabel = $('<p class="gridLabel">Opponent grid</p>');
          gridId = "#p2Grid";
      }
      var mytable = $('<table></table>').attr({
          id: "basicTable",
          class: ""
      });
      var rows = 10;
      var cols = 10;
      var tr = [];

      for (var i = 0; i <= rows; i++) {
          var row = $('<tr></tr>').attr({
              class: ["class1"].join(' ')
          }).appendTo(mytable);
          if (i == 0) {
              for (var j = 0; j < cols + 1; j++) {
                  $('<th></th>').text(j).attr({
                      class: ["info"]
                  }).appendTo(row);
              }
          } else {
              for (var j = 0; j < cols; j++) {
                  if (j == 0) {
                      $('<th></th>').text(String.fromCharCode(65+(l++))).attr({
                          class: ["info"]
                      }).appendTo(row);
                  }
                  $('<td></td>').attr('id', player + String.fromCharCode(65+(i-1)) + "" + (j+1)).appendTo(row);

              }
          }
      }

   }