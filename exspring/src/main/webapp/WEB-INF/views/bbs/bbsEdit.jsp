<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- <!DOCTYPE html>          
<html>                   
<head>                   
<meta charset=\"UTF-8\"> 
<title>게시판</title>     
</head>                  
<body> -->
<%-- <jsp:include page="/WEB-INF/views/menu.jsp" /> --%>
                		
<h1>게시글정보변경</h1>
<form action="${pageContext.request.contextPath}/bbs/edit.do" method="post" >
	번호: <input type="hidden" name="bbsNo" value="${bbsVo.bbsNo}" /><!-- readonly="readonly" --><br>
	<%-- <c:set value="${bbsVo.bbsWriter == loginUser.memId}" var="isMine" scope="" /> ${isMine}으로 꺼내서 사용 --%>
	<%-- <c:set value="${pageContext.request.contextPath}" var="contextPath" /> --%>
	
	제목: <input <c:if test="${bbsVo.bbsWriter != loginUser.memId}">readonly</c:if> type="text" name="bbsTitle" value="<c:out value="${bbsVo.bbsTitle}" />" /><br>
	내용: <textarea ${bbsVo.bbsWriter != loginUser.memId? 'readonly' : ''} name="bbsContent" rows="5" cols="30"><c:out value="${bbsVo.bbsContent}" /></textarea><br>
	
	작성자: <input type="hidden" name="bbsWriter" value="${bbsVo.bbsWriter}" /><c:out value="${bbsVo.bbsWriter}" /><br>
	등록일: <fmt:formatDate value="${bbsVo.bbsRegDate}" pattern="yyyy/MM/dd HH:mm:ss"/><br>
	
	<!-- 첨부파일들이 출력되도록 구현 -->
	<c:forEach var="vo" items="${bbsVo.attachList}">
		첨부파일: <a href="${pageContext.request.contextPath}/bbs/down.do?attNo=${vo.attNo}"><c:out value="${vo.attOrgName}" /></a> <br> <!-- 파일명도 문자열이므로, c:out 태그를 씌움 -->
	</c:forEach>
	<%-- 첨부파일1: <input type="file" name="bbsFile" value="<c:out value="${bbsVo.attachList[0].attOrgName}" />" /><br>
	첨부파일2: <input type="file" name="bbsFile" value="<c:out value="${bbsVo.attachList[1].attOrgName}" />" /><br> --%>
	
<!-- 1.로그인한 사용자가 작성한 글인 경우에만 저장 버튼과 삭제 버튼을 출력 -->
<!-- 2.삭제 버튼을 클릭하면, 삭제여부를 확인하고, 삭제하겠다고 응답한 경우에만 삭제 -->

	<!-- 자신이 작성한 글이 아닌 경우, 제목과 내용을 키보드로 입력할 수 없도록 구현 -->
	<c:if test="${bbsVo.bbsWriter == loginUser.memId}">
	
		<input type="submit" value="저장" />
	
		<a id="delLink" href="${pageContext.request.contextPath}/bbs/del.do?bbsNo=${bbsVo.bbsNo}&bbsWriter=${bbsVo.bbsWriter}"><button type='button' id="bbsDelBtn">삭제</button></a>
	
	</c:if>
	
</form>



<!--
<table>
<thead>
	<tr>
		<th>제목</th>
		<th>출판사</th>
		<th>가격</th>
	</tr>
</thead>
<tbody>
	<tr>
		<td><a id="" class="" href="...">자바웹을다루는기술</a></td>
		<td id="" class="">길벗</td>
		<td id="" class="">30000</td>
	</tr>
</tbody>
</table>

XML
<book>
	<title>자바웹을다루는기술</title>
	<pub>길벗</pub>
	<price>30000</price>
</book>

JSON
{
	title: '자바웹을다루는기술',
	pub : '길벗',
	price : 30000
}
-->


