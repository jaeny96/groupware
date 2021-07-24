$(function() {
	var loginInfoValue = localStorage.getItem("loginInfo");
	console.log(loginInfoValue);

	var formObj = document.querySelector("form.form");
	var idObj = formObj.querySelector("input[name=id]");
	var pwdObj = formObj.querySelector("input[name=pwd]");
	console.log(formObj);
	console.log(idObj);
	console.log(pwdObj);

	if (loginInfoValue != null && loginInfoValue != "") {
		idObj.value = loginInfoValue;
	}
	formObj.addEventListener("submit", function(event) {
		localStorage.removeItem("loginInfo");

		var chkboxObj = formObj.querySelector(
			"p.fieldset input[type=checkbox]"
		);
		console.log(chkboxObj);
		if (chkboxObj.checked) {
			localStorage.setItem("loginInfo", idObj.value);
		}

		var backurl = "/back/login";
		$.ajax({
			url: backurl,
			method: "post",
			data: {
				id: $("form.form input[name=id]").val(),
				pwd: $("form.form input[name=pwd]").val(),
			},
			success: function(responseObj) {
				console.log(responseObj.status);
				if (responseObj.status == 1) {
					//logined();
					alert("로그인에 성공하였습니다!");
					location.href="./main.html"
				} else {
					alert(responseObj.msg);
				}
			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}

		});

		event.preventDefault();
	});
});
