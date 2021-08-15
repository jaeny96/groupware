$(function () {
  var skdModifyModalObj = document.getElementById("skdDetail");
  var skdDeleteBtn = skdModifyModalObj.querySelector("button.deleteBtn");
  console.log(skdDeleteBtn);

  // var loginInfoIdObj = document.querySelector(
  //   "div.profileDropdown span.loginId"
  // );
  // var loginInfoNameObj = document.querySelector(
  //   "div.profileDropdown span.loginName"
  // );
  //todo일정 number 갖고오기

  //todo id가져오기
  //loginInfoNameObj.innerText;
  //loginInfoIdObj.innerText
  // var currentLoginEmp = "CEO";
  // var currentLoginId = "CEO001";
  var currentSkdNo = localStorage.getItem("skdNo");
  console.log(currentSkdNo);
  var backurlDeleteSkd = "/back/deleteschedule";

  //여기서부터..

  function skdDeleteClickHandler(e) {
    $.ajax({
      url: backurlDeleteSkd,
      method: "get",
      data: {
        skd_no: currentSkdNo, //todo채워넣어야..
      },
      success: function () {
        alert("일정이 삭제되었습니다!");
        $(function () {
          $("#scheduleMenu")
            .click(function () {
              this.click();
            })
            .click();
        });
      },
    });
    e.preventDefault();
  }

  skdDeleteBtn.addEventListener("click", skdDeleteClickHandler);
});
