var ip2 = 'localhost:8082'
var ip= '54.78.141.32'
var router = 1
var sitesListData;
var map;
var site_id;
var slider_val_div;
var chosen_ste_id = getParameterByName("site_id") == null ? 1 : getParameterByName("site_id")

window.onload = function () { //todo
    getSitesData().then(data => {
        sitesListData = data;
        fillDropDown(data);
        initMap();
        handleSiteClick(chosen_ste_id);

        setInterval(function(){  handleSiteClick(chosen_ste_id); }, 30000);
    })
};

function handle_pwm(){
    var sliderDiv = document.getElementById("pwm_range");
            sliderDiv.oninput = function () {
              slider_val_div.innerHTML = this.value;
            }
    slider_val_div = document.getElementById("slider_val");
    slider_val_div.innerHTML = sitesListData[chosen_ste_id-1].pwm
    sliderDiv.value =sitesListData[chosen_ste_id-1].pwm
}

async function PingServer(site_ip, id) {
    let response = await fetch('http://' + ip + '/api/v1/sites/ping?ip=' + site_ip)
    if (response.status == 200)
        document.getElementById("Router" + id).style.color = "green";
    else
        document.getElementById("Router" + id).style.color = "red";
}

function fillDropDown(sites) {
    var select = document.getElementById("sitesDropDown");
    select.options[0] = new Option("Select Site:", -1);

    for (var i = 0; i < sites.length; ++i) {
        select.options[i + 1] = new Option(sites[i].name, sites[i].site_id);
    }
}


function OnSelectedIndexChange() {

    var site_name = document.getElementById('sitesDropDown').value;
    chosen_ste_id = document.getElementById('sitesDropDown').selectedIndex;

    handleSiteClick(chosen_ste_id);
    updateQueryStringParameter("site_id",chosen_ste_id )

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

function switch_action(site_id, switch_id) {
    if (router == 1)
        target_ip = sitesListData[site_id - 1].ip
    else
        target_ip = sitesListData[site_id - 1].ip2


    if (switch_id == 1) {
        if (document.getElementById("switch1").checked) {
            fetch('http://' + target_ip + ':84?on_relay' + switch_id)
            sitesListData[site_id - 1].switch1 = true
            change_switch_status(site_id, 1, 1)
        }
        else {
            fetch('http://' + target_ip + ':84?off_relay' + switch_id)
            sitesListData[site_id - 1].switch1 = false
            change_switch_status(site_id, 1, 0)
        }
    }
    if (switch_id == 2) {
        if (document.getElementById("switch2").checked) {
            fetch('http://' + target_ip + ':84?on_relay' + switch_id)
            sitesListData[site_id - 1].switch2 = true
            change_switch_status(site_id, 2, 1)
        }
        else {
            fetch('http://' + target_ip + ':84?off_relay' + switch_id)
            sitesListData[site_id - 1].switch2 = false
            change_switch_status(site_id, 2, 0)        }
    }

}

function router_switch_action() {
    if (router == 1) {
        router = 2
    }
    else {
        router = 1
    }
}


function reset(site_id, reset_id) {

    if (router == 1)
        target_ip = sitesListData[site_id - 1].ip
    else
        target_ip = sitesListData[site_id - 1].ip2

    window.alert("reset ip " + target_ip);
    fetch('http://' + target_ip + ':84?reset_off' + reset_id);
    setTimeout(() => {
        fetch('http://' + target_ip + ':84?reset_on' + reset_id);
        window.alert("reset on ip " + target_ip) + " done ";

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

    drawSitesOnMap(map, sitesListData);

    getAlertsData().then(data => {
        setTimeout(() => {
            drawAllAlerts(data);
        }, 800)
    });



}

async function getSitesData() {
    let response = await fetch('http://' + ip + '/api/v1/sites');
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
        content: '<div id="infowindow">' + (massage.name) + '</div>'

    });

    marker.addListener('mouseover', function () {
        infowindow.open(marker.get('map'), marker);
    });
    marker.addListener('click', function () {
        handleSiteClick(massage.id);
    });
}

function handleSiteClick(site_id) {
    console.log(site_id)
    var site_ip = sitesListData[site_id - 1].ip
    handle_pwm()
    PingServer(site_ip, 1).then(PingServer(sitesListData[site_id - 1].ip2, 2))
    var select = document.getElementById("sitesDropDown");
    select.selectedIndex = site_id
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
        drawMeters(siteData[site_id - 1])
    })

    drawSiteInfo(sitesListData[site_id - 1])
    getAlertsData(site_id).then(data => drawSiteAlerts(data)) //todo remove array to json

    document.querySelector('#buttons').style.display = 'flex';
    document.querySelector('#buttons').style.flex = '0.2';
    document.querySelector('#extra_data').style.display = 'flex';

    document.getElementById("switch1").onclick = function () { switch_action(site_id, 1) }
    document.getElementById("switch2").onclick = function () { switch_action(site_id, 2) }

    document.getElementById("reset1").onclick = function () { reset(site_id, 1) }
    document.getElementById("reset2").onclick = function () { reset(site_id, 2) }

    document.getElementById("cameras").onclick = function () { goToCameras(sitesListData[massage.site_id - 1].cameras_link) }
    var bounds = {
        north: sitesListData[site_id - 1].lat - 0.1,
        south: sitesListData[site_id - 1].lat + 0.1,
        east: 34.90,
        west: 34.80
    };

    // Display the area between the location southWest and northEast.
    map.fitBounds(bounds);


    if (sitesListData[site_id - 1].switch1 == true)
        document.getElementById("switch1").checked = true
    else
        document.getElementById("switch1").checked = false


     if (sitesListData[site_id - 1].switch2 ==true)
        document.getElementById("switch2").checked = true
     else
        document.getElementById("switch2").checked = false
}

function drawChart(data) {

    var view = new google.visualization.DataView(data);
    view.setColumns([0, 3, 4, 5, 6]);

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
        height: '300%'
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
        max: 1024
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

function goToCameras(link) {
    window.open(link, '_blank');
}

async function pwm_click() {
    if (router == 1)
        target_ip = sitesListData[chosen_ste_id - 1].ip
    else
        target_ip = sitesListData[chosen_ste_id - 1].ip2
    sitesListData[chosen_ste_id-1].pwm = slider_val_div.innerHTML
    fetch('http://' + target_ip + ':84?pwm=' + slider_val_div.innerHTML)
    await fetch('http://' + ip + '/api/v1/sites/pwm/update?site_id=' + chosen_ste_id + '&pwm='+ slider_val_div.innerHTML)

}

async function change_switch_status(site_id, switch_id, status) {

  await fetch('http://' + ip + '/api/v1/sites/switch/update?site_id=' + site_id + '&switch_id='+ switch_id + '&status=' + status)
  }

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return parseInt(decodeURIComponent(results[2].replace(/\+/g, ' ')));
}


var updateQueryStringParameter = function (key, value) {
    var baseUrl = [location.protocol, '//', location.host, location.pathname].join(''),
        urlQueryString = document.location.search,
        newParam = key + '=' + value,
        params = '?' + newParam;

    // If the "search" string exists, then build params from it
    if (urlQueryString) {
        keyRegex = new RegExp('([\?&])' + key + '[^&]*');

        // If param exists already, update it
        if (urlQueryString.match(keyRegex) !== null) {
            params = urlQueryString.replace(keyRegex, "$1" + newParam);
        } else { // Otherwise, add it to end of query string
            params = urlQueryString + '&' + newParam;
        }
    }
    window.history.replaceState({}, "", baseUrl + params);
};