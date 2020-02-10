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
    marker.addListener('dblclick', function () {
      location.href = "site.html";
    });
  }
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