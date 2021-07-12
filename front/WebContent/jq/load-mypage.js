$(function() {
	var cardBodyObj = document.querySelector("div.cardBody");
	var nameObj = document.querySelector("div#name");
	var nameValue = nameObj.querySelector("h1>strong");
	var positionValue = nameObj.querySelector("div#position");
	var employeeIdObj = document.querySelector("div#employeeId");
	var employeeIdValue = employeeIdObj.querySelector("div.flex-grow-1");
	var departmentObj = document.querySelector("div#department");
	var departmentValue = departmentObj.querySelector("div.flex-grow-1");
	var jobObj = document.querySelector("div#job");
	var jobValue = jobObj.querySelector("div.flex-grow-1");
	var phoneObj = document.querySelector("div#phoneNum");
	var phoneValue = phoneObj.querySelector("div.flex-grow-0");
	var modifyBtnObj = phoneObj.querySelector("a#modifyPhoneBtn");
	var emailObj = document.querySelector("div#email");
	var emailValue = emailObj.querySelector("div.flex-grow-1");
	var myPageTableObj = document.querySelector("table.mypageLeave");
	var grantDaysObj = myPageTableObj.querySelector("td.myGrantDays");
	var useDaysObj = myPageTableObj.querySelector("td.myUseDays");
	var remainDaysObj = myPageTableObj.querySelector("td.myRemainDays");
	var targetObj = document.querySelectorAll("a.mdGroup");
	var modifyPhModalObj = document.querySelector("form.modiPh");
	var originPhoneNumObj = document.querySelector("div.originPhoneNum");
	var modifyPhoneNumObj = document.querySelector("input.modifyPhoneNum");
	var modifyPwdModalObj = document.querySelector("form.modiPwd");
	var modifyPwdObj = document.querySelector("input#modifyPwd");
	var chkModifyPwdObj = document.querySelector("input#chkModifyPwd");

	var empName;
	var position;
	var employeeId;
	var department;
	var job;
	var phone;
	var email;
	var password;
	var myGrantDays;
	var myRemainDays;

	function selectElement() {
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

	function openTargetModal(modalId, modalObj) {
		var openBtn = document.querySelector(modalId);
		var modal = document.querySelector("#" + modalObj + ">.modal");
		var overlay = modal.querySelector(".modal_overlay");
		var deleteBtn = modal.querySelector("button.deleteBtn");
		var xBoxBtn = modal.querySelector("button.xBox");
		var openModal = () => {
			modal.classList.remove("hidden");
		};
		var closeModal = () => {
			modal.classList.add("hidden");
		};
		//input에는 없는 overlay 클릭 -> close 기능
		overlay.addEventListener("click", closeModal);
		//모달창 열기 버튼
		openBtn.addEventListener("click", openModal);
		//모달창 닫기 버튼
		xBoxBtn.addEventListener("click", closeModal);
	}

	function clickHandler(e) {
		var targetAId = e.target.id;
		if (targetAId == "openProfile") {
			openTargetModal("#" + targetAId, "modalProfile");
		}
		if (targetAId == "openPwd") {
			openTargetModal("#" + targetAId, "modalPwd");
		}
		if (targetAId == "modifyPhoneBtn") {
			openTargetModal("#" + targetAId, "modalPhone");
		}
	}

	for (var i = 0; i < targetObj.length; i++) {
		targetObj[i].addEventListener("click", clickHandler);
	}


	var backurlProfile = '/back/showmyprofile';
	var backurlChangePh = '/back/changemyphonenum';
	var backurlChangePwd = '/back/changemypwd';

	$.ajax({
		url: backurlProfile,
		method: 'get',
		success: function(responseData) {
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

			selectElement();
		},
	});
	function modiPhSubmitHandler(e) {
		$.ajax({
			url: backurlChangePh,
			method: "post",
			data: {
				modiPhone: modifyPhoneNumObj.value,
			},
			success: function() {
				alert("연락처가 변경되었습니다");
				$(
					'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="mypage.html"]'
				).trigger('click');

			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});

		e.preventDefault();
	}
	function modiPwdSubmitHandler(e) {
		$.ajax({
			url: backurlChangePwd,
			method: "post",
			data: {
				modiPwd: modifyPwdObj.value,
				chkModiPwd: chkModifyPwdObj.value
			},
			success: function() {
				if (password == modifyPwdObj.value && password == chkModifyPwdObj.value) {
					alert("변경하려는 비밀번호가 기존 비밀번호와 같아 변경할 수 없습니다");
				} else {
					if (modifyPwdObj.value == chkModifyPwdObj.value) {
						alert("비밀번호가 변경되었습니다");
						$(
							'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="mypage.html"]'
						).trigger('click');
					} else {
						alert("비밀번호가 일치하지 않습니다");

					}
				}

			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});

		e.preventDefault();
	}

	modifyPhModalObj.addEventListener("submit", modiPhSubmitHandler);
	modifyPwdModalObj.addEventListener("submit", modiPwdSubmitHandler);

});