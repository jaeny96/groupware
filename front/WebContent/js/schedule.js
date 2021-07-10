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
 
 
  //modal 만드는 함수
    //파라미터 : class="modal" 의 id
    function createModal(id) {

      var modal = document.getElementById(id);
      modal.classList.remove("hidden");//모달열기
      var closeBtn = modal.querySelector("button.cancel");

      var overlay = modal.querySelector(".modal_overlay");
      var deleteBtn = modal.querySelector("button.deleteBtn");
      var xBoxBtn = modal.querySelector("button.xBox");


      //함수
      var openModal = () => {
        modal.classList.remove("hidden");
      }
      var deleteSKD = () => {
        window.alert('일정을 삭제하시겠습니까?');
      }
      var closeModal = () => {
        modal.classList.add("hidden");
      }

      //클릭이벤트
      //오버레이 부분 클릭 닫기
      overlay.addEventListener('click', closeModal);
      //모달창 닫기 버튼
      if (xBoxBtn != null) {
        xBoxBtn.addEventListener('click', closeModal);
      }

      if (closeBtn != null) {
        closeBtn.addEventListener('click', closeModal);
      }

      //todo : 삭제 버튼  
      if (deleteBtn != null) {
        deleteBtn.addEventListener('click', deleteSKD);
      }
    }

