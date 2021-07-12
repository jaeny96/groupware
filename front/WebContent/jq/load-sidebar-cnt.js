$(function () {
// 이클립스에서 받아오는 url
var backUrlApDocsList = "/back/sidebarcntall";

var allCntObject = document.getElementById("apAllLink");
var waitCntObject = document.getElementById("apWaitLink");
var okCntObject = document.getElementById("apOkLink");
var noCntObject = document.getElementById("apNoLink");

var allCnt = document.getElementById("apAllCntMark");
var waitCnt = document.getElementById("apWaitCntMark");
var okCnt = document.getElementById("apOkCntMark");
var noCnt = document.getElementById("apNoCntMark");
var cnt = new Array();

//개수 가지고 오기 함수
function createApCntElement() {
  if (allCntObject) {
    allCnt.innerHTML = cnt[0];
  }
  if (waitCntObject) {
    waitCnt.innerHTML = cnt[1];
  }
  if (okCntObject) {
    okCnt.innerHTML = cnt[2];
  }
  if (noCntObject) {
    noCnt.innerHTML = cnt[3];
  }
}



$.ajax({
  url: backUrlApDocsList,
  method: "get",
  data: {
    id: "DEV001",
  },
  success: function (responseData) {
    $(responseData).each(function (i, e) {
      cnt[i] = e;
    });
    createApCntElement();
  },
});
});