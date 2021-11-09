<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>

	<form id="actionForm" action="/board/list" method="get">
		<fieldset style="width: 800px;">

			<input type="hidden" name="bno" value="${board.bno}">
		
			<div class="form-group row">
				<div>
					<label for="staticEmail" class="col-sm-2 col-form-label">제목</label>
				</div>
				<div id="title" class="col-sm-10">
					<input name="title" type="text" value="${board.title}" disabled="disabled">
				</div>

			<div class="form-group">
				<div>
					<label for="exampleInputEmail1" class="form-label mt-4">작성자</label>
				</div>
				<input name="writer" type="text" value="${board.writer}" disabled="disabled">
			</div>

			<div class="form-group">
				<div>
					<label for="exampleTextarea">내용</label>
				</div>
				<textarea id="content" name="content" rows="3"
					style="margin: 0px; width: 650px; height: 250px;" disabled="disabled">${board.content}</textarea>
			</div>
			
			<!-- 현재 페이지 번호 -->
			<div class="form-group">
				<input type="hidden" name="pageNum" value="${criteria.pageNum}">
			</div>
			<!-- 페이지별 요소 갯수 -->
			<div class="form-group">
				<input type="hidden" name="amount" value="${criteria.amount}">
			</div>

			<button type="button" id="update_Btn">수정</button>
			<button type="button" id="delete_Btn">삭제</button>
			<button type="submit">확인</button>
	</form>

</body>

<script>
	
	var actionForm = $("#actionForm");
	//삭제 버튼 클릭시 Form 정의
	$("#delete_Btn").on("click", function(e){
		actionForm.attr("action", "/board/remove/" + ${board.bno});
		actionForm.submit();
	});
	
	//수정 버튼 클릭시 Form 정의
	$("#update_Btn").on("click", function(e){
		actionForm.attr("action", "/board/modifyPage");
		actionForm.attr("method", "post");
		$("input[name='title']").attr("disabled", false);
		$("input[name='writer']").attr("disabled", false);
		$("#content").attr("disabled", false);	
		actionForm.submit();
	});
</script>

</html>