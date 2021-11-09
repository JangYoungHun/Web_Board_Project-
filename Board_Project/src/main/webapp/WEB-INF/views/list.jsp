<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet"
	href="/resources/css/bootstrap.min.css" />
<!-- Custom styles for this template -->

<style>

.pagination{
padding: 15px 0 5px 10px;
 display: table;
  margin-left: auto;
   margin-right: auto;
    text-align: center;

}

.page-item{
	float: left;
	border: 1px
}


</style>
<body>

	<table class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Bno</th>
				<th scope="col">Title</th>
				<th scope="col">Writer</th>
				<th scope="col">UpdateDate</th>
				<th scope="col">RegDate</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<!-- 받아온 리스트 출력 하는 곳 -->
		</tbody>
	</table>

<form action="">
</form>
	<button type="button" id="addBtn">추가</button>

<div>
	
    <ul class="pagination">
    <c:if test="${criteria.prev}">
 		<li class="page-Btn"><a href="#"><<<</a></li>
 	</c:if>
 	
 	<c:forEach var="num" begin="${criteria.startPage}" end="${criteria.endPage}">
 		 <li class="page-item" ><a href="${num}">${num}</a></li>
 	</c:forEach>

    <c:if test="${criteria.prev}">
 			<li class="page-Btn"><a href="#">>>></a></li>
 	</c:if>
    
   </ul>
</div>

<!-- 페이징 용도,   하단 페이징 버튼에 사용  -->
<form id="pagingForm" action="/board/list" method="get">
	<input type="hidden" name="pageNum" value="${criteria.pageNum}">
	<input type="hidden" name="amount" value="${criteria.amount}">
</form>

	<script src="/resources/js/board.js"></script>

	<script>
	
	function getList(pageNum, amount){
		boardService.getList({pageNum : pageNum, amount : amount}, function(list) {
			console.log(list);	
			var str = "";
			list.forEach(function(item) {
				str += "<tr>"
						+ "<td>" + item.bno + "</td>"
						+ "<td>" + "<a href=\""+ item.bno +"\" id=\"getBoard\">" + item.title +"</a>" + "</td>" 
						+ "<td>" + item.writer + "</td>" 
						+ "<td>" + item.regdate + "</td>" 
						+ "<td>" + item.updatedate + "</td>" 
						+ "</tr>"
				
			});	
			tbody.html(str);
		});
	}
	
		var tbody = $("#tbody");
		$(document).ready(function() {
			getList(${criteria.pageNum}, ${criteria.amount});
			
			//조회 창 이동
			$("#tbody").on("click","#getBoard",function(e){
				e.preventDefault();
				pagingForm.attr("action", "/board/get/"+ $(this).attr("href"));
				pagingForm.submit();
			});
		});
	
		//패이징 버튼, 하단 숫자 버튼 클릭 이벤트
		var pagingForm = $("#pagingForm");
	$(".pagination").on("click", "li", function(e){
		
		e.preventDefault();
		pagingForm.find("input[name='pageNum']").val($(this).find("a").attr("href"));
		pagingForm.submit();
		});

		
	//addBtn 등록 버튼 클릭 이벤트
	$("#addBtn").on("click", function(e){
		pagingForm.attr("action", "/board/registerPage");
		pagingForm.submit();
	});
	

		
	</script>

</body>
</html>
