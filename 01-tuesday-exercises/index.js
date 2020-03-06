const names = [{ name: "Lars", phone: "1234567" }, { name: "Peter", phone: "675843" }, { name: "Jan", phone: "98547" }, { name: "Bo", phone: "79345" }];

function createTableRow(obj) {
    return '<tr><td>' + obj.name + '</td>' + '<td>' + obj.phone + '</td></tr>';
}

function insertArrayInTable(arr) {
    document.getElementById("tableBody").innerHTML = arr.map(createTableRow).join("");
}

insertArrayInTable(names);

let namesWithA = names.filter(obj => obj.name.toLowerCase().includes('a'));

//document.getElementById("btn").onclick = insertArrayInTable(namesWithA);
