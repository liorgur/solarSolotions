google.charts.load('current', { packages: ['corechart', 'line', 'table', 'gauge'] });
var sitesLior;
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
  getSitesData().then(data => drawSitesOnMap(map, data)).then(data => drawSiteTable(data));
}

async function getSitesData() {
  let response = await fetch('http://localhost:8082/api/v1/Sites/');
  let data = await response.json()
  return data.sites;

}

function drawSitesOnMap(map, sitesData){
 for (var i = 0; i < sitesData.length; ++i) {
      var marker = new google.maps.Marker({
        position: {
          lat: sitesData[i].lat,
          lng: sitesData[i].lon
        },
        map: map
      });
      attachMessage(marker, sitesData[i]);
    }
    return sitesData;
}

function attachMessage(marker, message) {
  var infowindow = new google.maps.InfoWindow({
    content: (message.name)
  });

  marker.addListener('click', function () {
    infowindow.open(marker.get('map'), marker);
  });
  marker.addListener('dblclick', handleDB)
}

function handleDB() {
  //    console.log(message.name)
  //        console.log(message.ip)
  fetch('http://63.35.216.142/api/v1/data/?ip=2.55.120.218').then(data => data.json()).then((jsonDataRaw) => {
    const siteData = jsonDataRaw.data
    var data = new google.visualization.DataTable();
    data.addColumn('datetime', 'time');
    data.addColumn('number', 'tmp');
    data.addColumn('number', 'volt');
    data.addColumn('number', 'light');
    data.addColumn('number', 'humidity');
    for (var i = 0; i < siteData.length; i++) {
      data.addRow([new Date(siteData[i].time), siteData[i].tmp, siteData[i].volt, siteData[i].light, siteData[i].humidity]);
    }
    drawDataTable(data)
    drawChart(data)
    drawMeters(siteData[siteData.length -1])
  })

}

function drawChart(data) {

  var options = {
    hAxis: {
      title: 'Time',
      format: 'hh:mm',
      gridlines: { count: 9 }
    },
    vAxis: {
      title: 'Values'
    }
  };

  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

  chart.draw(data, options);
}

function drawDataTable(data) {

  data.sort({
    column: 0,
    desc: true
  });
  var table = new google.visualization.Table(document.getElementById('table_div'));

  table.draw(data, { showRowNumber: true, width: '100%', height: '100%' });
}

function drawSiteTable(sitesData) {

var data = new google.visualization.DataTable();
    data.addColumn('string', 'name');
//        data.addColumn('string', 'name2');

    for (var i = 0; i < sitesData.length; i++) {
      data.addRow([sitesData[i].name, ]);

    }

  data.sort({
    column: 0,
    desc: true
  });
  var table = new google.visualization.Table(document.getElementById('site-list'));

  table.draw(data, { showRowNumber: true, width: '100%', height: '100%' });
}

function drawMeters(data) {

    var tmp = google.visualization.arrayToDataTable([
          ['Label', 'Value'],
          ["tmp", data["tmp"]]
        ]);

    var humidity = google.visualization.arrayToDataTable([
              ['Label', 'Value'],
              ["humidity", data["humidity"]]
            ]);

    var volt = google.visualization.arrayToDataTable([
               ['Label', 'Value'],
               ["volt", data["volt"]]
             ]);
    var light = google.visualization.arrayToDataTable([
              ['Label', 'Value'],
              ["light", data["light"]]
            ]);


    var tmp_options = {
              width: 400, height: 120,
              redFrom: 90, redTo: 100,
              yellowFrom:75, yellowTo: 90,
              minorTicks: 5
            };
    var humidity_options = {
              width: 400, height: 120,
              redFrom: 90, redTo: 100,
              yellowFrom:75, yellowTo: 90,
              minorTicks: 5
            };
    var volt_options = {
              width: 400, height: 120,
              redFrom: 90, redTo: 100,
              yellowFrom:75, yellowTo: 90,
              minorTicks: 5
            };
    var light_options = {
              width: 400, height: 120,
              redFrom: 90, redTo: 100,
              yellowFrom:75, yellowTo: 90,
              minorTicks: 5
            };

        var tmp_gauge = new google.visualization.Gauge(document.getElementById('tmp_gauge'));
        var humidity_gauge = new google.visualization.Gauge(document.getElementById('humidity_gauge'));
        var volt_gauge = new google.visualization.Gauge(document.getElementById('volt_gauge'));
        var light_gauge = new google.visualization.Gauge(document.getElementById('light_gauge'));


        chart.draw(tmp_gauge, tmp_options);
        chart.draw(humidity_gauge, humidity_options);
        chart.draw(volt_gauge, volt_options);
        chart.draw(light_gauge, light_options);


      }
