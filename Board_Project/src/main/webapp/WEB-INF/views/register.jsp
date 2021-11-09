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


	<form id="actionForm" action="/board/register" method="post">
		<fieldset style="width: 800px;">

			<div class="form-group row">
				<div>
					<label for="staticEmail" class="col-sm-2 col-form-label">제목</label>
				</div>
				<div id="title" class="col-sm-10">
					<input name="title" type="text" value="" maxlength="20">
				</div>
			</div>
			<div class="form-group">
				<div>
					<label for="exampleInputEmail1" class="form-label mt-4">작성자</label>
				</div>
				<input name="writer" type="text" value="" maxlength="10">
			</div>

			<div class="form-group">
				<div>
					<label for="exampleTextarea">내용</label>
				</div>
				<textarea id="content" name="content" rows="3" 
					style="margin: 0px; width: 650px; height: 250px;"> </textarea>
			</div>

			<div class="form-group">
				<input type="hidden" name="pageNum" value="${criteria.pageNum}">
			</div>
			<div class="form-group">
				<input type="hidden" name="amount" value="${criteria.amount}">
			</div>

			<button type="reset">초기화</button>
			<button id="cancle_Btn" type="button">취소</button>
			<button id="add_Btn" type="button">확인</button>
	</form>

<script>
var actionForm =$("#actionForm");
	
	//메모 등록 취소 버튼
	$("#cancle_Btn").on("click", function(e){
		actionForm.attr("action", "/board/list");		
		actionForm.attr("method", "get");
		$("input[name='title']").attr("disabled", true);
		$("input[name='writer']").attr("disabled", true);
		$("#content").attr("disabled", true);	
		actionForm.submit();
	});
	
	// 메모 등록 버튼
	$("#add_Btn").on("click", function(e){		
		if($("input[name='title']").val().trim()==""){
			alert("제목을 입력하세요.");
			return;
		}
		else if($("input[name='writer']").val().trim()==""){
			alert("작성자 이름을 입력하세요.");
			return;
		}
		else if($("#content").val().trim() ==""){
			alert("내용을 입력하세요.");
			return;
		}				
		actionForm.submit();
	});
	
	
</script>

</body>

</html>