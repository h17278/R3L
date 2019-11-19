function maFonctionTestResume() {
    var x =null;
    x = document.getElementById("resume").value;

    if (x=="")
    {
        alert("Le champ de saisi n'est pas rempli");
    }
    else
    {
        if (isNaN(x) || x < 0 || x > 100)
        {
            alert("Le nombre entrée n'est pas correct, veuillez saisir de nouveau description avec moins de 100 caractères ")
        }
    }
}



function maFonctionTestDetail() {
    var x =null;
    x = document.getElementById("detail").value;

    if (x=="")
    {
        alert("Le champ de saisi n'est pas rempli");
    }
    else
    {
        if (isNaN(x) || x < 0 || x > 10000)
        {
            alert("Le nombre entrée n'est pas correct, veuillez saisir de nouveau description avec moins de 100 caractères ")
        }
    }
}