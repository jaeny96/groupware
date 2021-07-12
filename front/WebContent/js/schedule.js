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


var skdShareDetail = "팀일정";
var skdTitleDetail = "홍대미팅";
var skdTypeDetail = "업무";
var skdStartDateDetail = "2021-07-01";
var skdStartTimeDetail = "07:00";
var skdStartEndDateDetail ="2021-07-02";
var skdStartEndTimeDetail = "08:00";
var skdContentDetail = "홍대 카페베네";

localStorage.setItem("skd_share", skdShareDetail);
localStorage.setItem("skd_title", skdTitleDetail);
localStorage.setItem("skd_type", skdTypeDetail);
localStorage.setItem("skd_start_date", skdStartDateDetail);
localStorage.setItem("skd_start_time", skdStartTimeDetail);
localStorage.setItem("skd_end_date", skdStartEndDateDetail);
localStorage.setItem("skd_end_time", skdStartEndTimeDetail);
localStorage.setItem("skd_content", skdContentDetail);



function resultSkdDetail(){
shareValue.innerHTML= skdShareDetail;
titleValue.innerHTML =skdTitleDetail;
typeValue.innerHTML  =skdTypeDetail;
StartTimeValue.innerHTML =skdStartTimeDetail;
EndTimeValue.innerHTML =skdStartEndTimeDetail;
ContentValue.innerHTML =skdContentDetail;

}

