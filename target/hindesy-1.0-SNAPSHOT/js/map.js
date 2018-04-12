var map;
var geocoder;
function initMap(){
    // Map options
     geocoder = new google.maps.Geocoder();
    recupPosition();
    var TinyHouse = new google.maps.LatLng(50.633333, 3.066667);
    var options = {
        zoom: 8,
        center: TinyHouse,

        mapTypeId: google.maps.MapTypeId.TERRAIN
    }

    // New map
    map = new google.maps.Map(document.getElementById('map'), options);

}



function SetMarker(lat, long){
    console.log(lat);
    var marker = new google.maps.Marker({
        map: map,
        position: {lat: lat, lng: long},
        animation: google.maps.Animation.DROP

    });

    var infoWindow = new google.maps.InfoWindow({
        content: 'Notre Tiny House !'
    });
    marker.addListener('click', function() {
        infoWindow.open(map, marker);
    });
}



function recupPosition() {

    var position = $('#catchPosition').text();
    console.log(position);
    geocodage(position);
    }



function geocodage(positionTiny) {

    geocoder.geocode({
        'address': positionTiny
    }, function(results, status) {

        /* Si l'adresse a pu être géolocalisée */
        if (status == google.maps.GeocoderStatus.OK) {
            /* Récupération de sa latitude et de sa longitude */

            console.log(results[0].geometry.location);
            var point = results[0].geometry.location;
            var lat = results[0].geometry.location.lat();
            var lng = results[0].geometry.location.lng();
            console.log(lat);
            SetMarker(lat, lng);

        }
        console.log("erreur");
    })


}
