var btnGroupObj = document.querySelector("div.btn-group");
var categoryObj = btnGroupObj.querySelector("div.dropdown-menu");
console.log(categoryObj);

var inputGroupObj = document.querySelector("div.input-group");
var searchObj = inputGroupObj.querySelector("input[type=text]");

function categoryHandler(e) {
  if (e.target.id == "categoryTitle") {
    searchObj.setAttribute("placeholder", "제목으로 검색하기");
  } else {
    searchObj.setAttribute("placeholder", "작성자로 검색하기");
  }
  //   alert(e.target.id);
}

function init() {
  categoryObj.addEventListener("click", categoryHandler);
}

init();
