window.onscroll = function() {scrollFunc()};

const header = document.getElementById("mainHeader");
const sticky = header.offsetTop;

function scrollFunc() {
    if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
    }
}