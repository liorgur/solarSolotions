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
  client.get('http://localhost:8082/api/v1/Sites/', function (response) {
    drewSites(response)
  });


  function drewSites(response) {
    var jsonResponse = JSON.parse(response);
    for (var i = 0; i < jsonResponse.sites.length; ++i) {
      console.log(jsonResponse.sites[i].lat)
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
    marker.addListener('dblclick', function () {
      console.log("dasdasdasdas");
      location.href = "site.html";
    });
  }
}
function google2() {
  alert("ggogle2")
}

//function google() {
//  google.charts.load('current', {
//    packages: ['corechart', 'line']
//  });
//  google.charts.setOnLoadCallback(drawBasic);

function drawBasic() {
alert("ASDASD")
google.charts.load('current', {
    packages: ['corechart', 'line']
  });
  var data = new google.visualization.DataTable();
  data.addColumn('number', 'X');
  data.addColumn('number', 'Dogs');

  data.addRows([
    [0, 0],
    [1, 100],
    [2, 23],
    [3, 17],
    [4, 18],
    [5, 9],
    [6, 11],
    [7, 27],
    [8, 33],
    [9, 40],
    [10, 32],
    [11, 35],
    [12, 30],
    [13, 40],
    [14, 42],
    [15, 47],
    [16, 44],
    [17, 48],
    [18, 52],
    [19, 54],
    [20, 42],
    [21, 55],
    [22, 56],
    [23, 57],
    [24, 60],
    [25, 50],
    [26, 52],
    [27, 51],
    [28, 49],
    [29, 53],
    [30, 55],
    [31, 60],
    [32, 61],
    [33, 59],
    [34, 62],
    [35, 65],
    [36, 62],
    [37, 58],
    [38, 55],
    [39, 61],
    [40, 64],
    [41, 65],
    [42, 63],
    [43, 66],
    [44, 67],
    [45, 69],
    [46, 69],
    [47, 70],
    [48, 72],
    [49, 68],
    [50, 66],
    [51, 65],
    [52, 67],
    [53, 70],
    [54, 71],
    [55, 72],
    [56, 73],
    [57, 75],
    [58, 70],
    [59, 68],
    [60, 64],
    [61, 60],
    [62, 65],
    [63, 67],
    [64, 68],
    [65, 69],
    [66, 70],
    [67, 72],
    [68, 75],
    [69, 80]
  ]);

  var options = {
    hAxis: {
      title: 'Time'
    },
    vAxis: {
      title: 'Popularity'
    }
  };

  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

 //chart.draw(data, options);
  google.load("visualization", "1", {packages:["corechart"]});
  google.setOnLoadCallback(drawVisualization);
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
