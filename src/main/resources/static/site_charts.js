google.charts.load('current', { packages: ['corechart','line','table'] });
//google.charts.setOnLoadCallback(drawBasic);

function start() {

  var client = new HttpClient();
  client.get('http://63.35.216.142/api/v1/data/?ip=2.55.120.218', function (response) {
//  client.get('/api/v1/data/?ip=2.55.120.218', function (response) {
    var jsonData = JSON.parse(response);
    drawBasic(jsonData.data);
  });
}

function drawBasic(jsonData) {
  var data = new google.visualization.DataTable();
  data.addColumn('datetime', 'time');
  data.addColumn('number', 'tmp');
  data.addColumn('number', 'volt');
  data.addColumn('number', 'light');
  data.addColumn('number', 'humidity');
  for (var i = 0; i < jsonData.length; i++) {
        data.addRow([new Date(jsonData[i].time), jsonData[i].tmp, jsonData[i].volt, jsonData[i].light, jsonData[i].humidity]);
  }

  var options = {
    hAxis: {
      title: 'Time',
         format: 'hh:mm',
              gridlines: {count: 9}
    },
    vAxis: {
      title: 'Values'
    }
  };

  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

  chart.draw(data, options);

 data.sort({
      column: 0,
      desc: true
    });
  var table = new google.visualization.Table(document.getElementById('table_div'));

   table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
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