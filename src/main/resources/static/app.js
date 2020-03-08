var ip2 = 'localhost:8082'
var ip = '52.30.206.53'

google.charts.load('current', {
    packages: ['corechart', 'line', 'table', 'gauge']
});

function showModal() {
    document.querySelector('#modal').style.display = 'flex';
}

function closeModal() {
    document.querySelector('#modal').style.display = 'none';
}

function button1_action(ip){
    window.alert("button1_action "+ ip);

}
function button2_action(ip) {
window.alert("button2_action "+ ip);

}

function reset(ip) {
    window.alert("reset ip " + ip);
    fetch('http://' + ip + "?/reset");
}

function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 8,
        center: {
            lat: 32.1192362,
            lng: 35.5750295
        }
    });

    var bounds = {
        north: 32.1,
        south: 32.2,
        east: 34.90,
        west: 34.80
    };

    // Display the area between the location southWest and northEast.
    map.fitBounds(bounds);

    getSitesData().then(data => {
        drawSitesOnMap(map, data);
        setTimeout(() => {
            drawSiteTable(data);
        }, 800)
    });


    getAlertsData().then(data => {
     setTimeout(() => {
            drawAllAlerts(data);
              }, 800)
    });



}

async function getSitesData(id) {
    let param = " "
    if (id != null) {
        param = "?id=" + id
    }
    let response = await fetch('http://' + ip + '/api/v1/sites' + param);
    let data = await response.json()
    return data.sites;

}

async function getAlertsData(site_id) {
    let param = " "
    if (site_id != null) {
        param = "?site_id=" + site_id
    }
    let response = await fetch('http://' + ip + '/api/v1/alerts' + param);
    let data = await response.json()
    return data.alerts;

}


function drawSitesOnMap(map, sitesData) {
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
}

function attachMassage(marker, massage) {
    var infowindow = new google.maps.InfoWindow({
        content: (massage.name)
    });

    marker.addListener('mouseover', function() {
        infowindow.open(marker.get('map'), marker);
    });
    marker.addListener('click', function() {
        handleClick(massage);
    });
}

function handleClick(massage) {
    fetch('http://' + ip + '/api/v1/data/?ip=' + massage.ip).then(data => data.json()).then((jsonDataRaw) => {
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
        drawMeters(siteData[siteData.length - 1])
    })
    getSitesData(massage.id).then(data => drawSiteInfo(data)) //todo remove array to json
    getAlertsData(massage.id).then(data => drawSiteAlerts(data)) //todo remove array to json

    document.querySelector('#buttons').style.display = 'flex';
    document.querySelector('#buttons').style.flex = '1';
    document.querySelector('#extra_data').style.display = 'flex';
    document.getElementById("button1").onclick = function() {button1_action(massage.ip)}
    document.getElementById("button2").onclick = function() {button1_action(massage.ip)}
    document.getElementById("reset").onclick = function() {reset(massage.ip)}


}

function drawChart(data) {
    var options = {
        hAxis: {
            title: 'Time',
            format: 'hh:mm',
            gridlines: {
                count: 9
            }
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

    table.draw(data, {
        showRowNumber: true,
        width: '100%',
        height: '100%'
    });
}

function drawSiteTable(sitesData) {

    const data = new google.visualization.DataTable();
    data.addColumn('string', 'name');
    data.addColumn('string', 'ip');
    data.addColumn('number', 'id');


    for (var i = 0; i < sitesData.length; i++) {
        data.addRow([sitesData[i].name, sitesData[i].ip,sitesData[i].id,]);

    }

    data.sort({
        column: 0,
        desc: true
    });

    var view = new google.visualization.DataView(data);

     view.setColumns([0]);//only use the first column

    var table = new google.visualization.Table(document.getElementById('site-list'));


    table.draw(view, {
        width: '100%',
        height: '100%'
    });

    google.visualization.events.addListener(table, 'select', siteTableClickHandler);

    function siteTableClickHandler(massage){
        var selection = table.getSelection();
        var item = selection[selection.length -1];

       var ip = data.getFormattedValue(item.row, 1);
       var id =  data.getFormattedValue(item.row, 2);
       handleClick({ip,id})
}
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
        width: 500,
        height: 150,
        redFrom: 60,
        redTo: 100,
        yellowFrom: 40,
        yellowTo: 60,
        minorTicks: 5
    };
    var humidity_options = {
        width: 500,
        height: 150,
        redFrom: 60,
        redTo: 100,
        yellowFrom: 40,
        yellowTo: 60,
        minorTicks: 5
    };
    var volt_options = {
        width: 600,
        height: 150,
        redFrom: 30,
        redTo: 40,
        yellowFrom: 0,
        yellowTo: 23,
        minorTicks: 5,
        max: 40
    };
    var light_options = {
        width: 600,
        height: 150,
        redFrom: 90,
        redTo: 100,
        yellowFrom: 75,
        yellowTo: 90,
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


    data.addRow(['name', siteInfo[0].name]); //todo remove array
    data.addRow(['ip', siteInfo[0].ip]);
    data.addRow(['lat', siteInfo[0].lat.toString()]);
    data.addRow(['lon', siteInfo[0].lon.toString()]);
    data.addRow(['description', siteInfo[0].description]);
    data.addRow(['id', siteInfo[0].id.toString()]);
    data.addRow(['provider1', siteInfo[0].provider1]);
    data.addRow(['provider2', siteInfo[0].provider2]);

    var table = new google.visualization.Table(document.getElementById('site_info_div'));

    table.draw(data, {
        width: '100%',
        height: '100%'
    });
}

function drawSiteAlerts(alerts) {

    var data = new google.visualization.DataTable();
    data.addColumn('datetime', 'time');
    data.addColumn('string', 'type');
    data.addColumn('number', 'value');

    for (var i = 0; i < alerts.length; i++) {
        data.addRow([new Date(alerts[i].time), alerts[i].type, alerts[i].value]);
    }

    var table = new google.visualization.Table(document.getElementById('site_alerts'));

    table.draw(data, {
        width: '100%',
        height: '100%'
    });
}

function drawAllAlerts(alerts) {

    var data = new google.visualization.DataTable();
    data.addColumn('datetime', 'time');
    data.addColumn('string', 'type');
    data.addColumn('number', 'value');

    for (var i = 0; i < alerts.length; i++) {
        data.addRow([new Date(alerts[i].time), alerts[i].type, alerts[i].value]);
    }


    data.addRow([new Date('Mar 5, 2020, 10:28:40 PM'), 'volt', 0.1]);
    data.addRow([new Date('Mar 5, 2020, 11:28:40 PM'), 'tmp', 50.1]);
    data.addRow([new Date('Mar 5, 2020, 12:28:40 PM'), 'volt', 35.1]);
    data.addRow([new Date('Mar 5, 2020, 14:28:40 PM'), 'light', 0.]);


    var table = new google.visualization.Table(document.getElementById('all_alerts'));

    table.draw(data, {
        width: '100%',
        height: '100%'
    });
}
