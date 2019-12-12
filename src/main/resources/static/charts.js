google.charts.load('current', { packages: ['corechart', 'line'] });
//google.charts.setOnLoadCallback(drawBasic);

function start() {

  var client = new HttpClient();
  client.get('http://localhost:8082/api/v1/data/?ip=2.55.120.218', function (response) {
    var jsonData = JSON.parse(response);
    drawBasic(jsonData.data);
  });
}

function drawBasic(jsonData) {
  var data = new google.visualization.DataTable();
  data.addColumn('datetime', 'X');
  data.addColumn('number', 'tmp');
  data.addColumn('number', 'volt');
  data.addColumn('number', 'light');
  data.addColumn('number', 'humidity');


  for (var i = 0; i < jsonData.length; i++) {
    var year = new Date(jsonData[i].time).getFullYear();
    var month = new Date(jsonData[i].time).getMonth();
    var day =new Date(jsonData[i].time).getDate();
    var hour =new Date(jsonData[i].time).getHours();
    var min =new Date(jsonData[i].time).getMinutes();
//    data.addRow([new Date(year,month,day,hour,min), jsonData[i].tmp, jsonData[i].volt, jsonData[i].light, jsonData[i].humidity]);
        data.addRow([new Date(jsonData[i].time), jsonData[i].tmp, jsonData[i].volt, jsonData[i].light, jsonData[i].humidity]);

//    new Date(2000, 8, 5)
  }


  //      data.addRows([
  //        [0, 0],   [1, 100],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
  //        [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
  //        [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
  //        [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
  //        [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
  //        [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
  //        [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
  //        [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
  //        [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
  //        [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
  //        [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
  //        [66, 70], [67, 72], [68, 75], [69, 80]
  //      ]);

  var options = {
    hAxis: {
      title: 'Time'
    },
    vAxis: {
      title: 'Popularity'
    }
  };

  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

  chart.draw(data, options);
}

var HttpClient = function () {
  this.get = function (aUrl, aCallback) {
    var anHttpRequest = new XMLHttpRequest();
    anHttpRequest.onreadystatechange = function () {
      if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
        aCallback(anHttpRequest.responseText);
    }

    anHttpRequest.open("GET", aUrl, true);
    anHttpRequest.send(null);
  }
}