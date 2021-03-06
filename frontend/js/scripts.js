var clientesSvg = '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="user-tie" class="svg-inline--fa fa-user-tie fa-w-14" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path fill="currentColor" d="M224 256c70.7 0 128-57.3 128-128S294.7 0 224 0 96 57.3 96 128s57.3 128 128 128zm95.8 32.6L272 480l-32-136 32-56h-96l32 56-32 136-47.8-191.4C56.9 292 0 350.3 0 422.4V464c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48v-41.6c0-72.1-56.9-130.4-128.2-133.8z"></path></svg>'
var clientesIconUrl= 'data:image/svg+xml;base64,' + btoa(clientesSvg);

var armazensSvg = '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="warehouse" class="svg-inline--fa fa-warehouse fa-w-20" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 512"><path fill="currentColor" d="M504 352H136.4c-4.4 0-8 3.6-8 8l-.1 48c0 4.4 3.6 8 8 8H504c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8zm0 96H136.1c-4.4 0-8 3.6-8 8l-.1 48c0 4.4 3.6 8 8 8h368c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8zm0-192H136.6c-4.4 0-8 3.6-8 8l-.1 48c0 4.4 3.6 8 8 8H504c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8zm106.5-139L338.4 3.7a48.15 48.15 0 0 0-36.9 0L29.5 117C11.7 124.5 0 141.9 0 161.3V504c0 4.4 3.6 8 8 8h80c4.4 0 8-3.6 8-8V256c0-17.6 14.6-32 32.6-32h382.8c18 0 32.6 14.4 32.6 32v248c0 4.4 3.6 8 8 8h80c4.4 0 8-3.6 8-8V161.3c0-19.4-11.7-36.8-29.5-44.3z"></path></svg>'
var armazensIconUrl= 'data:image/svg+xml;base64,' + btoa(armazensSvg);

const access_token = 'pk.eyJ1IjoibW9vemR6biIsImEiOiJjazd5eGh6bjAwMGl1M21vOTdjMTI1d3NzIn0.cxQh0B_dBFEc7xNjtn0-zQ'

var mymap = L.map('mapid').setView([38.717158, -9.139066], 13);
var currentMarker
var locations = {"armazens":[],"clientes":[],"veiculos":[]}
var markers = []

var clientesIcon = L.icon({
    iconUrl: clientesIconUrl,
    iconSize:     [38, 95], 
    iconAnchor:   [17, 70],  
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
});
var armazensIcon = L.icon({
    iconUrl: armazensIconUrl,

    iconSize:     [38, 95],
    iconAnchor:   [17, 70], 
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
});

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery ?? <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: access_token
}).addTo(mymap);

mymap.on('click', onMapClick);

var geocoder = L.Control.geocoder({
    defaultMarkGeocode: false
  })
    .on('markgeocode', function(e) {
      onMarkGeocodeClick(e)
      console.log('hello')
    })
    .addTo(mymap);

$('#addLoc').on('click', newLocation);
$('#getRoutes').on('click', getRoutes);

function onMarkGeocodeClick(e) {
    console.log(e.geocode.center)
    currentMarker = e.geocode.center
    showModal()
}

function onMapClick(e) {
    currentMarker = e.latlng
    console.log(currentMarker)
    showModal()
}

function showModal(){
    $("#modelAddPoint").modal("show");
}

