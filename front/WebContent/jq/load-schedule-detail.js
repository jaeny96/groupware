//수정
$(function () {
  var backSkdModify = "/back/modifyschedule";

  var modifySkdFormObj = $("#modifySkdContent");
  var modifySkdSubmitBtn = $("button.modifySkdSubmit");
  var skdUpdateTypeObj = $("#skdUpdateTypeSelect"); //problem

  var skdUpdateTypeValue = "업무";
  skdUpdateTypeObj.change(function () {
    console.log(this.value);
    skdUpdateTypeValue = this.value + "";
  });

  var skdUpdateTitle = $("#update_title");
  var skdUpdateContent = $("#input_content_update"); //내용
  var skdUpdateStartDate = $("#start_date_update"); //
  var skdUpdateStartTime = $("#start_time_update"); //
  var skdUpdateEndDate = $("#end_date_update"); //종료날짜
  var skdUpdateEndTime = $("#end_time_update"); //종료시간
  var skdUpdateShare2 = $('input[name="radio-2"]'); //problem
  var currentSkdNo = localStorage.getItem("skdNo");

  var test = "p";
  skdUpdateShare2.change(function () {
    console.log(this.value);
    test = this.value;
    console.log(test);
  });

  $("#skdModifyBtn").click(function () {
    var UpdatePreTitleValue = localStorage.getItem("title");
    // var UdpatePreTypeValue=localStorage.getItem("type");
    //  console.log(UdpatePreTypeValue);
    var UpdatePreStartDate = localStorage.getItem("startDate");

    var UpdatePreStartTime = localStorage.getItem("startTime");

    var skdOriginEndDate = localStorage.getItem("endDate");
    var skdOriginEndTime = localStorage.getItem("endTime");
    var UdpatePreContentValue = localStorage.getItem("content");

    console.log(UpdatePreTitleValue);
    console.log(UpdatePreStartDate);
    console.log(UpdatePreStartTime);
    console.log(skdOriginEndDate);
    console.log(skdOriginEndTime);
    console.log(UdpatePreContentValue);

    skdUpdateTitle.attr("value", UpdatePreTitleValue);

    // skdUpdateContent.innerHTML=UdpatePreContentValue;
    skdUpdateStartDate.val(UpdatePreStartDate);

    skdUpdateStartTime.val(UpdatePreStartTime);
    skdUpdateEndDate.val(skdOriginEndDate);
    skdUpdateEndTime.val(skdOriginEndTime);
    skdUpdateContent.val(UdpatePreContentValue);
    console.log(skdUpdateTitle);
    // skdUpdateType.val(UdpatePreTypeValue);
  });
  function modifySkdSubmitHandler(e) {
    // console.log(skdUpdateShare);

    if (test == "p") {
      $.ajax({
        url: backSkdModify,
        method: "post",
        data: {
          title: skdUpdateTitle.val(),
          content: skdUpdateContent.val(),
          start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
          end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
          allDay: false,
          backgroundColor: "#ffc107",
          calendarType: skdUpdateTypeValue,
          teamOrPersonal: test,
          skd_no: currentSkdNo,
        },
        success: function () {
          window.alert("일정이 변경되었습니다");
          $("#scheduleMenu").click();
          location.reload();
          // $(function () {
          //   $("#scheduleMenu")
          //     .click(function () {
          //       this.click();
          //     })
          //     .click();
          // });
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
    } else if (test == "t") {
      $.ajax({
        url: backSkdModify,
        method: "post",
        data: {
          title: skdUpdateTitle.val(),
          content: skdUpdateContent.val(),
          start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
          end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
          allDay: false,
          backgroundColor: "#28a745",
          calendarType: skdUpdateTypeValue,
          teamOrPersonal: test,
          skd_no: currentSkdNo,
        },
        success: function () {
          window.alert("일정이 변경되었습니다");
          $("#scheduleMenu").click();
          location.reload();
          // $(function () {
          //   $("#scheduleMenu")
          //     .click(function () {
          //       this.click();
          //     })
          //     .click();
          // });
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
  }

  modifySkdSubmitBtn.click(modifySkdSubmitHandler);

  //삭제

  var skdModifyModalObj = document.getElementById("skdDetail");
  var skdDeleteBtn = skdModifyModalObj.querySelector("button.deleteBtn");
  console.log(skdDeleteBtn);

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
        $("#scheduleMenu").click();
        location.reload();
        // $(function () {
        //   $("#scheduleMenu")
        //     .click(function () {
        //       this.click();
        //     })
        //     .click();
        // });
      },
    });
    e.preventDefault();
  }

  skdDeleteBtn.addEventListener("click", skdDeleteClickHandler);
});
