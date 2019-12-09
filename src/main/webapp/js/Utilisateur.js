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
        lineElement.appendChild(createCell("Président"));
    } else {
        lineElement.appendChild(createCell("Membre"));
    }

    let nameClub = document.getElementById("name-" + user.club).innerText;
    console.log(nameClub);
    lineElement.appendChild(createCell(nameClub));
    //lineElement.appendChild(createCell(user.club));

    let updateButton = document.createElement("button");
    updateButton.title = "Changer les autorisations de cette utilisateur";
    updateButton.innerText = "Changer autorisation";
    updateButton.onclick = function () {
        updateUser(user);
    };
    lineElement.appendChild(updateButton);

    let deleteButton = document.createElement("button");
    deleteButton.title = "Supprimer "+user.pseudo;
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

let deleteUser = function (user) {
    let userName = document.getElementById("userName").innerText;
    console.log("pseudo de l utilisateur : " + userName);
    console.log("pseudo utilisateur à supprimer : " + user.pseudo);
    if(userName !== user.pseudo) {
        if (confirm("Etes-vous sûr de supprimer " + user.pseudo + " ?")) {
            let deleteRequest = new XMLHttpRequest();
            deleteRequest.open("DELETE", "ws/users/" + user.idutilisateur, true);

            deleteRequest.onload = function () {
                listUsers();
            };

            deleteRequest.send();
        }
    }
    else{
        alert("Vous ne pouvez pas supprimer vous supprimer");
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

let listClubs = function () {
    let usersRequest = new XMLHttpRequest();

    usersRequest.open("GET", "ws/clubs", true);
    usersRequest.responseType = "json";

    usersRequest.onload = function () {
        let clubs = this.response;
        console.log(clubs);
        refreshClubTable(clubs);
    };

    usersRequest.send();
};
let createClubLine = function(club){
    let lineElement = document.createElement("tr");

    lineElement.id = "club-" + club.id;

    lineElement.appendChild(createCell(club.id));
    cellName = createCell(club.name);
    cellName.id = "name-" + club.id ;
    lineElement.appendChild(cellName);

    return lineElement;
};

let refreshClubTable = function (clubs) {
    let tableElement = document.getElementById("listClubsBody");
    var newTableElement = tableElement.cloneNode(false);
    for (const club of clubs) {
        newTableElement.appendChild(createClubLine(club));
    }

    tableElement.parentNode.replaceChild(newTableElement, tableElement);
    listUsers();
};


window.onload = function(){
    listClubs();
};