<hr> <!-- 수평선 -->
<form id="replyForm" action="${pageContext.request.contextPath}/reply/add.do" method="post">
	<input type="hidden" name="repBbsNo" value="${bbsVo.bbsNo}" />
	<textarea name="repContent" rows="3" cols="30"></textarea>
	<!-- <input type="submit" /> -->
	<input id="repSaveBtn" type="button" value="저장" />
</form>


<hr>

<div id="replyList"></div>

<template id="replyTemp">
	<div class="repContent"></div>
	<div class="repWriter"></div>
	<div class="repRegDate"></div>
	<input type="button" value="삭제" class="delBtn" data-no=""/>
	<hr>
</template>


<!-- 댓글 내용을 입력하고 submit 버튼을 클릭하면, -->
<!-- reply 테이블에 댓글내용이 저장(insert)되도록 구현 -->



<script type="text/javascript">
	$('#delLink').on('click', function(ev) { //이벤트 실행할 때, on 메서드 사용, 1번째 인자로 이벤트 이름, 2번째 인자로 함수 이름
		var ok = confirm('정말 삭제?');
		if(!ok) {
//			ev.preventDefault(); //이벤트에 대한 브라우저의 기본동작을 취소
			return false; //이벤트 전파를 중단 하고, 이벤트에 대한 브라우저의 기본동작을 취소
		}
	});


//	<template> 엘리먼트의 내용은 content 속성을 사용하여 접근
	var $repTemp = $(document.querySelector('#replyTemp').content); //html5부터 지원하는 명령어
	//document.getElementById('replyTemp') //옛날부터 있던 명령어
	//var $repTemp = $(document.getElementById('replyTemp').content);
	
