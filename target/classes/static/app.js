var ip = 'localhost:8082'
var ip2  = '52.30.206.53'

var sitsListData;
var map;

window.onload = function () {
 getSitesData().then(data => {sitsListData = data;
 fillDropDown(data);
 initMap();
 handleSiteClick(1);
// document.getElementById('sitesDropDown').selectedIndex = 1

})
};

async function PingServer(site_ip, id ){
    let response= await fetch('http://' + ip + '/api/v1/sites/ping?ip=' +site_ip)
    if (response.status == 200)
        document.getElementById("Server" + id).style.color = "green";
    else
        document.getElementById("Server" + id).style.color = "red";
    }

function fillDropDown(sites){
var select = document.getElementById("sitesDropDown");
    select.options[0] = new Option("Select Site:", -1);

    for (var i = 0; i < sites.length; ++i) {
    select.options[i+1] = new Option(sites[i].name, sites[i].site_id);}
}


function OnSelectedIndexChange(){

var site_name = document.getElementById('sitesDropDown').value;
var site_id = document.getElementById('sitesDropDown').selectedIndex;

var ip = sitsListData[site_id-1].ip
var ip2 = sitsListData[site_id-1].ip2

var id = sitsListData[site_id-1].id
handleSiteClick(site_id);

}

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
         fetch('http://' + ip + ':84?on_relay1')
}

function button2_action(ip) {
window.alert("button2_action "+ ip);
         fetch('http://' + ip + ':84?button2')
}

function switch_action(ip, id) {
    {
        if (document.getElementById("switch" +id ).value == "OFF") {
            document.getElementById("switch"+id).value = "ON";
            fetch('http://' + ip + ':84?on_relay' + id)
        }

        else if (document.getElementById("switch"+id).value == "ON") {
            document.getElementById("switch" +id).value = "OFF";
            fetch('http://' + ip + ':84?off_relay'+ id)
        }
    }
}


 function reset(ip, id) {
    window.alert("reset ip " + ip);
     fetch('http://' + ip + ':84?reset_off' + id);
        setTimeout(() => {
fetch('http://' + ip + ':84?reset_on'+ id);
    window.alert("reset on ip " + ip) + " done ";

        }, 10000)

}

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 9,
        center: {
            lat: 32.1192362,
            lng: 35.5750295
        }
    });

    var bounds = {
        north: 32.0,
        south: 32.3,
        east: 34.90,
        west: 34.80
    };

    // Display the area between the location southWest and northEast.
    map.fitBounds(bounds);

//    getSitesData().then(data => {
//        drawSitesOnMap(map, data);
////        setTimeout(() => {
////            drawSiteTable(data);
////        }, 800)
//    });

    drawSitesOnMap(map, sitsListData);

    getAlertsData().then(data => {
     setTimeout(() => {
            drawAllAlerts(data);
              }, 800)
    });



}

async function getSitesData() {
    let param = " "
//    if (id != null) {
//        param = "?id=" + id
//    }
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
                lng: sitesData[i].lon,
            },
            map: map

        });
        attachMassage(marker, sitesData[i]);
    }
}

function attachMassage(marker, massage) {
    var infowindow = new google.maps.InfoWindow({
        content: '<div id="infowindow">' +(massage.name) +  '</div>'

    });

    marker.addListener('mouseover', function() {
        infowindow.open(marker.get('map'), marker);
    });
    marker.addListener('click', function() {
        handleSiteClick(massage.id);
    });
}

function handleSiteClick(id) {
    var site_ip = sitsListData[id-1].ip
    PingServer(site_ip, 1)
    PingServer(sitsListData[id-1].ip2, 2)

    var select = document.getElementById("sitesDropDown");
    select.selectedIndex = id
    fetch('http://' + ip + '/api/v1/data/?ip=' + site_ip).then(data => data.json()).then((jsonDataRaw) => {
        const siteData = jsonDataRaw.data
        var data = new google.visualization.DataTable();
        data.addColumn('datetime', 'time');
        data.addColumn('string', 'ip');
        data.addColumn('number', 'gateway');
        data.addColumn('number', 'tmp');
        data.addColumn('number', 'volt');
        data.addColumn('number', 'light');
        data.addColumn('number', 'humidity');
        for (var i = 0; i < siteData.length; i++) {
            data.addRow([new Date(siteData[i].time), siteData[i].ip, siteData[i].gateway, siteData[i].tmp, siteData[i].volt, siteData[i].light, siteData[i].humidity]);
        }
        drawDataTable(data)
        drawChart(data)
        drawMeters(siteData[0])
    })

    drawSiteInfo(sitsListData[id-1])
    getAlertsData(id).then(data => drawSiteAlerts(data)) //todo remove array to json

    document.querySelector('#buttons').style.display = 'flex';
    document.querySelector('#buttons').style.flex = '0.2';
    document.querySelector('#extra_data').style.display = 'flex';

    document.getElementById("switch1").onclick = function() {switch_action(site_ip, 1)}
    document.getElementById("switch2").onclick = function() {switch_action(site_ip, 2)}

    document.getElementById("reset1").onclick = function() {reset(site_ip, 1)}
    document.getElementById("reset2").onclick = function() {reset(sitsListData[id-1].ip2, 2)}

    document.getElementById("cameras").onclick = function() {goToCameras(sitsListData[massage.id-1].cameras_link)}
        var bounds = {
        north: sitsListData[id-1].lat - 0.1,
        south: sitsListData[id-1].lat + 0.1,
//        east: sitsListData[massage.id-1].lon - 0.1,
//        west: sitsListData[massage.id-1].lon + 0.1,
   east: 34.90,
        west: 34.80
    };

    // Display the area between the location southWest and northEast.
    map.fitBounds(bounds);

}

