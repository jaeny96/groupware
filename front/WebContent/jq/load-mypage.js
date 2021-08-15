$(function () {
  var cardBodyObj = document.querySelector("div.cardBody");
  //사원의 이름 객체
  var nameObj = document.querySelector("div#name");
  //사원의 이름값이 들어갈 객체
  var nameValue = nameObj.querySelector("h1>strong");
  //사원의 직급 객체
  var positionValue = nameObj.querySelector("div#position");
  //사원의 사번 객체
  var employeeIdObj = document.querySelector("div#employeeId");
  //사원의 사번값이 들어갈 위치
  var employeeIdValue = employeeIdObj.querySelector("div.flex-grow-1");
  //사원의 부서 객체
  var departmentObj = document.querySelector("div#department");
  //사원의 부서값이 들어갈 위치
  var departmentValue = departmentObj.querySelector("div.flex-grow-1");
  //사원의 직무 객체
  var jobObj = document.querySelector("div#job");
  //사원의 직무 값이 들어갈 위치
  var jobValue = jobObj.querySelector("div.flex-grow-1");
  //사원의 핸드폰 객체
  var phoneObj = document.querySelector("div#phoneNum");
  //사원의 핸드폰값이 들어갈 위치
  var phoneValue = phoneObj.querySelector("div.flex-grow-0");
  //사원의 핸드폰 번호 변경 버튼
  var modifyBtnObj = phoneObj.querySelector("a#modifyPhoneBtn");
  //사원의 이메일 객체
  var emailObj = document.querySelector("div#email");
  //사원의 이메일 값이 들어갈 위치
  var emailValue = emailObj.querySelector("div.flex-grow-1");
  //사원의 휴가정보가 들어갈 테이블 객체
  var myPageTableObj = document.querySelector("table.mypageLeave");
  //사원에게 부여된 휴가일 객체
  var grantDaysObj = myPageTableObj.querySelector("td.myGrantDays");
  //사원이 사용한 휴가일 객체
  var useDaysObj = myPageTableObj.querySelector("td.myUseDays");
  //사원의 남은 휴가일 객체
  var remainDaysObj = myPageTableObj.querySelector("td.myRemainDays");
  var targetObj = document.querySelectorAll("a.mdGroup");
  //핸드폰 번호 변경 form 객체
  var modifyPhModalObj = document.querySelector("form.modiPh");
  //기존 핸드폰 번호 객체
  var originPhoneNumObj = document.querySelector("div.originPhoneNum");
  //변경할 핸드폰 번호 input 객체
  var modifyPhoneNumObj = document.querySelector("input.modifyPhoneNum");
  //비밀번호 변경 form 객체
  var modifyPwdModalObj = document.querySelector("form.modiPwd");
  //변경할 비밀번호 input 객체
  var modifyPwdObj = document.querySelector("input#modifyPwd");
  //변경할 비밀번호 확인 inpur 객체 - 같은지 확인해야함
  var chkModifyPwdObj = document.querySelector("input#chkModifyPwd");

  //사원 이름 저장할 변수
  var empName;
  //사원 직급 저장할 변수
  var position;
  //사원 사번 저장할 변수
  var employeeId;
  //사원 부서 저장할 변수
  var department;
  //사원 직무 저장할 변수
  var job;
  //사원 핸드폰번호 저장할 변수
  var phone;
  //사원 이메일 저장할 변수
  var email;
  //사원 비밀번호 저장할 변수
  var password;
  //사원에게 부여된 휴가일 저장할 변수
  var myGrantDays;
  //사원에게 남은 휴가일 저장할 변수
  var myRemainDays;

  //사원 정보 대입
  function insertElement() {
    nameValue.innerHTML = empName;
    positionValue.innerHTML = position;
    employeeIdValue.innerHTML = employeeId;
    departmentValue.innerHTML = department;
    jobValue.innerHTML = job;
    phoneValue.innerHTML = phone;
    emailValue.innerHTML = email;
    grantDaysObj.innerHTML = myGrantDays + "일";
    useDaysObj.innerHTML = myGrantDays - myRemainDays + "일";
    remainDaysObj.innerHTML = myRemainDays + "일";

    originPhoneNumObj.innerHTML = phone;
  }

  //타겟 모달창 열기
  function openTargetModal(modalId, modalObj) {
    //타겟 모달 아이디
    var modal = document.querySelector("#" + modalObj + ">.modal");
    var overlay = modal.querySelector(".modal_overlay");
    var xBoxBtn = modal.querySelector("button.xBox");
    //타겟 모달창 열었다
    modal.classList.remove("hidden");

    var closeModal = () => {
      modal.classList.add("hidden");
    };
    //input에는 없는 overlay 클릭 -> close 기능
    overlay.addEventListener("click", closeModal);
    //모달창 닫기 버튼
    xBoxBtn.addEventListener("click", closeModal);
  }

  //모달창 open 핸들러
  function clickHandler(e) {
    var targetAId = e.target.id;
    //타겟 아이디 : 프로필 수정
    if (targetAId == "openProfile") {
      openTargetModal("#" + targetAId, "modalProfile");
    }
    //타겟아이디 : 비밀번호 수정
    if (targetAId == "openPwd") {
      openTargetModal("#" + targetAId, "modalPwd");
    }
    //타겟아이디 : 핸드폰번호 수정
    if (targetAId == "modifyPhoneBtn") {
      openTargetModal("#" + targetAId, "modalPhone");
      // openTargetModal("#modifyPhoneBtn", "modalPhone");
    }
  }

  for (var i = 0; i < targetObj.length; i++) {
    targetObj[i].addEventListener("click", clickHandler);
  }

  //mypage에서 사용할 backurl
  var backurlProfile = "/back/showmyprofile";
  var backurlChangePh = "/back/changemyphonenum";
  var backurlChangePwd = "/back/changemypwd";

  //로그인한 사원 정보 get
  $.ajax({
    url: backurlProfile,
    method: "get",
    success: function (responseData) {
      empName = responseData.employee.name;
      position = responseData.employee.position.position_title;
      employeeId = responseData.employee.employee_id;
      department = responseData.employee.department.department_title;
      job = responseData.employee.job.job_title;
      phone = responseData.employee.phone_number;
      email = responseData.employee.email;
      password = responseData.employee.password;
      myGrantDays = responseData.leave.grant_days;
      myRemainDays = responseData.leave.remain_days;

      insertElement();
    },
  });

  //핸드폰 번호 변경 시 submit 이벤트 핸들러
  function modiPhSubmitHandler(e) {
    $.ajax({
      url: backurlChangePh,
      method: "post",
      data: {
        modiPhone: modifyPhoneNumObj.value,
      },
      success: function () {
        alert("연락처가 변경되었습니다");
        //해당 페이지 재로딩
        $('#sidebar > div > ul > li > a[href="mypage.html"]').trigger("click");
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

  //비밀번호 변경 시 submit 이벤트 핸들러
  function modiPwdSubmitHandler(e) {
    $.ajax({
      url: backurlChangePwd,
      method: "post",
      data: {
        modiPwd: modifyPwdObj.value,
        chkModiPwd: chkModifyPwdObj.value,
      },
      success: function () {
        //모두 입력한 경우
        if (modifyPwdObj.value != "" && chkModifyPwdObj.value != "") {
          //비밀번호 변경 값과 확인 값이 같은 경우
          //+ 비밀번호 변경값,확인값이 기존 비밀번호값과 같은 경우
          if (
            password == modifyPwdObj.value &&
            password == chkModifyPwdObj.value
          ) {
            alert(
              "변경하려는 비밀번호가 기존 비밀번호와 같아 변경할 수 없습니다"
            );
          } else {
            if (modifyPwdObj.value == chkModifyPwdObj.value) {
              alert("비밀번호가 변경되었습니다");
              //해당 페이지 재로딩
              $('#sidebar > div > ul > li > a[href="mypage.html"]').trigger(
                "click"
              );
            } else {
              alert("비밀번호가 일치하지 않습니다");
            }
          }
        } else {
          //입력된 값이 하나라도 없을 경우
          alert("입력된 값이 없습니다.");
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

  //휴대폰번호 수정 모달 submit 이벤트 등록
  modifyPhModalObj.addEventListener("submit", modiPhSubmitHandler);
  //비밀번호 수정 모달 submit 이벤트 등록
  modifyPwdModalObj.addEventListener("submit", modiPwdSubmitHandler);
});
