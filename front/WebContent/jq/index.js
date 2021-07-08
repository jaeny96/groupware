$(function () {
  var loginInfoValue = localStorage.getItem("loginInfo");

  var idObj = document.querySelector("div.login>form.form input[name=id]");
  if (loginInfoValue != null && loginInfoValue != "") {
    idObj.value = loginInfoValue;
  }
  var formObj = document.querySelector("form.form");

  function submitHandler() {
    localStorage.removeItem("loginInfo");

    var chkboxObj = document.querySelector(
      "div.login>form.form p.fieldset input[type=checkbox]"
    );

    if (chkboxObj.checked) {
      localStorage.setItem("loginInfo", idObj.value);
    }

    var backurl = "/back/login";
    $.ajax({
      url: backurl,
      method: "post",
      data: {
        id: $("div.login>form.form input[name=id]").val(),
        pwd: $("div.login>form.form input[name=pwd]").val(),
      },
      success: function (responseObj) {
        if (responseObj.staus == 1) {
        } else {
          alert(responseObj.msg);
        }
      },
      error: function (xhr) {
        alert(xhr.staus);
      },
    });
    event.preventDefault();
  }

  formObj.addEventListener("submit", submitHandler);
});
