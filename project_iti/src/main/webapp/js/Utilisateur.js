let listUsers = function () {
    let usersRequest = new XMLHttpRequest();

    usersRequest.open("GET", "ws/users", true);
    usersRequest.responseType = "json";

    usersRequest.onload = function () {
        let users = this.response;
        refreshTable(users);
    };

    usersRequest.send();
};

let createLine = function(user){
    let lineElement = document.createElement("tr");

    lineElement.id = "user-" + user.idutilisateur;
    lineElement.appendChild(createCell(user.pseudo));
    lineElement.appendChild(createCell(user.mail));
    if(user.president === true) {
        lineElement.appendChild(createCell("président"));
    } else {
        lineElement.appendChild(createCell("Membre"));
    }
    lineElement.appendChild(createCell(user.club));

    let updateButton = document.createElement("button");
    updateButton.title = "Update this user";
    updateButton.innerText = "Changer autorisation";
    updateButton.onclick = function () {
        updateUser(user);
    };
    lineElement.appendChild(updateButton);

    let deleteButton = document.createElement("button");
    deleteButton.title = "Delete this user";
    deleteButton.innerText = "Supprimer";
    deleteButton.onclick = function () {
        deleteUser(user);
    };
    lineElement.appendChild(deleteButton);

    return lineElement;
};

let refreshTable = function (users) {
    let tableElement = document.getElementById("listUsersBody");
    var newTableElement = tableElement.cloneNode(false);
    for (const user of users) {
        newTableElement.appendChild(createLine(user));
    }
    tableElement.parentNode.replaceChild(newTableElement, tableElement);
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
    }

};

let deleteUser = function (user) {
    if (confirm("Are you sure you want to delete " + user.pseudo + " ?")) {
        let deleteRequest = new XMLHttpRequest();
        deleteRequest.open("DELETE", "ws/users/" + user.idutilisateur, true);

        deleteRequest.onload = function () {
            listUsers();
        };

        deleteRequest.send();
    }
};

let updateUser = function (user) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("PATCH", "ws/users/" + user.idutilisateur, true);

    saveRequest.onload = function () {
        listUsers();
    };
    saveRequest.send();
};


window.onload = function(){
    listUsers();
};