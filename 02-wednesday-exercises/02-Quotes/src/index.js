import 'bootstrap/dist/css/bootstrap.css'

let btn = document.getElementById("quoteBtn");
btn.addEventListener("click", getQuote);

function getQuote(evt) {
    evt.preventDefault();
    fetch('https://studypoints.info/jokes/api/jokes/period/hour')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            document.getElementById("quote").innerHTML = data.joke;
        });
}

setInterval(getQuote, 3600000);