function drawChart(data) {

    var view = new google.visualization.DataView(data);
    view.setColumns([0, 3, 4,5,6]);

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
        },
        width: '300%',
               height:'300%'
    };
    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(view, options);
}

function drawDataTable(data) {
    data.sort({
        column: 0,
        desc: true
    });
    var table = new google.visualization.Table(document.getElementById('table_div'));

var options =
     {
       allowHtml: true,
       showRowNumber: false,
       width: '100%',
       height: '100%',

       cssClassNames: {
         headerRow: 'headerRow',
         tableRow: 'tableRow',
         oddTableRow: 'oddTableRow',
         selectedTableRow: 'selectedTableRow',
         hoverTableRow: 'hoverTableRow',
         headerCell: 'headerCell',
         tableCell: 'tableCell',
         rowNumberCell: 'rowNumberCell'
       }

     };
    table.draw(data, options);
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
        height: 200,
        redFrom: 60,
        redTo: 100,
        yellowFrom: 40,
        yellowTo: 60,
        minorTicks: 5
    };
    var humidity_options = {
        width: 500,
        height: 200,
        redFrom: 60,
        redTo: 100,
        yellowFrom: 40,
        yellowTo: 60,
        minorTicks: 5
    };
    var volt_options = {
        width: 200,
        height: 200,
        redFrom: 30,
        redTo: 40,
        yellowFrom: 0,
        yellowTo: 23,
        minorTicks: 5,
        max: 40
    };
    var light_options = {
        width: 600,
        height: 200,
        redFrom: 900,
        redTo: 1024,
        yellowFrom: 700,
        yellowTo: 900,
        minorTicks: 5,
//        majorTicks:['0','200','400','600','800'],
        max:1024
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
    data.addRow(['ip2', siteInfo.ip2]);
    data.addRow(['contact_person', siteInfo.contact_person]);
    data.addRow(['contact_phone', siteInfo.contact_phone]);
    data.addRow(['description', siteInfo.description]);
    data.addRow(['provider1', siteInfo.provider1]);
    data.addRow(['provider2', siteInfo.provider2]);
    data.addRow(['provider3', siteInfo.provider3]);
    data.addRow(['provider4', siteInfo.provider4]);

    var table = new google.visualization.Table(document.getElementById('site_info_div'));

   var options =
        {
          allowHtml: true,
          showRowNumber: false,
          width: '100%',
          height: '100%',

          cssClassNames: {
            headerRow: 'headerRow',
            tableRow: 'tableRow',
            oddTableRow: 'oddTableRow',
            selectedTableRow: 'selectedTableRow',
            hoverTableRow: 'hoverTableRow',
            headerCell: 'headerCell',
            tableCell: 'tableCell',
            rowNumberCell: 'rowNumberCell'
          }
        };
       table.draw(data, options);
}

function drawSiteAlerts(alerts) {

    var data = new google.visualization.DataTable();
    data.addColumn('datetime', 'time');
    data.addColumn('string', 'name');
    data.addColumn('string', 'type');
    data.addColumn('number', 'value');

    for (var i = 0; i < alerts.length; i++) {
        data.addRow([new Date(alerts[i].time), alerts[i].name, alerts[i].type, alerts[i].value]);
    }

    data.sort({
            column: 0,
            desc: true
        });

    var table = new google.visualization.Table(document.getElementById('site_alerts'));

   var options =
        {
          allowHtml: true,
          showRowNumber: false,
          width: '100%',
          height: '100%',

          cssClassNames: {
            headerRow: 'headerRow',
            tableRow: 'tableRow',
            oddTableRow: 'oddTableRow',
            selectedTableRow: 'selectedTableRow',
            hoverTableRow: 'hoverTableRow',
            headerCell: 'headerCell',
            tableCell: 'tableCell',
            rowNumberCell: 'rowNumberCell'
          }
        };
       table.draw(data, options);
}

function drawAllAlerts(alerts) {

    var data = new google.visualization.DataTable();
        data.addColumn('datetime', 'time');
        data.addColumn('string', 'name');
        data.addColumn('string', 'type');
        data.addColumn('number', 'value');

        for (var i = 0; i < alerts.length; i++) {
            data.addRow([new Date(alerts[i].time), alerts[i].name, alerts[i].type, alerts[i].value]);
        }


    data.sort({
            column: 0,
            desc: true
        });

   var table = new google.visualization.Table(document.getElementById('all_alerts'));
   var options =
     {
       allowHtml: true,
       showRowNumber: false,
                 width: '100%',
                 height: '100%',
       cssClassNames: {
         headerRow: 'headerRow',
         tableRow: 'tableRow',
         oddTableRow: 'oddTableRow',
         selectedTableRow: 'selectedTableRow',
         hoverTableRow: 'hoverTableRow',
         headerCell: 'headerCell',
         tableCell: 'tableCell',
         rowNumberCell: 'rowNumberCell'
       }
     };

    table.draw(data, options);

}

function goToCameras(link)
{
window.open(link, '_blank');}
