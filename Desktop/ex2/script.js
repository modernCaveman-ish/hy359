function passwordMissmatch() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    let unMatched = document.getElementById("unMatched");
    let matched = document.getElementById("matched");

    console.log('onkeyup works ' + password + ' ' + confirmPassword);

    if( password == confirmPassword ){
        if(confirmPassword == "" || password == "") {
            unMatched.style.display = "none";
            matched.style.display = "none";
            return false;    
        }
        console.log("passwordMissmatch approves");
        unMatched.style.display = "none";
        matched.style.display = "block";
        return true;
    } else {
        if(confirmPassword == "" || password == "") {
            unMatched.style.display = "none";
            matched.style.display = "none";
            return false;
        }
        console.log("passwordMissmatch doesn't approve");
        unMatched.style.display = "block";
        matched.style.display = "none";
        return true;
    }
}

function filterWhitespace(string){
    for(var c in string){
        if(string[i] == ' '){
            string[i] = '+';
        }
        while(string[i] == ' '){
            i++;
        }
    }
    return string;
}

function sendMeLocation(){
    // Just to be sure
    document.getElementById('locationNotFoundMessage').style.display = "none";
    
    // Kill the Map if needed
    killMap();

    var street = document.getElementById('street').value;
    var city = document.getElementById('city').value;
    var country = document.getElementById('country').value;

    //console.log('Country is ' + country);

    //Url is for API https://nominatim.openstreetmap.org/search?<params>
    var url = 'https://nominatim.openstreetmap.org/search?';

    if(!street || !city || !country){
        var error_message = 'Empty fields: ';
        if(!street){
            error_message += 'Street ';
        } if(!city){
            error_message += 'city ';
        } if(!country){
            error_message += 'Country ';
        }
        console.log(error_message);
    }

    if(street || city || country){
        if(street){
            url += 'street=';
            url += street; 
            url += '&';
        } if(city){
            url += 'city=';
            url += city;
            url += '&';
        } if(country){
            url += 'country=';
            url += country;
            url += '&';
        }
    }

    url = url.replace(/\s+/g, '+');;

    // https://nominatim.openstreetmap.org/ui/search.html?street=the+hill&country=uk

    url += 'format=json';

    console.log('URL is: ' + url);

    var xhttp = new XMLHttpRequest();

    let data;
    xhttp.open('GET', url, true);
    
    xhttp.send(); 
    
    let map;

    xhttp.onreadystatechange = function() {
        if(xhttp.status == 200 && xhttp.readyState > 3){
            data = JSON.parse(xhttp.responseText);
            //console.log('response text: ' + data);
            console.log('Trying to stringify json: ' + JSON.stringify(data, null, 4));
            
            hideUnhideLocationError(data);
            // display On Map data if Location is valid
            if(data != ""){
                map = displayOnMap(data);
            }
        }
    };

}


async function hideUnhideLocationError(data) {
    if(data == "") {
        console.log('Error location does not exist ' + data);
        document.getElementById('locationNotFoundMessage').style.display = "block";
    }else {
        console.log('data is' + data);
        document.getElementById('locationNotFoundMessage').style.display = "none";
        
        // If location is valid create Map and display it on it
        //displayOnMap(data);
    }    
}

// Take the firt object that openStreetMaps returns and display it on the map
 function displayOnMap(data){

    console.log("Display On Map is on Lon " + data[0].lon + ' lat ' + data[0].lat);

    //Display map
    document.getElementById('mapdiv').style.display = "block";

    map = new OpenLayers.Map("mapdiv");
    map.addLayer(new OpenLayers.Layer.OSM());

    // var lonLat = new OpenLayers.LonLat( -0.1279688 ,51.5077286 )
    var lonLat = new OpenLayers.LonLat( data[0].lon ,data[0].lat )
          .transform(
            new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
            map.getProjectionObject() // to Spherical Mercator Projection
          );
          
    var zoom=16;

    var markers = new OpenLayers.Layer.Markers( "Markers" );
    map.addLayer(markers);
    
    markers.addMarker(new OpenLayers.Marker(lonLat));
    
    map.setCenter (lonLat, zoom);

    return map;
}

async function killMap() {
    let mapdiv = document.getElementById('mapdiv');

    if(mapdiv.style.display == "block") {
        mapdiv.style.display = "none";
    }

    // Delete Previous addition by displayOnMap
    mapdiv.innerHTML = "";
}

// Get info given form browser geolocation
function getGeolocation() {
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
        
        getExactLocation();
    } else {
        console.log("Geolocation is unavailable");
    }
}

function showPosition(position) {
    console.log( "Latitude: " + position.coords.latitude +
    "\nLongitude: " + position.coords.longitude);
    
    let coordinates = {
        latitude: position.coords.latitude,
        longitude: position.coords.longitude
    };

    getExactLocation(coordinates);
}

// function success(pos) {
//     var crd = pos.coords;
//     return crd;
// }

//Problem: cant get to return coords from getExactLocation()

// TODO Use AJAX Requests to get
//  async function getExactLocation(){
//     let coordinates;
//     navigator.geolocation.getCurrentPosition(function(pos, coordinates){
//         let crd = pos.coords;
//         coordinates = crd;
//         console.log("printing coords found "+ coordinates.longitude 
//         + '\n' + coordinates.latitude);
//     });

//    console.log("This is getExactLocation " + coordinates);
// }

//GOt to watch promises and async. I think i need promises or arrow functions 
// so i dont get any errors then its easy

function getExactLocation(coordinates){

    // URL https://nominatim.openstreetmap.org/reverse?lat=<value>&lon=<value>&<params>
    var url;

    let xmr = new XMLHttpRequest();

    url = "https://nominatim.openstreetmap.org/reverse?lat=";
    // add latitude
    url += coordinates.latitude;
    url += "&lon=";
    // add longitude
    url += coordinates.longitude;
    // return json format
    url += "&format=json";

    console.log('URL is: ' + url);
    
    xmr.open('GET', url, true);

    xmr.send();

    let data;
    xmr.onreadystatechange = async function() {
        if(xmr.status == 200 && xmr.readyState > 3){
            data = JSON.parse(xmr.responseText);
            console.log("Stringifying json data " + JSON.stringify(data, null, 4));
            console.log("Address: " + data.address.road + ',' +
             data.address.house_number + '\nCity: ' + data.address.city + 
             '\nCountry: ' + data.address.country);

             // Since we have the location info we need to create a button to
             // user autofill his location
            document.getElementById('panel').innerHTML += '<button id="autofill_button">Fill Location</button>';
            
            let button = document.getElementById('autofill_button');
            button.addEventListener('click', function() {
                document.getElementById('street').value = data.address.road + ', ' + data.address.house_number;
                document.getElementById('city').value = data.address.city;
                document.getElementById('country').value = data.address.country;
            })
        }
        else if (xmr.status >= 400) {
            console.log('Error, could not retrieve location info..');
            console.log('xmr.status == ' + xmr.status);
            
            // display error message on the html page
            document.getElementById('street').innerHTML = "  Autofill unavailable";
        }
    }
}

