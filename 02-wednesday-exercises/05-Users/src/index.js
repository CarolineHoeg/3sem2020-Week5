import 'bootstrap/dist/css/bootstrap.css'

function getAll() {
    fetch('http://localhost:3333/api/users')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (data.status) {
                document.getElementById("searchError").innerHTML = data.msg;
            } else {
                document.getElementById("tableBody").innerHTML = createTableFromArray(data);
            }
        });
}

getAll();

function createTableFromArray(arr) {
    let str = arr.map(user => "<tr><td>" + user.id
        + "</td><td>" + user.age + "</td><td>" + user.name
        + "</td><td>" + user.gender + "</td><td>" + user.email + "</td></tr>").join("");
    return str;
}

function createTableFromObject(user) {
    let str = "<tr><td>" + user.id
        + "</td><td>" + user.age + "</td><td>" + user.name
        + "</td><td>" + user.gender + "</td><td>" + user.email + "</td></tr>";
    return str;
}

//Get by ID
let idBtn = document.getElementById("idBtn");
idBtn.addEventListener("click", getById);

function getById(evt) {
    evt.preventDefault();
    let id = document.getElementById('idInput').value;
    let url = 'http://localhost:3333/api/users/' + id;
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (data.status) {
                document.getElementById("searchError").innerHTML = data.msg;
            } else {
                document.getElementById("tableBody").innerHTML = createTableFromObject(data);
            }
        }).catch(function (err) {
            console.log(err);
        });
}

//Add new user
let addBtn = document.getElementById("addBtn");
addBtn.addEventListener("click", addUser);

function addUser(evt) {
    evt.preventDefault();
    let uName = document.getElementById("userName").value;
    let uAge = document.getElementById("userAge").value;
    let uEmail = document.getElementById("userEmail").value;
    let genders = document.getElementsByName("gender");
    let uGender;
    for (var i = 0; i < genders.length; i++) {
        if (genders[i].checked) {
            uGender = genders[i].value;
        }
    }
    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: uName,
            age: uAge,
            gender: uGender,
            email: uEmail
        })
    }

    fetch("http://localhost:3333/api/users/", options);
    getAll();
}

//Edit user
let getEditBtn = document.getElementById("getEditBtn");
getEditBtn.addEventListener("click", getUserToEdit);

function getUserToEdit(evt) {
    evt.preventDefault();
    let id = document.getElementById('editIdInput').value;
    let url = 'http://localhost:3333/api/users/' + id;
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (data.status) {
                document.getElementById("searchEditError").innerHTML = data.msg;
            } else {
                document.getElementById("updateName").value = data.name;
                document.getElementById("updateAge").value = data.age;
                document.getElementById("updateEmail").value = data.email;
                if (data.gender === "male") {
                    document.getElementById("updateMale").checked = true;
                } else {
                    document.getElementById("updateFemale").checked = true;
                }
                document.getElementById("editDiv").style = "visibility: visible;"
            }
        });
}

let updBtn = document.getElementById("editBtn");
updBtn.addEventListener("click", editUser);

function editUser(evt) {
    evt.preventDefault();
    let id = document.getElementById("editIdInput").value;
    let url = "http://localhost:3333/api/users/" + id;
    let uName = document.getElementById("updateName").value;
    let uAge = document.getElementById("updateAge").value;
    let uEmail = document.getElementById("updateEmail").value;
    let genders = document.getElementsByName("gender");
    let uGender;
    for (var i = 0; i < genders.length; i++) {
        if (genders[i].checked) {
            uGender = genders[i].value;
        }
    }

    let options = {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: uName,
            age: uAge,
            gender: uGender,
            email: uEmail
        })
    }

    fetch(url, options);
    getAll();
}

//Delete user
let delBtn = document.getElementById("delBtn");
delBtn.addEventListener("click", deleteUser);

function deleteUser(evt) {
    evt.preventDefault();
    let id = document.getElementById("userId").value;
    let url = "http://localhost:3333/api/users/" + id;
    let options = {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    }

    fetch(url, options);
    getAll();
}
