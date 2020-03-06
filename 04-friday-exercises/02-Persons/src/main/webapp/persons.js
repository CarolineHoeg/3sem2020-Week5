let persons = [];

fetch('api/person/all')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            persons = data;
            document.getElementById("tableBody").innerHTML = createTable(persons);
        });


function createTable(arr) {
    let str = arr.map(person => "<tr><td>" + person.id
                + "</td><td>" + person.fName + "</td><td>" + person.lName
                + "</td><td>" + person.phone + "</td></tr>").join("");
    return str;
}

document.getElementById("reloadBtn").addEventListener("click", reload);

function reload(evt) {
    evt.preventDefault();
    fetch('api/person/all')
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                persons = data;
                document.getElementById("tableBody").innerHTML = createTable(persons);
            });
}

document.getElementById("addBtn").addEventListener("click", addPerson);

function addPerson(evt) {
    evt.preventDefault();
    let pFName = document.getElementById("fName").value;
    let pLName = document.getElementById("lName").value;
    let pPhone = document.getElementById("phone").value;
    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fName: pFName,
            lName: pLName,
            phone: pPhone
        })
    };

    fetch("api/person/add", options);
}