//1.로그인한 사용자가 작성한 댓글에만 삭제 버튼 출력
//2.삭제버튼 클릭시, 삭제여부를 묻는 창을 띄우고, 삭제하겠다고 선택한 경우에만 삭제
//3.댓글 저장 성공시, 댓글 입력란의 내용 초기화

	// 댓글을 추가하면, 곧바로 댓글목록에 출력되도록 구현
	// 각 댓글 아래에 삭제 버튼을 출력
	function refreshReplyList(){
		
		//"${pageContext.request.contextPath}/reply/list.do"로 GET 방식 AJAX 요청을 전송하여
		//현재 글에 대한 댓글들을 받아오도록 구현
		$.ajax({
		  url: "${pageContext.request.contextPath}/reply/list.do", //요청주소
		  method: "GET", //요청방식
		  data: { repBbsNo : ${bbsVo.bbsNo} }, //요청파라미터
		  dataType: "json"	//응답데이터타입
		}).done(function( data ) {
			console.log(data); // "1개의 댓글 저장" 이라고 출력되도록
			
			/*
			var listHtml = '';
			for (var i=0; i<data.length; i++){
				var repVo = data[i];
				console.log( repVo.repContent, repVo.repWriter, repVo.repRegDate );
				listHtml += '<div>' + repVo.repContent + '</div>'; 
				listHtml += '<div>' + repVo.repWriter + '</div>'; 
				listHtml += '<div>' + repVo.repRegDate; + '</div>';
				//listHtml += '<div>' + repVo.repNo; + '</div>';				
				//listHtml += '<input type="hidden" name="repNo" value="' + repVo.repNo + '" />';
				//if ( repVo.repWriter == ${sessionScope.loginUser.getMemId()} ) {
				if ( repVo.repWriter == '${loginUser.memId}' ) {
					listHtml += '<div><input data-no="'+repVo.repNo+'" class="delBtn" type="button" value="삭제" /></div>'; //id="repDelBtn"
					//onclick="delReply();"
				}
				listHtml += '<hr>';
			}
			console.log(listHtml); 
			//listHtml 값을 id="replyList"인 div 엘리먼트의 내용으로 출력
			$('#replyList').html( listHtml );
			*/
			
			
			var listHtml = [];
			for (var i=0; i<data.length; i++){
				var repVo = data[i];
				//console.log( repVo.repContent, repVo.repWriter, repVo.repRegDate );
				
				/*
				listHtml.push( $('<div>').text( repVo.repContent ) ); //<div>repVo.repContent</div> 
				listHtml.push( $('<div>').text( repVo.repWriter ) ); //<div>repVo.repWriter</div> 
				listHtml.push( $('<div>').text( repVo.repRegDate ) ); //<div>repVo.repRegDate</div> 
				//listHtml += '<div>' + repVo.repNo; + '</div>';				
				//listHtml += '<input type="hidden" name="repNo" value="' + repVo.repNo + '" />';
				//if ( repVo.repWriter == ${sessionScope.loginUser.getMemId()} ) {
				if ( repVo.repWriter == '${loginUser.memId}' ) {
					//listHtml += '<div><input data-no="'+repVo.repNo+'" class="delBtn" type="button" value="삭제" /></div>'; //id="repDelBtn"
					//listHtml.push( $('<input>').text( repVo.repNo ) ); //<input>repVo.repNo</input>
					listHtml.push( $('<input>').attr( 'data-no', repVo.repNo )
												.attr('type', "button")
												//.attr({ 'data-no': repVo.repNo, type: 'button' })
												.addClass('delBtn') //.attr('class', "delBtn")
												.val('삭제') //.attr('value', "삭제")
								);

				}
				listHtml.push( $('<hr>') );
				*/
					
				var $newRep = $repTemp.clone();
				//$newRep.find('div')
				//$('div', $newRep)
				$newRep.find('.repContent').text( repVo.repContent );
				$newRep.find('.repWriter').text( repVo.repWriter );
				$newRep.find('.repRegDate').text( repVo.repRegDate );
				if ( repVo.repWriter == '${loginUser.memId}' ) {
					$newRep.find('.delBtn').attr( 'data-no', repVo.repNo );
				} else {
					$newRep.find('.delBtn').remove();
				}
				
				listHtml.push( $newRep );
				
				//<div class="repContent">repVo.repContent</div>
				//<div class="repWriter">repVo.repWriter</div>
				//<div class="repRegDate">repVo.repRegDate</div>
				//<input type="button" value="삭제" calss="delBtn" data-no="repVo.repNo"/>
				//<hr>
				
			}
			//console.log(listHtml); 
			//listHtml 값을 id="replyList"인 div 엘리먼트의 내용으로 출력
			$('#replyList').empty().append( listHtml );
			
		}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행
		  alert( "Request failed: " + textStatus );
		});
		
	}
	
	
