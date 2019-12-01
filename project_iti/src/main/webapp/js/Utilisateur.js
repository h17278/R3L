/*
const getUserDetails = function (userId) {
    let detailsRequest = new XMLHttpRequest();
    detailsRequest.open("GET", "ws/users/" + userId, true);
    detailsRequest.responseType = "json";

    detailsRequest.onload = function () {
        let user = this.response;
        console.log(user);
    };

    detailsRequest.send();
};
*/


let listUsers = function () {
    let usersRequest = new XMLHttpRequest();

    usersRequest.open("GET", "ws/users", true);
    usersRequest.responseType = "json";

    usersRequest.onload = function () {
        let users = this.response;
        console.log(users);
        gerenerateLine(users);
    };

    usersRequest.send();
};

let createLine = function(user){
    let lineElement = document.createElement("tr");
    console.log(user.idutilisateur);

    lineElement.id = "user-" + user.idutilisateur;
    lineElement.appendChild(createCell(user.pseudo));
    lineElement.appendChild(createCell(user.mail));
    lineElement.appendChild(createCell(user.president));
    lineElement.appendChild(createCell(user.club));

    return lineElement;
};

let createCell = function(text){
    let cellElement;
    cellElement = document.createElement("td");
    cellElement.innerText = text;
    return cellElement;
};

let gerenerateLine = function(users){
    let table = document.getElementById("listUsers");

    for (const user of users){
        let newLine = createLine(user);
        table.appendChild(newLine);
    };

};



window.onload = function(){
    listUsers();
};