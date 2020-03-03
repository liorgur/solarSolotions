google.charts.load('current', { packages: ['corechart','line','table'] });

function initMap() {

  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 8,
    center: { lat: 32.1192362, lng: 35.5750295 }
  });

  var bounds = {
    north: 32.1,
    south: 32.2,
    east: 34.90,
    west: 34.80
  };


  // Display the area between the location southWest and northEast.
  map.fitBounds(bounds);


  var client = new HttpClient();
  client.get('http://63.35.216.142/api/v1/Sites/', function (response) {
//  client.get('/api/v1/Sites/', function (response) {

    drewSites(response)
  });

  function drewSites(response) {
    var jsonResponse = JSON.parse(response);
    for (var i = 0; i < jsonResponse.sites.length; ++i) {
      var marker = new google.maps.Marker({
        position: {
          lat: jsonResponse.sites[i].lat,
          lng: jsonResponse.sites[i].lon
        },
        map: map
      });
      attachMessage(marker, jsonResponse.sites[i]);
    }
  }
  // Attaches an info window to a marker with the provided message. When the
  // marker is clicked, the info window will open with the secret message.
  function attachMessage(marker, message) {
    var infowindow = new google.maps.InfoWindow({
      content: (message.name)
    });

    marker.addListener('click', function () {
      infowindow.open(marker.get('map'), marker);
    });
    marker.addListener('dblclick', handleDB)
  }
}

function handleDB(){
//    console.log(message.name)
//        console.log(message.ip)
fetch('http://63.35.216.142/api/v1/data/?ip=2.55.120.218').then(data=>data.json()).then((jsonDataRaw)=>{
const jsonData = jsonDataRaw.data
  var data = new google.visualization.DataTable();
    data.addColumn('datetime', 'time');
    data.addColumn('number', 'tmp');
    data.addColumn('number', 'volt');
    data.addColumn('number', 'light');
    data.addColumn('number', 'humidity');
    for (var i = 0; i < jsonData.length; i++) {
          data.addRow([new Date(jsonData[i].time), jsonData[i].tmp, jsonData[i].volt, jsonData[i].light, jsonData[i].humidity]);
    }
   drawTable(data)
    drawChart(data)
})

}

function drawChart(data) {

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
}
function drawTable(data) {

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
    // anHttpRequest.setRequestHeader("Access-Control-Allow-Origin", "*");
    anHttpRequest.send(null);
  }
}