/* 	function delReply() {
		alert('삭제!');
	} */
	
	
	//삭제버튼을 클릭하면 해당 댓글이 삭제되도록, 
	//ReplyController.java, ReplyService.java, ReplyServiceImpl.java, ReplyDao.java
	//,ReplyMapper.xml 파일을 변경
	$('#replyList').on('click', '.delBtn', function() {
		//alert('삭제!' + $(this).attr('data-no'));
		var ok = confirm('정말 삭제?');
		
		if (!ok) return;
		
		$.ajax({
			  url: "${pageContext.request.contextPath}/reply/del.do", //요청주소
			  method: "GET", //요청방식
			  data: { repNo : $(this).attr('data-no') }, //요청파라미터
			  dataType: "json"	//응답데이터타입				
			}).done(function( msg ) { //요청 전송 후 성공적으로 응답을 받았을 때 실행
				refreshReplyList();
				alert(msg.result + "개의 댓글 삭제"); // "1개의 댓글 저장" 이라고 출력되도록
			}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행
			  alert( "Request failed: " + textStatus );
			});
		
	});
	
	refreshReplyList();

	//저장버튼을 클릭했을 때, AJAX로 댓글 저장 요청을 전송
	//AJAX
	//(1)XmlHttpRequest 객체 사용
	//(2)fetch() 함수 사용
	//(3)$.ajax() 메서드 사용
	
	
	//저장버튼을 클릭했을 때, 알럿창으로 '클릭!' 출력
	$('#repSaveBtn').on('click', function() {
		/* alert("클릭!"); */
		
		$.ajax({
			  url: "${pageContext.request.contextPath}/reply/add.do", //요청주소
			  method: "POST", //요청방식
			  //data: 'repBbsNo=${bbsVo.bbsNo}&repContent='+$('[name="repContent"]').val(), //요청파라미터
			  data: { repBbsNo : ${bbsVo.bbsNo}, repContent : $('[name="repContent"]').val() }, //요청파라미터
			  //data: $('#replyForm').serialize() , //요청파라미터, 입력요소를 전부다 파라미터로 바꿔라 => serialize
			  dataType: "json"	//응답데이터타입
			  //"json"으로 설정하면, 응답으로 받은 JSON 문자열을 자바스크립트 객체로 변환하여
			  //응답처리함수(done())에게 인자로 전달					
			}).done(function( msg ) { //요청 전송 후 성공적으로 응답을 받았을 때 실행
				refreshReplyList();
				// msg === '{"result":1,"ok":true}' => 시작중괄호, 끝중괄호 없애고, :기준으로 해주고, ,(콤마)기준으로 앞에꺼, ...
				// msg === {"result":1,"ok":true} => 객체 => msg.result	
//				var data = JSON.parse(msg); //JSON(문자열)을 자바스크립트 객체로 변환
				// data === {"result":1,"ok":true} => 객체 => data.result 
				alert(msg.result + "개의 댓글 저장"); // "1개의 댓글 저장" 이라고 출력되도록
				//location.reload();
				$('[name="repContent"]').val('');
			  /* $( "#log" ).html( msg ); */
			}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행
			  alert( "Request failed: " + textStatus );
			});
		
	});
	
	
	/*
	//삭제버튼을 클릭했을 때,
	$('#repDelBtn').on('click', function() {
	
	$.ajax({
		  url: "${pageContext.request.contextPath}/reply/del.do", //요청주소
		  method: "GET", //요청방식
		  data: { repNo : ${repVo.repNo} }, //요청파라미터
		  dataType: "json"	//응답데이터타입
		}).done(function( msg ) {
			alert( msg );
			refreshReplyList();
		}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행
		  alert( "Request failed: " + textStatus );
		});
		
	});
	*/
	
	
	
	
	
	/* $('#repSaveBtn').click(function(){}) */
	
</script>


<!-- <script>
$('#repSaveBtn').on('click', function() {
$.ajax({
	  url: "${pageContext.request.contextPath}/reply/list.do", //요청주소
	  method: "GET", //요청방식
	  data: { repBbsNo : ${bbsVo.bbsNo} }, //요청파라미터
	  dataType: "json"	//응답데이터타입
	}).done(function( msg ) { 
		alert(msg.result + "개의 댓글 저장"); // "1개의 댓글 저장" 이라고 출력되도록
	}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행
	  alert( "Request failed: " + textStatus );
	});
});
</script> -->



                	
<!-- </body>                  
</html> -->           

<!--  
1. 회원정보변경 화면에서 이름과 포인트를 변경하고 submit 버튼을 클릭하면,
	MemEditServlet 클래스의 doPost 메서드가 실행되도록 memEdit.jsp 파일을 변경하세요.
2. 회원정보변경 화면에서 아이디는 키보드로 값을 입력(변경)할 수 없도록
	memEdit.jsp 파일을 변경하세요.
3. MemEditServlet 클래스의 doPost 메서드에서 사용자가 입력한 정보에 따라서
	데이터베이스의 회원 정보(이름, 포인트)가 변경되도록
	MemEditServlet.java, MemberDao.java, MemberDaoBatis.java, MemberMapper.xml 파일을 변경하세요. 
-->	
	       