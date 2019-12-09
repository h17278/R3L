
function navPosition() {
    if (window.scrollY > 115) {
        console.log("Scroll limit");
        let nav = document.getElementsByTagName("nav")[0];
        let navBarList = nav.getElementsByTagName("div");
        if (navBarList.length < 3) {
            let newNavBar = document.getElementById("NavBar").cloneNode(true);
            nav.appendChild(newNavBar);
            document.getElementById("NavBar").style.position = 'fixed';
            document.getElementById("NavBar").style.display = '';
            document.getElementById("NavBar").style.top = '0';
            document.getElementById("NavBar").style.left = '0';
            document.getElementById("NavBar").style.zIndex = '1';
        }
    } else {
        let nav = document.getElementsByTagName("nav")[0];
        let navBarList = nav.getElementsByTagName("div");
        let i = navBarList.length - 1;
        if (navBarList.length > 2) {
            navBarList[i].remove();
        }
        document.getElementById("NavBar").style.position = '';
        document.getElementById("NavBar").style.display = 'block';
    }
}