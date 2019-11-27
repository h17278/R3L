
let listParams = {
    idutilisateur: 1,
    pseudo: undefined,
    motdepasse: undefined,
    mail: undefined,
    president: undefined,
    idclub: undefined,
};

let listUsers = function () {
    let usersRequest = new XMLHttpRequest();
    let url = "ws/users" + listParams.idutilisateur;

    usersRequest.open("GET", url, true);
    usersRequest.responseType = "json";

    usersRequest.onload = function () {
        let users = this.response;
        refreshTable(users);
    };

    usersRequest.send();
};
