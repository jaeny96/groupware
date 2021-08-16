$(function () {

  //a링크 구성요소 받아오는 변수
  var allCntObject = document.getElementById("apAllLink");
  var waitCntObject = document.getElementById("apWaitLink");
  var okCntObject = document.getElementById("apOkLink");
  var noCntObject = document.getElementById("apNoLink");
 //div 구성요소 받아오는 변수 - 이곳에 마크 값 넣을 예정 
  var allCnt = document.getElementById("apAllCntMark");
  var waitCnt = document.getElementById("apWaitCntMark");
  var okCnt = document.getElementById("apOkCntMark");
  var noCnt = document.getElementById("apNoCntMark");

  //배열로 받아오기 위한 변수 
  var cnt = new Array();

  //사이드바의 마크에 받아온 데이터로 채우는 함수 
  function createApCntElement() {
    if (allCntObject) {//전체 - 0번 index
      allCnt.innerHTML = cnt[0];
    }
    if (waitCntObject) {//대기 - 1번 index
      waitCnt.innerHTML = cnt[1];
    }
    if (okCntObject) {//승인 - 2번 index
      okCnt.innerHTML = cnt[2];
    }
    if (noCntObject) {//반려 - 3번 index
      noCnt.innerHTML = cnt[3];
    }
  }

  //사이드바 관련 ajax 
  $.ajax({
    url: "/back/sidebarcntall",
    method: "get",
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        cnt[i] = e;
      });
      createApCntElement();
    },
  });
});
