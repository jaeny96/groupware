var btnGroupObj = document.querySelector("div.btn-group");
var categoryObj = btnGroupObj.querySelector("div.dropdown-menu");
var tBodyObj = document.querySelector("tbody.bdTbody");

var bdTitle = ["안녕", "안녕2"];
var bdWriter = ["오지수", "정연주"];
var bdDate = ["2021-07-07", "2021-07-07"];

var pageGroup = [1, 2, 3, 4];

var inputGroupObj = document.querySelector("div.input-group");
var searchObj = inputGroupObj.querySelector("input[type=text]");

function createBdElement(i) {
  var tr = document.createElement("tr");
  var th = document.createElement("th");
  th.setAttribute("scope", "row");
  th.innerHTML = i + 1;
  var tdTitle = document.createElement("td");
  var tdWriter = document.createElement("td");
  tdWriter.innerHTML = bdWriter[i];
  var tdDate = document.createElement("td");
  tdDate.innerHTML = bdDate[i];
  var a = document.createElement("a");
  a.setAttribute("class", "sidebar-link-js");
  a.setAttribute("style", "color: black");
  a.setAttribute("href", "board-detail.html");
  a.innerText = bdTitle[i];

  tdTitle.appendChild(a);
  tr.appendChild(th);
  tr.appendChild(tdTitle);
  tr.appendChild(tdWriter);
  tr.appendChild(tdDate);
  tBodyObj.appendChild(tr);
}

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
  for (var i = 0; i < bdTitle.length; i++) {
    createBdElement(i);
  }
}

init();
