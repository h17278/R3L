function searchEvent() {
    let articleList = [];
    for (let j = 0; j < document.getElementsByTagName('article').length; j++) {
        articleList.push(document.getElementById('rank-' + (j + 1).toString()));
    }
    console.log("articleList :", articleList);

    let result = [];
    let side = "left";
    let input = document.getElementById("searchEvent").value.toLowerCase();
    console.log("searchEvent=" + input);
    for (let i = 0; i < articleList.length; i++) {
        let article = articleList[i];
        let h1 = document.querySelector("article#rank-" + (i + 1) + " h1").innerText.toLowerCase();
        let h2 = document.querySelector("article#rank-" + (i + 1) + " h2").innerText.toLowerCase();
        let h4 = document.querySelector("article#rank-" + (i + 1) + " h4").innerText.toLowerCase();
        let h3 = document.querySelector("article#rank-" + (i + 1) + " h3.bureau").innerText.toLowerCase();
        console.log("inner text h1 " + h1);
        console.log("inner text h2 " + h2);
        if (h1.includes(input) || h2.includes(input) || h4.includes(input) || h3.includes(input)) {
            console.log("libelle=" + article.className + " | side =" + side);
            result.push(article);
            document.getElementById(side).appendChild(article);
            article.style.display = "block";
            console.log(side.localeCompare("left"));
            if (!side.localeCompare("left")) {
                side = "right";
            } else {
                side = "left";
            }
        } else {
            article.style.display = "none";
        }
    }
    console.log(result);
}


function searchClub() {
    let articleList = [];
    for (let j = 0; j < document.getElementsByTagName('article').length; j++) {
        articleList.push(document.getElementById('rank-' + (j + 1).toString()));
    }
    console.log("articleList :", articleList);

    let result = [];
    let side = "left";
    let input = document.getElementById("searchEvent").value.toLowerCase();
    console.log("searchEvent=" + input);
    for (let i = 0; i < articleList.length; i++) {
        let article = articleList[i];
        let tdList = [];
        let h1 = document.querySelector("article#rank-" + (i + 1) + " h1").innerText.toLowerCase();
        console.log("inner text h1 " + h1);

        tdList = articleList[i].getElementsByTagName('td');
        let membersList = [];

        for (let j = 0; j < tdList.length; j++) {
            membersList.push(tdList[j].innerText.toLowerCase());
        }
        console.log("list of members:" + membersList);

        document.getElementById(side).appendChild(article);
        article.style.display = "none";
        if (h1.includes(input)) {
            console.log("libelle=" + article.className + " | side =" + side);
            result.push(article);
            document.getElementById(side).appendChild(article);
            article.style.display = "block";
            console.log(side.localeCompare("left"));
            if (!side.localeCompare("left")) {
                side = "right";
            } else {
                side = "left";
            }
        } else {
            for (let k = 0; k < membersList.length; k++) {
                if (membersList[k].includes(input)) {
                    console.log("libelle=" + article.className + " | side =" + side);
                    result.push(article);
                    document.getElementById(side).appendChild(article);
                    article.style.display = "block";
                    console.log(side.localeCompare("left"));
                    if (!side.localeCompare("left")) {
                        side = "right";
                    } else {
                        side = "left";
                    }
                }
            }
        }

    }
    console.log(result);
}
