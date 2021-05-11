var mymap = L.map('mapid').setView([51.505, -0.09], 13);
var currentMarker
var locations = {"armazens":[],"clientes":[],"veiculos":[]}
var markers = [] 

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoibW9vemR6biIsImEiOiJjazd5eGh6bjAwMGl1M21vOTdjMTI1d3NzIn0.cxQh0B_dBFEc7xNjtn0-zQ'
}).addTo(mymap);

mymap.on('click', onMapClick);

$('#addLoc').on('click', newLocation);

function onMapClick(e) {
    currentMarker = e.latlng
    $("#modelAddPoint").modal("show");
}

function newLocation() {
    var marker = L.marker({"lat":currentMarker.lat,"lng":currentMarker.lng}).addTo(mymap);
    var option = $( "#markerType option:selected" ).val()
    var markerID = markers.push(marker) - 1
    var location = {
        "latitude": currentMarker.lat,
        "longitude": currentMarker.lng,
        "name": $('#nameInput').val(),
        "markerID": markerID
    }

    var infoID = locations[option].push(location) - 1
    var newEntry = '<li class="list-group-item" id="'+markerID+'" onclick="centerMap('+markerID+')">'+$('#nameInput').val()+'<i class="fas fa-pencil-alt text-primary" onclick="editInfo(&quot;'+option+'&quot;,'+infoID+')"></i><i class="far fa-trash-alt text-danger" onclick="delLocation(&quot;'+option+'&quot;,'+infoID+')"></i></li>'//"'+option+'",'+infoID+'
    $('#'+option+'List').html($('#'+option+'List').html()+newEntry)
    $('#nameInput').val("")
    $("#modelAddPoint").modal("hide");

    currentMarker = null

    /* var toast = document.getElementById('toastNotice');//select id of toast
    var bsToast = new bootstrap.Toast(toast);//inizialize it
    bsToast.show(); */
    
}

function centerMap(marker){
    mymap.setView(markers[marker].getLatLng(), mymap.getZoom());
}

function editInfo(type,id){

}

function delLocation(type, id){
    locations[type].splice(id,1)
    mymap.removeLayer(markers[marker])
    $("#"+id+"").remove();
}