var skdDetail = document.querySelector("div#skdDetail");
console.log(skdDetail);
//일정 상세내역 
var shareObj = document.getElementById("skdDetailShare");
console.log(shareObj);
var shareValue = shareObj.querySelector("td.skdDetailInputData");
console.log(shareValue);
var typeObj = document.getElementById("skdDetailType");
var typeValue = typeObj.querySelector("td.skdDetailInputData");
console.log(typeValue);
var titleObj = document.getElementById("skdDetailTitle");
var titleValue = titleObj.querySelector("td.skdDetailInputData");
var StartTimeObj = document.getElementById("skdDetailStartTime");
var StartTimeValue =StartTimeObj.querySelector("td.skdDetailInputData");
console.log(StartTimeValue);
var EndTimeObj =document.getElementById("skdDetailEndTime");
var EndTimeValue = EndTimeObj.querySelector("td.skdDetailInputData");
console.log(EndTimeValue);
var ContentObj = document.getElementById("skdDetailContent");
var ContentValue =ContentObj.querySelector("td.skdDetailInputData");
console.log(ContentValue);


var share = "개인일정";
var title = "홍대미팅";
var type = "업무";
var startTime = "2021-07-01 13:00";
var endTime = "2021-07-02 14:00";
var content = "홍대 카페베네";

function resultSkdDetail(){
shareValue.innerHTML= share;
titleValue.innerHTML =title;
typeValue.innerHTML  =type;
StartTimeValue.innerHTML =startTime;
EndTimeValue.innerHTML =endTime;
ContentValue.innerHTML =content;

}

function init() {
    resultSkdDetail();
   
  }
  
 init();
  