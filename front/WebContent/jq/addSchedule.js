
// $(function(){
// var skdInsertObj = document.querySelector('#skdInput > div.modal_content > div > form'); // 
// console.log(skdInsertObj);
// //일정 저장 버튼 
// var skdInsertBtn = skdInsertObj.querySelector("button.skdInsertSaveBtn");
// console.log(skdInsertBtn);
// var skdInsertUrl = '/back/addschedule';

// function addScheduleClickeHandler(e){
//     console.log(e.target.title + e.target.start);
//     $.ajax({
//         url: skdInsertUrl,
//         method: 'post',
//         datatype: 'json',
//         data: {
//             title: skdInputTitle.val(),
//             content: skdInputContent.val(),
//             start: skdInputStartDate.val() + " "+skdInputStartTime.val(),
//             end: skdInputEndDate.val() + " "+skdInputEndTime.val(),
//             allDay: false,
//             backgroundColor: '#ffc107',
//             extendedProps: {
//               calendarType: skdInputType.value,
//               teamOrPersonal: skdInputShare.val()
//           }
//         },
//         success: function(){
//             alert('일정이 추가되었습니다');
//             // $('#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li.sidebar-item.active > a').trigger('click');

//         },
//         error:function(request,status,error){
// alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);}

//     });
//     e.preventDefault(); //이걸 해줘야 하나? 
//     // $("#demo-calendar").fullCalendar('addEventSource', JSON, true); 
// }

// skdInsertObj.addEventListener('submit', addScheduleClickeHandler);
// //button 누르면 알아서 submit 되니까 submit됐을 때 전송하는 걸로 해도 됨
// });