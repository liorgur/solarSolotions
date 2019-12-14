google.charts.load('current', { packages: ['corechart', 'line'] });
//google.charts.setOnLoadCallback(drawBasic);

function start() {

  var client = new HttpClient();
  client.get('http://63.35.216.142/api/v1/data/?ip=2.55.120.218', function (response) {
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
//    var year = new Date(jsonData[i].time).getFullYear();
//    var month = new Date(jsonData[i].time).getMonth();
//    var day =new Date(jsonData[i].time).getDate();
//    var hour =new Date(jsonData[i].time).getHours();
//    var min =new Date(jsonData[i].time).getMinutes();
//    data.addRow([new Date(year,month,day,hour,min), jsonData[i].tmp, jsonData[i].volt, jsonData[i].light, jsonData[i].humidity]);
        data.addRow([new Date(jsonData[i].time), jsonData[i].tmp, jsonData[i].volt, jsonData[i].light, jsonData[i].humidity]);

  }


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