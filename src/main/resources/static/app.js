function initMap() {
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 5,
    center: { lat: 32.1192362, lng: 35.5750295 }
  });

  var bounds = {
    north: 32.1,
    south: 32.2,
    east: 35.55,
    west: 35.59
  };


  // Display the area between the location southWest and northEast.
  map.fitBounds(bounds);

  // Add 5 markers to map at random locations.
  // For each of these markers, give them a title with their index, and when
  // they are clicked they should open an infowindow with text from a secret
  // message.
  var secretMessages = ['This', 'is', 'the', 'secret', 'message'];
  var lngSpan = bounds.east - bounds.west;
  var latSpan = bounds.north - bounds.south;

  // var client = new httpGetAsync();
  // client.get('http://localhost:8082/api/v1/Sites', function (response) {
  //   log(response)
  // });

  for (var i = 0; i < secretMessages.length; ++i) {
    var marker = new google.maps.Marker({
      position: {
        lat: bounds.south + latSpan * Math.random(),
        lng: bounds.west + lngSpan * Math.random()
      },
      map: map
    });
    attachSecretMessage(marker, secretMessages[i]);
  }
}


// Attaches an info window to a marker with the provided message. When the
// marker is clicked, the info window will open with the secret message.
function attachSecretMessage(marker, secretMessage) {
  var infowindow = new google.maps.InfoWindow({
    content: secretMessage
  });

  marker.addListener('click', function () {
    infowindow.open(marker.get('map'), marker);
  });
}

// function httpGetAsync(theUrl, callback) {
//   var xmlHttp = new XMLHttpRequest();
//   xmlHttp.onreadystatechange = function () {
//     if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
//       callback(xmlHttp.responseText);
//   }
//   xmlHttp.open("GET", theUrl, true); // true for asynchronous
//   xmlHttp.send(null);
// }
