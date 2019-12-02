
function searchEvent() {
    let articleList = [];
    for (let j=0; j<document.getElementsByTagName('article').length; j++) {
        articleList.push(document.getElementById('rank-' + (j+1).toString()));
    }
    console.log("articleList :", articleList);

    let result = [];
    let side="left";
    let input = document.getElementById("searchEventTest").value.toLowerCase();
    console.log("searchEventTest="+input);
    for (let i=0; i<articleList.length; i++) {
        let article = articleList[i];
        let h1 = document.querySelector("article#rank-"+(i+1)+" h1").innerText.toLowerCase();
        let h2 = document.querySelector("article#rank-"+(i+1)+" h2").innerText.toLowerCase();
        let h4 = document.querySelector("article#rank-"+(i+1)+" h4").innerText.toLowerCase();
        let h3 = document.querySelector("article#rank-"+(i+1)+" h3.bureau").innerText.toLowerCase();
        console.log("inner text h1 " +h1);
        console.log("inner text h2 " +h2);
        if (h1.includes(input) || h2.includes(input) || h4.includes(input) || h3.includes(input)) {
            console.log("libelle="+article.className+" | side ="+side);
            result.push(article);
            document.getElementById(side).appendChild(article);
            article.style.display = "block";
            console.log(side.localeCompare("left"));
            if(!side.localeCompare("left")){
                side="right";
            }else{
                side = "left";
            }
        } else {
            article.style.display = "none";
        }
    }
    console.log(result);
}
