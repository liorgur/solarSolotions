var ip2 = 'localhost:8082'
var ip= '54.78.141.32'

window.onload = async function () { //todo
    let response = await validate_pass()
    if (response == true) {
    loadjs("app.js")

    }
    else
        {
        await window.onload()
        }
        }


async function validate_pass()
{
var password = "please";
var x = prompt("Enter in the password ","");
let response = await fetch('http://' + ip + '/api/v1/security/validate_pass?pass=' + x)
if (response.status == 200) {
    return true
}
else {
 alert("Wrong Password");
return false
}

  }



function loadjs(file) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = file;
    script.onload = function () { //todo
                         getSitesData().then(data => {
                             sitesListData = data;
                             fillDropDown(data);
                             initMap();
                             handleSiteClick(chosen_ste_id);

                             setInterval(function(){  handleSiteClick(chosen_ste_id); }, 30000);
                         })
                       }
    document.body.appendChild(script);

 }



