const persons = ["Lars", "Jan", "Peter", "Bo", "Frederik"];

//1a
console.log(persons.filter(s => s.includes('a')));

//1b
console.log(persons.map(s => s.split("").reverse().join("")));

//2a
function myFilter(array, callback) {
    let filteredArr = [];
    for (var i = 0; i < array.length; i++) {
        let obj = array[i];
        if (callback(obj)) {
            filteredArr.push(obj);
        }
    }
    return filteredArr;
}

console.log("My filter: ", myFilter(persons, s => s.includes('a')));

//2b
function myMap(array, callback) {
    let mappedArr = [];
    for (var i = 0; i < array.length; i++) {
        let obj = array[i];
        mappedArr.push(callback(obj));
    }
    return mappedArr;
}

console.log("My map: ", myMap(persons, s => s.split("").reverse().join("")));

//3a
Array.prototype.myNewFilter = function (callback) {
    let filteredArr = [];
    for (var i = 0; i < this.length; i++) {
        let obj = this[i];
        if (callback(obj)) {
            filteredArr.push(obj);
        }
    }
    return filteredArr;
}

console.log("Filter with prototype: ", persons.myNewFilter(s => s.includes('a')));

//3b
Array.prototype.myNewMap = function (callback) {
    let mappedArr = [];
    for (var i = 0; i < this.length; i++) {
        let obj = this[i];
        mappedArr.push(callback(obj));
    }
    return mappedArr;
}

console.log("Map with prototype: ", persons.myNewMap(s => s.split("").reverse().join("")));

//4a
var numbers = [1, 3, 5, 10, 11];

console.log(numbers.map((num, index) => {
    if (index < 2) {
        return (num + 1) * 2;
    }
    if (index === 2) {
        return num * 3;
    }
    if (index === 3) {
        return num * 2 + 1;
    }
    return num;
}))

//4b
console.log('<nav>' + persons.map(s => '<a href="">' + s + '</a>').join("") + '</nav>');

//4c
var names = [{ name: "Lars", phone: "1234567" }, { name: "Peter", phone: "675843" }, { name: "Jan", phone: "98547" }, { name: "Bo", phone: "79345" }];

const tableHead = '<table><tr><th>Name</th><th>Phone</th></tr>';

console.log(tableHead + names.map(obj => '<tr><td>' + obj.name + '</td>' + '<td>' + obj.phone + '</td></tr>').join("") + '</table>');

//5a
var all = ["Lars", "Peter", "Jan", "Bo"];

console.log(all.join());
console.log(all.join(" "));
console.log(all.join("#"));

//5b
var numbers = [2, 3, 67, 33];

console.log(numbers.reduce((total, num) => total + num));

//5c
var members = [
    { name: "Peter", age: 18 },
    { name: "Jan", age: 35 },
    { name: "Janne", age: 25 },
    { name: "Martin", age: 22 }];

console.log(members.reduce((total, obj, index, arr) => total + obj.age / arr.length, 0));

//5d
var votes = [ "Clinton","Trump","Clinton","Clinton","Trump","Trump","Trump","None"];

console.log(votes.reduce((data, amount) => {
    data[amount] = (data[amount] || 0) + 1 ;
    return data;
  } , {}));