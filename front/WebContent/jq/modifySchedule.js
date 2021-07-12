$(function(){

//localStorage 값 받아오기 
var skdOriginType = localStorage.getItem('skd_type');
var skdOriginTitle =  localStorage.getItem('skd_title');
var skdOriginContent =  localStorage.getItem('skd_content');
var skdOriginStartDate = localStorage.getItem('skd_start_date'); //시작날짜
var skdOriginStartTime = localStorage.getItem('skd_start_time');
var skdOriginEndDate = localStorage.getItem('skd_end_date');//종료날짜
var skdOriginEndTime = localStorage.getItem('skd_end_time');//종료시간
var skdOriginShare = localStorage.getItem('skd_share');

//경로
var backSkdModify= '/back/modifyschedule';


var modifySkdFormObj = $('#modifySkdContent');
var modifySkdSubmitBtn = $('button.modifySkdSubmit');
var skdUpdateTypeObj = $("#skdUpdateTypeSelect");//problem

var skdUpdateTypeValue = '업무';
skdUpdateTypeObj.change(function(){
 console.log(this.value);
 skdUpdateTypeValue=this.value+"";
});

var skdUpdateTitle = $('#update_title');
var skdUpdateContent = $("#input_content_update"); //내용
var skdUpdateStartDate = $("#start_date_update"); //시작날짜
var skdUpdateStartTime = $("#start_time_update");//시작시간
var skdUpdateEndDate = $("#end_date_update");//종료날짜
var skdUpdateEndTime = $("#end_time_update");//종료시간
var skdUpdateShare2 = $('input[name="radio-2"]'); //problem$(':radio[name="radioValue"]:checked').val();
// var skdUpdateShare = $('input[name="radio-2"]:checked'); //problem$(':radio[name="radioValue"]:checked').val();
var test='p';
skdUpdateShare2.change(function(){
  console.log(this.value);
  test = this.value;
  console.log(test);
})


//기존 값 넣기 
skdUpdateTitle.attr('value', skdOriginTitle) ;
skdUpdateContent.val(skdOriginContent);
skdUpdateStartDate.val(skdOriginStartDate);
skdUpdateStartTime.val(skdOriginStartTime);
skdUpdateEndDate.val(skdOriginEndDate);
skdUpdateEndTime.val(skdOriginEndTime);
//skdUpdateType.val(skdOriginType);

//시간, content는 가능
//dropdown, radio는 불가 
//skdUpdateShare(skdOriginShare);



function modifySkdSubmitHandler(e) {
// console.log(skdUpdateShare);
    if (test == "p"){
		$.ajax({
      
			url: backSkdModify,
			method: "post",
			data: {
        title: skdUpdateTitle.val(),
        content: skdUpdateContent.val(),
        start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
        end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
        allDay: false,
        backgroundColor: '#ffc107',
        calendarType: skdUpdateTypeValue,
        teamOrPersonal: test

			},
			success: function() {
					alert("일정이 변경되었습니다");

			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});
		e.preventDefault();

 }else if(test == "t"){
  $.ajax({
    url: backSkdModify,
    method: "post",
    data: {
      title: skdUpdateTitle.val(),
      content: skdUpdateContent.val(),
      start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
      end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
      allDay: false,
      backgroundColor: '#28a745',
        calendarType: skdUpdateTypeValue,
        teamOrPersonal: test

    }  ,
    success: function() {
        alert("일정이 변경되었습니다");
    },
    error: function(request, status, error) {
      alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
    }
  });
  e.preventDefault();
}
}

modifySkdSubmitBtn.click(modifySkdSubmitHandler);

});
