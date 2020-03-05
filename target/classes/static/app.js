var ip = 'localhost:8082'
var ip2 = '63.35.216.142'

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

async function getSitesData(id) {
  let param = " "
  if(id !=null){
    param = "?id="+ id
  }
  let response = await fetch('http://' + ip +'/api/v1/Sites/' + param);
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
      attachMassage(marker, sitesData[i]);
    }
    return sitesData;
}

function attachMassage(marker, massage) {
  var infowindow = new google.maps.InfoWindow({
    content: (massage.name)
  });

  marker.addListener('click', function () {
    infowindow.open(marker.get('map'), marker);
  });
  marker.addListener('dblclick', function(){handleDB(massage);});
}

function handleDB(massage) {
  fetch('http://' + ip +'/api/v1/data/?ip='+ massage.ip).then(data => data.json()).then((jsonDataRaw) => {
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

  getSitesData(massage.id).then(data => drawSiteInfo(data))


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

    var tmp_data = google.visualization.arrayToDataTable([
          ['Label', 'Value'],
          ["tmp", data["tmp"]]
        ]);

    var humidity_data = google.visualization.arrayToDataTable([
              ['Label', 'Value'],
              ["humidity", data["humidity"]]
            ]);

    var volt_data = google.visualization.arrayToDataTable([
               ['Label', 'Value'],
               ["volt", data["volt"]]
             ]);
    var light_data = google.visualization.arrayToDataTable([
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


        tmp_gauge.draw(tmp_data, tmp_options);
        humidity_gauge.draw(humidity_data, humidity_options);
        volt_gauge.draw(volt_data, volt_options);
        light_gauge.draw(light_data, light_options);


      }

function drawSiteInfo(siteInfo) {

var data = new google.visualization.DataTable();
    data.addColumn('string', 'type');
        data.addColumn('string', 'data');


      data.addRow(['name', siteInfo.name]);
      data.addRow(['ip', siteInfo.ip]);
        data.addRow(['lat', siteInfo.lat]);
          data.addRow(['lon', siteInfo.lon]);
            data.addRow(['description', siteInfo.description]);
              data.addRow(['id', siteInfo.id]);
              data.addRow(['provider1', siteInfo.provider1]);
                data.addRow(['provider2', siteInfo.provider2]);




  var table = new google.visualization.Table(document.getElementById('site_info_div'));

  table.draw(data, { showRowNumber: true, width: '100%', height: '100%' });
}