function init() {
    resultSkdDetail();
   
  }
  
 init();


 //calendar 전역번수로 선언 
 var calendar = null;

 //스케쥴의 상세내역을 담고 있는 skdObj를 전역변수로 선언
 var skdInputObj;


 //html 로드 후 javascript 로드 
 $(document).ready(function () {
   //calendar id를 가진div 객체를 변수에 담기
   var calendarEl = document.getElementById("calendar");
   //풀 캘린더를 활성화
   calendar = new FullCalendar.Calendar(calendarEl, {

     initialView: 'dayGridMonth', //1~31 달력 
     locale: 'ko',
     editable: true, // 일정 수정 가능 여부
     eventLimit: true, /*달력상에 셀 크기보다 많은 이벤트가 등록되어 있는 경우 more로 표기*/
     dayMaxEventRows: true,
     navLinks: true,
     selectable: true,
     // select: function (arg) {	// 날짜셀 클릭시(아래는 일정 추가) 
     //   createModal("skdInput");
     //   if (title) {
     //     calendar.addEvent({
     //       title: title,
     //       start: arg.start,
     //       end: arg.end,
     //       allDay: arg.allDay
     //     })
     //   }
     //   calendar.unselect();
     // }
     // , unselect: function (arg) { },
     // eventLimit: true,/*달력상에 셀 크기보다 많은 이벤트가 등록되어 있는 경우 more로 표기*/
     // // navLinkWeekClick: function (weekStart, jsEvent) {
     // //   console.log('week start', weekStart.toISOString());
     // //   console.log('coords', jsEvent.pageX, jsEvent.pageY);
     // // },



     customButtons: {
       addeventButton: {
         text: '일정 추가',
         id: 'modalInputBtn',
         click: function () {
           //modal 띄우기
           const modal = document.getElementById("skdInput");
           const closeBtn = document.querySelector("button.cancel");
           modal.classList.remove("hidden");

           $(closeBtn).on("click", function () {
             modal.classList.add("hidden");
           });

           var skdInputForm = document.getElementById('skdInput');

           var test = 'p';
           var skdInputShare = $('input[name="radio-3"]');
             console.log(skdInputShare);
             skdInputShare.change(function(){
               test = this.value;
              console.log(test);
             });

             var skdInputTypeValue = '업무';
             var skdInputTypeObj = $("#skdType");
             skdInputTypeObj.change(function(){
             console.log(this.value);
             skdInputTypeValue=this.value+"";
             });

           //저장버튼 클릭이벤트

           $("button.skdInsertSaveBtn").on("click", function (e) {

             var selectOption = document.getElementById("skdType");
             

             var skdInputTitle = $("#input_title"); //제목
             var skdInputContent = $("#input_content"); //내용
             var skdInputStartDate = $("#start_date"); //시작날짜
             var skdInputStartTime = $("#start_time");//시작시간
             var skdInputEndDate = $("#end_date");//종료날짜
             var skdInputEndTime = $("#end_time");//종료시간
           
             if (skdInputStartTime.val() == "" || skdInputEndTime.val() == "") {
               alert("시간을 입력하세요.");
             } else
               if (new Date(skdInputEndDate.val()) - new Date(skdInputStartDate.val()) < 0) {
                 alert("종료일이 시작일보다 먼저입니다.");
               } else {
                 if (test=='p') {
                   calendar.addEvent(
                     {
                       title: skdInputTitle.val(),
                       content: skdInputContent.val(),
                       start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
                       end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
                       allDay: false,
                       backgroundColor: '#ffc107',
                       calendarType: skdInputTypeValue,
                       teamOrPersonal: test

                     })
                     

                   var skdInsertObj = document.querySelector('#skdInput > div.modal_content > div > form'); // 
                   console.log(skdInsertObj);
                   //일정 저장 버튼 
                   var skdInsertBtn = skdInsertObj.querySelector("button.skdInsertSaveBtn");
                   console.log(skdInsertBtn);
                   var skdInsertUrl = '/back/addschedule';

                   $.ajax({
                     url: skdInsertUrl,
                     method: 'post',
                     datatype: 'json',
                     data: {
                       title: skdInputTitle.val(),
                       content: skdInputContent.val(),
                       start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
                       end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
                       allDay: false,
                       backgroundColor: '#ffc107',
                       calendarType: skdInputTypeValue,
                       teamOrPersonal: test
                     },
                     success: function () {
                       alert('일정이 추가되었습니다');
                      
                       $('#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(4) > a').trigger('click');

                     },
                     error: function (request, status, error) {
                       alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                     }

                   });
                   e.preventDefault(); //이걸 해줘야 하나? 


                 } else if(test=='t'){
                   
                   calendar.addEvent({
                     title: skdInputTitle.val(),
                     content: skdInputContent.val(),
                     start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
                     end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
                     allDay: false,
                     backgroundColor: '#28a745',
                     extendedProps: {
                       calendarType: skdInputTypeValue,
                       teamOrPersonal: test
                     }
                   })
                     console.log(skdInputTitle.val() + skdInputContent.val());
                   
                     $.ajax({
                     url: skdInsertUrl,
                     method: 'post',
                     datatype: 'json',
                     data: {
                       title: skdInputTitle.val(),
                       content: skdInputContent.val(),
                       start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
                       end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
                       allDay: false,
                       backgroundColor: '#28a745',
                       extendedProps: {
                         calendarType: skdInputTypeValue,
                         teamOrPersonal: test
                       }
                     },
                     success: function () {
                       alert('일정이 추가되었습니다');
                       $('#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li.sidebar-item.active > a').trigger('click');

                     },
                     error: function (request, status, error) {
                       alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                     }

                   });
                   e.preventDefault(); //이걸 해줘야 하나? 
                   // $("#demo-calendar").fullCalendar('addEventSource', JSON, true); 

                 }

               }


           });
         }
       },
       teamSchedule: {
         text: '공유 캘린더',
         class: 'teamSchedule'
       },
       personalSchedule: {
         text: '내 캘린더',
         class: 'personalSchedule'
       },
       weekSchedule: {
         text: '주간',
         class: 'weekSchedule'
       },
     },
     //left, center, right 구조 바꾸도록 함 
     headerToolbar: {
       left: 'addeventButton teamSchedule personalSchedule',
       center: 'title',
       right: 'dayGridWeek,dayGridMonth today prev,next'

     },
     //   하루 스케쥴 추가, ajax로 작업하기 
     events: [
       {
         start: "2021-07-01 11:00",
         end: "2021-07-01 11:00",
         title: "테스트"
       },
       {

       }],



     //extendedProp 적용
     eventDidMount: function (info) {

       // {calendarType: 캘린더타입, teamOrPersonal: 팀or개인일정}
     },

     //일정 클릭했을 때 
     eventClick: function (info) {

       createModal("skdDetail");

     }


   });

   //eventSources : ajax로 기등록된 일정 불러오기 
   //eventAdd: 일정 추가 저장
   //eventChange : 일정 변경 후 저장
   //eventRemove : 일정 삭제 

   //캘린더 이벤트 코드는 여기까지 

   //시작 날짜를 현재 날짜로
   document.getElementById('start_date').value = new Date().toISOString().substring(0, 10);
   //종료 날짜를 현재 날짜로
   document.getElementById('end_date').value = new Date().toISOString().substring(0, 10);

   //시작 시간을 현재 시간으로 
   document.getElementById('start_time').value = new Date(new Date()).toTimeString();
   //종료 시간을 현재 시간으로 
   document.getElementById('end_time').value = new Date(new Date()).toTimeString();

   // //기간으로 검색하기, 제목내용으로 검색하기 창 설정
   var skdSearchObj = document.getElementById("skdSearch"); //검색입력창
   var categoryObj = document.querySelector("div.dropdown-menu");

   function categoryHandler(e) {
     if (e.target.id == "skdCategoryPeriod") {
       createModal("skdSearchPeriod");
     } else if (e.target.id == "skdCategoryTitle") {
       createModal("skdSearchTitle");
     } else if (e.target.id == "skdCategoryAll") {
       createModal("skdSearchAll");
     }

   }

   function init() {
     categoryObj.addEventListener("click", categoryHandler);
   }

   init();


   //모든 코드를 실행 후 캘린더를 렌더링
   calendar.render();
 });


 //전체 저장 함수, 나한테 전체 저장은 필요 없고 형식만 가져오면 됨
 // function allSave(){
 //   var allEvent = calendar.getEvents();
 //  // console.log(allEvent);//테스트 

 //  var events =new Array();
 //   for(var i=0; i<allEvent.length; i++){

 //     var obj = new Object(); // 아래 데이터를 담을 객체 만들기 
 //     obj.title = allEvent[i]._def.title;
 //     obj.allday = allEvent[i]._def.allday;
 //     obj.start = allEvent[i]._instance.range.start;
 //     obj.end = allEvent[i].instsance.range.end; //obj의 변수에 넣기

 //      //모든 이벤트가 없어질 때까지,타이틀 갖고오기 테스트vlfdy
 //      events.push(obj);// 배열에 obj넣기 
 //   }

 //      // events를 서버에 전송할 때 문자열 형태로넘겨야 해서 String으로 변환할 필ㄹ요가 있음 
 // var jsondata = JSON.Stringify(events);
 // console.log(jsondata); //jsondata 콘솔테스트

 // savedata(jsondata); //savedata 메서드에 jsondata 넣기 
 // }

 // //ajax넣기 
 // function savedata(jsondata){
 //   $.ajax
 // } 


 //데이터베이스에 저장할 데이터추출하기 
 //title, start, end 등 라이브러리에서 갖고 오는 

 
 
  //modal 만드는 함수
    //파라미터 : class="modal" 의 id
    function createModal(id) {

      var modal = document.getElementById(id);
      modal.classList.remove("hidden");//모달열기
      var closeBtn = modal.querySelector("button.cancel");

      var overlay = modal.querySelector(".modal_overlay");
      var deleteBtn = modal.querySelector("button.deleteBtn");
      var xBoxBtn = modal.querySelector("button.xBox");
      var modifyBtn = modal.querySelector("button.modifyBtn");
      var modifySubmitBtnObj = modal.querySelector("button.modifySubmit");

      //함수
      var openModal = () => {
        modal.classList.remove("hidden");
      }
      var deleteSKD = () => {
       // window.alert('일정을 삭제하시겠습니까?');
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

      if(modifyBtn!=null){
        modifyBtn.addEventListener('click', function(){
          modal.classList.add("hidden");
          createModal("skdModifyDetail");
        });
      }

      if(modifySubmitBtnObj!=null){
        modifySubmitBtnObj.addEventListener('click',function(){
          modal.classList.add("hidden");
        });
      }


    }