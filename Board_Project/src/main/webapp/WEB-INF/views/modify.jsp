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
					<input name="title" type="text" value="${board.title}">
				</div>
			</div>
			<div class="form-group">
				<div>
					<label for="exampleInputEmail1" class="form-label mt-4">작성자</label>
				</div>
				<input name="writer" type="text" value="${board.writer}" readonly="readonly">
			</div>

			<div class="form-group">
				<div>
					<label for="exampleTextarea">내용</label>
				</div>
				<textarea id="content" name="content" rows="3"
					style="margin: 0px; width: 650px; height: 250px;">${board.content}</textarea>
			</div>

			<div class="form-group">
				<input type="hidden" name="pageNum" value="${criteria.pageNum}">
			</div>
			<div class="form-group">
				<input type="hidden" name="amount" value="${criteria.amount}">
			</div>

			<button type="button" id="cancle_Btn">취소</button>
			<button type="button" id="ok_Btn">확인</button>
	</form>

</body>

<script>
var actionForm = $("#actionForm");
	
	//수정 완료 버튼
	$("#ok_Btn").on("click",function(e){
		
		console.log("click");
		actionForm.attr("action", "/board/modify");
		actionForm.attr("method", "post");
		
		actionForm.submit();
	});
</script>

</html>