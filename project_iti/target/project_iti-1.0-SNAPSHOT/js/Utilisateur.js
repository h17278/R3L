var listUtilisateurs  = function() {
    let utilisateursRequest = new XMLHttpRequest();
    utilisateursRequest.open("GET", "user/list", true);
    utilisateursRequest.responseType = "json";

    utilisateursRequest.onload = function () {
        let utilisateurs = this.response;
        for(const utilisateur of utilisateurs){

        }

    }

};

var adduserToUserService = function (utilisateur) {
    let userLiElement = document.createElement("li");
    userLiElement.setAttribute("class", "list-group-item d-flex justify-content-between");
    userLiElement.id="fighter"+fighter.id;

    userLiElement.appendChild(createSummary(fighter));
    userLiElement.appendChild(createActionsElement(fighter));

    document.getElementById("halloffame").appendChild(fighterLiElement);
};