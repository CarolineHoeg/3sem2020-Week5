import 'bootstrap/dist/css/bootstrap.css'
import jokes from "./jokes";

const allJokes = jokes.getJokes().map(joke => "<li>" + joke + "</li>");
document.getElementById("jokes").innerHTML = allJokes.join("");

//Get by id
let idBtn = document.getElementById("idBtn");
idBtn.addEventListener("click", getById);

function getById(evt) {
    evt.preventDefault();
    let jokeById = jokes.getJokeById(document.getElementById('inputId').value);
    document.getElementById("joke").innerHTML = "<li>" + jokeById + "</li>";
}

//Add joke
let addBtn = document.getElementById("addBtn");
addBtn.addEventListener("click", addJoke);

function addJoke(evt) {
    evt.preventDefault();
    jokes.addJoke(document.getElementById('inputJoke').value);
    const allJokes = jokes.getJokes().map(joke => "<li>" + joke + "</li>");
    document.getElementById("jokes").innerHTML = allJokes.join("");
}
