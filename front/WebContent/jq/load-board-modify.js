$(function () {
  //기존의 게시글 제목값
  var originBdTitleInModi = localStorage.getItem("bdTitle");
  //기존의 게시글 내용값
  var originBdContentInModi = localStorage.getItem("bdContent");
  //게시글 번호값
  var bdTargetNoInModi = localStorage.getItem("bdNumber");

  //게시글 수정 form 객체
  var modifyBdFormObj = document.querySelector("form#modifyBdForm");
  //게시글 제목 수정 input 객체
  var titleObjInModi = document.querySelector("input[name=title]");
  //게시글 내용 수정 textarea 객체 - summernote
  var contentObjInModi = document.querySelector("div.note-editable");
  //게시글 내용 textarea placeholder 객체
  var contentPlaceHolderObj = document.querySelector("div.note-placeholder");

  //게시글 내용이 있다면, placeholder 없애기
  if (
    originBdContentInModi.innerHTML != "" ||
    originBdContentInModi.innerHTML != null
  ) {
    contentPlaceHolderObj.innerHTML = "";
  }

  //게시글 제목 수정 input 객체에 기존 게시글 제목값 대입
  titleObjInModi.value = originBdTitleInModi;
  //게시글 내용 수정 input 객체에 기존 게시글 내용값 대입
  contentObjInModi.innerHTML = originBdContentInModi;

  //게시글 수정 시 사용할 backurl
  var backurlModiBdDetail = "/back/changeboarddetail";

  //게시글 수정 form submit 이벤트 핸들러
  function modifyBdSubmitHandler(e) {
    $.ajax({
      url: backurlModiBdDetail,
      method: "post",
      data: {
        modiBdTargetNo: bdTargetNoInModi,
        modiBdTitle: titleObjInModi.value,
        modiBdContent: contentObjInModi.innerText,
      },
      success: function () {
        //제목이 입력되지 않으면 수정 x
        if (titleObjInModi.value == "" || titleObjInModi.value == null) {
          alert("제목이 입력되지 않았습니다");
        } else {
          alert("게시글이 변경되었습니다");
          //게시글 수정 후 재로딩
          $(
            '#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li > a[href="board-detail.html"]'
          ).trigger("click");
        }
      },
      error: function (request, status, error) {
        alert(
          "code:" +
            request.status +
            "\n" +
            "message:" +
            request.responseText +
            "\n" +
            "error:" +
            error
        );
      },
    });
    e.preventDefault();
  }

  //form 객체 submit 이벤트 등록
  modifyBdFormObj.addEventListener("submit", modifyBdSubmitHandler);
});
