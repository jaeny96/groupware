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

var empName = "임창균";
var position = "책임";
var employeeId = "DEV001";
var department = "기획개발실";
var job = "자바개발";
var phone = "010-1186-6485";
var email = "dev001@angkeum.com";

function selectElement() {
  nameValue.innerHTML = empName;
  positionValue.innerHTML = position;
  employeeIdValue.innerHTML = employeeId;
  departmentValue.innerHTML = department;
  jobValue.innerHTML = job;
  phoneValue.innerHTML = phone;
  emailValue.innerHTML = email;
}

function init() {
//  selectElement();

}

init();
