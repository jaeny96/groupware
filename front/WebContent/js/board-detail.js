var bdDetailObj = document.querySelector("div.bdDetailTitle");
var detailTitleObj = bdDetailObj.querySelector("h2");
var detailInfoObj = bdDetailObj.querySelector("div.flex-grow-1");
var infoWriterObj = detailInfoObj.querySelector("small.bdDetailWriter");
var infoDateObj = detailInfoObj.querySelector("small.bdDetailDate");
var detailContentObj = document.querySelector("div.bdDetailContent");
var bdBtnGroupObj = document.querySelector("div.bdBtnGroup");
var cmCardBodyObj = document.querySelector("div.cmCardBody");
var cmWriterObj = document.querySelector("h5.currentLoginId");

var bdDetailTitle = "안녕";
var bdDetailWriter = "김정은";
var bdDetailWriterId = "MSD002";
var bdDetailDate = "2021-07-07 11:03";
var bdDetailContent = "배고파";
var currentLoginEmp = "오지수";
var currentLoginId = "MSD001";
var cmContent = ["안녕하세요", "넵"];
var cmWriter = ["오지수", "김정은"];
var cmWrtierId = ["MSD001", "MSD002"];
var cmDate = ["2021-07-07 11:30", "2021-07-07 12:00"];

function createCmElement(i) {
  var cmBodyDiv = document.createElement("div");
  cmBodyDiv.setAttribute("class", "d-flex align-items-start cmBody mb-2");
  var profileImg = document.createElement("img");
  profileImg.setAttribute("src", "img/avatars/avatar-5.jpg");
  profileImg.setAttribute("width", "36");
  profileImg.setAttribute("height", "36");
  profileImg.setAttribute("class", "rounded-circle mr-2 mb-2");
  profileImg.setAttribute("alt", cmWriter[i]);

  var cmContentDiv = document.createElement("div");
  cmContentDiv.setAttribute("class", "flex-grow-1");

  var cmDeleteSmall = document.createElement("small");
  cmDeleteSmall.setAttribute("class", "float-right text-navy");
  var cmDeleteA = document.createElement("a");
  cmDeleteA.setAttribute("class", "cmDeleteBtn");
  cmDeleteA.setAttribute("href", "board-detail.html");
  cmDeleteA.innerHTML = "X";
  cmDeleteSmall.appendChild(cmDeleteA);

  var cmDateSmall = document.createElement("small");
  cmDateSmall.setAttribute("class", "float-right text-navy mr-3 cmDate");
  cmDateSmall.innerHTML = cmDate[i];
  var cmContentSpan = document.createElement("span");
  cmContentSpan.setAttribute("class", "cmContnet");
  cmContentSpan.innerHTML = cmContent[i] + "<br />";
  var cmWriterSmall = document.createElement("small");
  cmWriterSmall.setAttribute("class", "text-muted cmWriter");
  cmWriterSmall.innerHTML = cmWriter[i] + " " + cmWrtierId[i] + "<br />";

  cmContentDiv.appendChild(cmDeleteSmall);
  cmContentDiv.appendChild(cmDateSmall);
  cmContentDiv.appendChild(cmContentSpan);
  cmContentDiv.appendChild(cmWriterSmall);

  cmBodyDiv.append(profileImg);
  cmBodyDiv.appendChild(cmContentDiv);

  cmCardBodyObj.appendChild(cmBodyDiv);

  if (currentLoginId != cmWrtierId[i]) {
    cmDeleteSmall.setAttribute("style", "display:none");
  } else {
    cmDeleteSmall.removeAttribute("style");
  }
}

function createBdDatailElement() {
  detailTitleObj.innerHTML = bdDetailTitle;
  infoWriterObj.innerHTML = bdDetailWriter;
  infoDateObj.innerHTML = bdDetailDate;
  detailContentObj.innerHTML = bdDetailContent;

  if (currentLoginId != bdDetailWriterId) {
    bdBtnGroupObj.setAttribute("style", "display:none");
  } else {
    bdBtnGroupObj.removeAttribute("style");
  }
  cmWriterObj.innerHTML = currentLoginEmp;
}

function init() {
  createBdDatailElement();
  for (var i = 0; i < cmContent.length; i++) {
    createCmElement(i);
  }
}

init();
