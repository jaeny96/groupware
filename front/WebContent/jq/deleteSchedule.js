$(function () {
  var skdModifyModalObj = document.getElementById("skdDetail");
  var skdDeleteBtn = skdModifyModalObj.querySelector("button.deleteBtn");
  console.log(skdDeleteBtn);

  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  var loginInfoNameObj = document.querySelector(
    "div.profileDropdown span.loginName"
  );
  //todo일정 number 갖고오기

  //todo id가져오기
  //loginInfoNameObj.innerText;
  //loginInfoIdObj.innerText
  var currentLoginEmp = "CEO";
  var currentLoginId = "CEO001";
  var currentSkdNo = 2;
  var backurlDeleteSkd = "/back/deleteschedule";

  //여기서부터..

  function skdDeleteClickHandler(e) {
    console.log(e.target.id + "//" + currentLoginId);
    $.ajax({
      url: backurlDeleteSkd,
      method: "get",
      data: {
        removeSkdno: currentSkdNo, //todo채워넣어야..
        removeSkdId: currentLoginId,
      },
      success: function () {
        alert("일정이 삭제되었습니다!");
        $(
          " #sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(3) > a"
        ).trigger("click");
      },
    });
    e.preventDefault();
  }

  skdDeleteBtn.addEventListener("click", skdDeleteClickHandler);
});
