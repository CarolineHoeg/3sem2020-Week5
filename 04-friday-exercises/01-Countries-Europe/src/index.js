import 'bootstrap/dist/css/bootstrap.css'

let url = "http://restcountries.eu/rest/v1/alpha?codes";

let map = document.getElementById("svg2");
map.addEventListener("click", getCountry);

var previous;

function getCountry(evt) {
    let country = evt.target.closest('path');
    if (previous == null) {
        previous = country;
        country.style = "fill:red";
    } else {
        previous.style = "fill:#c0c0c0";
        country.style = "fill:red";
        previous = country;
    }

    if (evt.target.id.length <= 2) {
        let finalUrl = url + "=" + country.id;
        fetch(finalUrl)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                insertDataAboutCountry(data);
            });
    } else {
        let finalUrl = url + "=" + country.parentNode.id;
        fetch(finalUrl)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                insertDataAboutCountry(data);
            });
    }
}

function insertDataAboutCountry(country) {
    document.getElementById("countryDiv").innerHTML = `Country: ${country[0].name}<br>
    Population: ${country[0].population}<br>
    Area:${country[0].area}<br>
    Borders: ${country[0].borders}`;
}