function newLocation() {
    var option = $( "#markerType option:selected" ).val()
    var icon2Use = option == "armazens" ? armazensIcon : clientesIcon
    var marker = L.marker({"lat":currentMarker.lat,"lng":currentMarker.lng},{icon:icon2Use}).addTo(mymap);
    var markerID = markers.push(marker) - 1

    var input = $('#nameInput').val() != "" ? $('#nameInput').val() : (option == 'armazens'? "Armazem #": "Cliente #") + markerID
    var location = {
        "latitude": currentMarker.lat,
        "longitude": currentMarker.lng,
        "name": input,
        "markerID": markerID
    }
    var newEntry = ''
    var infoID = locations[option].push(location) - 1
    console.log(locations)
    console.log(infoID)
    if (option == 'armazens') {
        marker.bindPopup("<b>"+input+"</b>")
        newEntry = getArmazemHTML(markerID,input,option,infoID)
        updateWarehouses()
    } else if (option == 'clientes') {
        marker.bindPopup("<b>"+input+"</b>")
        newEntry = getClienteHTML(markerID,input,option,infoID)
    }      
    //var newEntry = '<li class="list-group-item" id="'+markerID+'" onclick="centerMap('+markerID+')">'+$('#nameInput').val()+'<i class="fas fa-pencil-alt text-primary" onclick="editInfo(&quot;'+option+'&quot;,'+infoID+')"></i><i class="far fa-trash-alt text-danger" onclick="delLocation(&quot;'+option+'&quot;,'+infoID+')"></i></li>'//"'+option+'",'+infoID+'
      
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

function delLocation(type, infoid, markerid){
    console.log(type, infoid, markerid)
    //locations[type].splice(infoid,1)
    delete locations[type][infoid]
    if (type == 'armazens') {
        updateWarehouses()
    }
    mymap.removeLayer(markers[markerid])
    //markers.splice(markerid,1)
    delete markers[markerid]
    $("#"+type+markerid+"").remove();
}

function getArmazemHTML(markerID,input,option,infoID){
    var s = ''
    s += '<li class="list-group-item" id="'+option+markerID+'" onclick="centerMap('+markerID+')">'
    s += '  <span>'+input+'</span>'
    s += '  <button type="button" class="btn btn-outline-danger float-end" onclick="delLocation(&quot;'+option+'&quot;,'+infoID+','+markerID+')">Remover</button>'
    s += '</li>'

    return s
}
function getClienteHTML(markerID,input,option,infoID){
    var s = ''
    s += '<div class="accordion-item" id="'+option+markerID+'">'+
            '<h2 class="accordion-header" id="heading'+markerID+'">'+
                '<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse'+markerID+'" aria-expanded="false" aria-controls="collapse'+markerID+'">'+input+'</button>'+
            '</h2>'+
            '<div id="collapse'+markerID+'" class="accordion-collapse collapse" aria-labelledby="heading'+markerID+'" data-bs-parent="#'+option+markerID+'">'+
            '<div class="accordion-body">'+
                '<div class="container">'+
                '<div class="row">'+
                    '<div class="input-group mb-3">'+
                        '<span class="input-group-text">Quantidade:</span>'+
                        '<input class="form-control" type="number" value="10" id="quantity'+markerID+'">'+
                        '<span class="input-group-text">kg</span>'+
                    '</div>'+
                '</div>'+
                '<div class="row">'+
                    '<div class="input-group mb-3">'+
                        '<span class="input-group-text">Preco:</span>'+
                        '<input class="form-control" type="number" value="10" id="price'+markerID+'">'+
                        '<span class="input-group-text">???</span>'+
                    '</div>'+
                '</div>'+
                '<div class="row">'+
                    '<div class="input-group mb-3">'+
                        '<span class="input-group-text">Entrega:</span>'+
                        '<input type="time" class="form-control" value="10:00" aria-label="10:00" id="time1'+markerID+'">'+
                        '<span class="input-group-text">-</span>'+
                        '<input type="time" class="form-control" value="20:00" aria-label="20:00"id="time2'+markerID+'">'+
                    '</div>'+
                '</div>'+
                /* '<div class="row">'+
                    '<div class="form-check">'+
                    '<input class="form-check-input" type="checkbox" value="" id="parcial'+markerID+'">'+
                    '<label class="form-check-label" for="parcial'+markerID+'">Permite entregas parciais</label>'+
                    '</div>'+
                '</div>'+ */
                '<div class="row">'+
                    '<button type="button" class="btn btn-outline-danger" onclick="delLocation(&quot;'+option+'&quot;,'+infoID+','+markerID+')" >Remover</button>'+
                '</div>'+
               '</div>'+
            '</div>'+
            '</div>'+
        '</div>'
    return s
}

function addVehicle(){
    var newEntry = getVehicleHTML()
    $('#veiculosList').html($('#veiculosList').html()+newEntry)
}

function delVehicle(vehID){
    delete locations.veiculos[vehID]
    $("#veiculos"+vehID+"").remove();
}

function getVehicleHTML(){
    var vehID = locations.veiculos.push({
        "custoHora": 10,
        "custoDist": 10
    })
    console.log(vehID)
    var s = ''
    s += '<div class="accordion-item" id="veiculos'+vehID+'">'+
            '<h2 class="accordion-header" id="heading'+vehID+'">'+
                '<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseVehicle'+vehID+'" aria-expanded="false" aria-controls="collapseVehicle'+vehID+'">Veiculo #'+vehID+'</button>'+
            '</h2>'+
            '<div id="collapseVehicle'+vehID+'" class="accordion-collapse collapse" aria-labelledby="heading'+vehID+'" data-bs-parent="#veiculosList">'+
                '<div class="accordion-body">'+
                    '<div class="container">'+
                        '<div class="row">'+
                            '<div class="input-group mb-3">'+
                                '<span class="input-group-text">Custo - Hora:</span>'+
                                '<input class="form-control" type="number" id="carga'+vehID+'" value="10">'+
                                '<span class="input-group-text">???/h</span>'+
                            '</div>'+
                        '</div>'+
                        '<div class="row">'+
                            '<div class="input-group mb-3">'+
                            '<span class="input-group-text">Custo - Distancia:</span>'+
                            '<input class="form-control" type="number" value="10" id="dist'+vehID+'">'+
                            '<span class="input-group-text">???/km</span>'+
                            '</div>'+
                        '</div>'+
                        '<div class="row">'+
                            '<select class="form-select mb-3" id="vehID'+vehID+'" aria-label="Vehicle '+vehID+' select label">' +
                                getWarehouses() +
                            '</select>' +
                        '</div>'+
                        
                        '<div class="row">'+
                            '<button type="button" class="btn btn-outline-danger" onclick="delVehicle('+vehID+')">Remover</button>'+
                        '</div>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'
    return s
}
function updateWarehouses(){
    var currWarehouses = getWarehouses()
    locations.veiculos.forEach(function(item, vehID){
        vehID = vehID + 1
        console.log($("#vehID"+vehID).html())
        $("#vehID"+vehID).html(currWarehouses)
        console.log($("#vehID"+vehID).html())
    })
 }   

function getWarehouses() {
    if (locations.armazens.filter(n => n !== null || n !== undefined).length > 0 ) {
        var s='<option value="0" selected>Selecionar armazem de saida</option>'
        locations.armazens.forEach(function(item){
            s+='<option value="'+item.name+'">'+item.name+'</option>'
        })
        return s
    } else return '<option value="0" selected>Nao existem armazens</option>'
}
var line = []
var latlngs = []
var linepoly
var polyline
function getRoutes(){
    $("#percurso").html("<h3>Percursos</h3>")
    var body = {
        "clientes":[],
        "veiculos":[]
    }
    if(locations.veiculos.length == 0) {
        $(".toast-body").html("Nao existem veiculos!")
        return $('.toast').toast('show')
    }
    else {
        locations.veiculos.forEach(function(veh, vehID){
            vehID = vehID + 1
            veh.vehicleId = vehID.toString()
            veh.custoHora = parseInt($("#carga"+vehID+"").val())
            veh.custoDist = parseInt($("#dist"+vehID+"").val())
            if ($("#vehID"+vehID+"").val() == "0") {
                $(".toast-body").html("Existem veiculos sem armazem de partida!")
                return $('.toast').toast('show')
            } else {
                veh.armazemPartida = {}
                locations.armazens.forEach(function(item){
                    if($("#vehID"+vehID).val() == item.name ){
                        veh.armazemPartida.latitude = item.latitude
                        veh.armazemPartida.longitude = item.longitude
                    }
                })
                body.veiculos.push(veh)
                if(locations.clientes.length == 0){
                    $(".toast-body").html("Nao existem clientes!")
                    return $('.toast').toast('show')
                } else {
                    locations.clientes.forEach(function(item){
                        var itemObject = jQuery.extend(true, {}, item)

                        itemObject['quantity'] = parseInt($("#quantity"+itemObject.markerID+"").val())
                        itemObject['price'] = parseInt($("#price"+itemObject.markerID+"").val())
                        itemObject['timeWindow'] = [$("#time1"+itemObject.markerID+"").val(),$("#time2"+itemObject.markerID+"").val()]
                        /* itemObject['allowParcial'] = $("#parcial"+itemObject.markerID+"").is(":checked") */
                        delete itemObject.markerID
                        itemObject.name
                        body.clientes.push(itemObject)
                    })
                
                    console.log(JSON.stringify(body))
                    const colors = ["red","green","blue"]
                
                    $.ajax({
                        url: "http://localhost:8080/request",
                        method: "post",
                        contentType: "application/json",
                        data:JSON.stringify(body),
                        success: function (res, status) {
                            console.log(res.results)
                            var s="<dl>"
                            res.results.forEach(function(item){
                                s+= "<dt>Veiculo "+(parseInt(vehID))+"</dt>"
                                line = []
                                var url = "https://api.mapbox.com/directions/v5/mapbox/driving/"
                                item.clients.forEach(function(client,clientID, array){
                                    
                                    if(clientID == 0 || clientID == item.clients.length - 1) {
                                        s+= "<dd>- Armazem </dd>"
                                        //client.longitude = body.veiculos[item.vehicleId].armazemPartida.longitude
                                    }
                                    else s+= "<dd>- Client"+ clientID +"</dd>"
                                    url +=  client.longitude+","+ client.latitude
                                    if(!(clientID === array.length -1)) {
                                        url += ";"
                                    }
                                    line.push([client.latitude,client.longitude])
                                })
                                url += '?steps=true&geometries=geojson&access_token=' + access_token;
                                if (mymap.hasLayer(linepoly)) linepoly.remove(mymap)
                                linepoly = L.polyline(line, {color: colors[item.vehicleId], opacity: 0.3});
                                linepoly.addTo(mymap);
                                $.ajax({
                                    url: url,
                                    method: "get",
                                    success: function (res, status) {
                                        latlngs = [];
                                        var coords = res.routes[0].geometry;
                                        coords.coordinates.forEach(function(item){
                                            //console.log(item)
                                            latlngs.push([item[1],item[0]])
                                        })
                                        if (mymap.hasLayer(polyline)) polyline.remove(mymap)
                                        polyline = L.polyline(latlngs, {color: colors[item.vehicleId]});
                                        polyline.addTo(mymap);
                                    }
                                    ,error: function () { alert(JSON.stringify('error')); }});
                            })
                            s+="</dl>"
                            $("#percurso").html($("#percurso").html()+s)
                            }
                            
                            ,error: function () { alert(JSON.stringify('error')); }});
                }
            }
        })
    }
}


