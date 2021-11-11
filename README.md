# Web_Board_Project
NoticeBoard 프로젝트의 FrontEnd를 APP -> WEB으로 재구성한 프로젝트  
기본적인 CRUD를 이용하여 게시글을 작성, 수정, 공유, 삭제를 할 수 있는 웹 사이트 제작 프로젝트.

## 사용 기술
+ Front End 
> + HTML 
> + javascript
> + jquery
> + Ajax

Back End
> + Java
> + Spring FrameWork
> + REST
> + Oracle Database
> + Mybatis


## Oracle DataBase 구조
![그림1](https://user-images.githubusercontent.com/81062639/140886977-88ca6b3c-0736-4202-ad62-4ec375f63af1.png)


## 화면 구성
![dsadasdsad](https://user-images.githubusercontent.com/81062639/140887021-aead5489-c5c0-4a3e-9669-4871509d7ad3.PNG)


## 목록 조회 페이지
Ajax를 사용하여 작성된 게시글의 목록 데이터를 json 형식으로 받아 데이터 테이블에 추가하여 보여주는 페이지.  
목록을 보여주는 목록테이블, 하단의 게시글 추가 버튼, 페이지 버튼으로 구성되어있다.  
페이지번호와 한 페이지당 요소의 갯수 정보를 이용하여 Paging 기능을 구현하였다.  

## Paging 관련 클래스  
### Criteria  
페이징 기능을 구현 하는데 필요한 요소 클래스  


```java
public class Criteria {
	
	// 현재 페이지 번호
	long pageNum;
	// 페이지별 요소의 갯수
	long amount;
	
	//DataBase의 전체 요소 갯수
	long total;
	
	/* 마지막 페이지
	 * ex) 1~10 페이지를 보여줄경우 startPage = 1 endPage = 10
	 * 11페이지로 넘어갈 경우  startPage = 11 endPage = 20 
	 */
	long endPage;
	long startPage;
	
	// 이전 버튼 여부
	boolean prev;
	// 다음 버튼 여부
	boolean next;
		
	// 초기화 
	// 페이징에 필요한 데이터를 계산한다.
	public void init() {

		long realEnd = (long)(Math.ceil(total/((amount)*(1f))));
		endPage = (long)(Math.ceil(pageNum/10.0))*10;	

		startPage = endPage - 9;
		
		if(realEnd < endPage) {
			endPage = realEnd;
		}	
		prev = startPage > 1;
		next = endPage < realEnd;
	}
	
}
```


### 버튼 구성
목록 조회 페이지에는 게시글 추가 버튼과 페이징 버튼이 있다.    
각각의 버튼에 Jquery를 이용하여 onClick 이벤트를 등록한다.  
  
> + 게시글 추가 버튼   

게시글을 추가하는 페이지로 이동한다. 


```javascript
//페이징 버튼, 하단 숫자 버튼 클릭 이벤트  
// 하단의 페이지 버튼을 눌렀을 떄 해당하는 페이지의 요소의 데이터를 요청하고 결과를 받아 화면에 출력한다.
		var pagingForm = $("#pagingForm");
	$(".pagination").on("click", "li", function(e){
		
		e.preventDefault();
		pagingForm.find("input[name='pageNum']").val($(this).find("a").attr("href"));
		pagingForm.submit();
		});
```


> + 목록 조회 버튼    

서버에 해당하는 페이지의 요소데이터를 비동기적으로 요청한다.
 
 
> 
```javascript
//Ajax 요청


//페이징 버튼, 하단 숫자 버튼 클릭 이벤트
// 하단의 페이지 버튼을 눌렀을 떄 해당하는 페이지로 이동한다.
	var pagingForm = $("#pagingForm");
	$(".pagination").on("click", "li", function(e){
		
	e.preventDefault();
	pagingForm.find("input[name='pageNum']").val($(this).find("a").attr("href"));
	pagingForm.submit();
	});


/*****************************************************************************************************************/


// board.js 
// 게시글과 관련된 함수들을 포함하고 있는 파일.
// 해당하는 페이지의 목록 조회 요청을 한다.

let boardService = (function(){	
function getList(param,callback){
  $.getJSON(`/board/getList?pageNum=${param.pageNum}&amount=${param.amount}`, function(data){
    callback(data);
   }
    ).fail(function(shr,status, err){});
  }
  return {getList:getList};
})();


/*****************************************************************************************************************/


// list.jsp
//요청의 결과로 받은 json 데이터를 테이블에 추가하며 화면에 출력한다.
// callback 함수 전달.
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
	});}


```

## 개별 조회, 수정, 추가 페이지  

개별 조회, 수정, 추가 페이지는 공통적인 페이지 구성을 가지고 있다.  
제목, 작성자 내용을 입력하는 형식을 가지고 있다.  

### 공통 구성 요소  
공통 구성요소에는 5개의 input 태그가 있다.  
각 페이지 별로 필요한 요소가 다르기 떄문에 input 태그의 disabled 속성과 readonly 속성을 이용한다.

#### input 태그
> + title : 제목
> + writer : 작성자
> + content : 내용 
> + pageNum: 현재 페이지 번호
> + amount: 페이지별 요소의 갯수


```javascript
	<form id="actionForm" action="해당하는 URI" method="HttpMethod">
		<fieldset style="width: 800px;">

			<div class="form-group row">
				<div>
					// 제목 입력 or 조회란
					<label for="staticEmail" class="col-sm-2 col-form-label">제목</label>
				</div>
				<div id="title" class="col-sm-10">
					<input name="title" type="text" value="" maxlength="20">
				</div>
			</div>
			<div class="form-group">
				<div>
					// 작성자 입력 or 조회란
					<label for="exampleInputEmail1" class="form-label mt-4">작성자</label>
				</div>
				<input name="writer" type="text" value="" maxlength="10">
			</div>

			<div class="form-group">
				<div>
					// 내용 입력 or 조회란
					<label for="exampleTextarea">내용</label>
				</div>
				<textarea id="content" name="content" rows="3" 
					style="margin: 0px; width: 650px; height: 250px;"> </textarea>
			</div>
				
				// 페이징 기능을 위한 요소
				// pageNum: 현재 페이지 번호.
				// amount: 페이지별 요소의 갯수
			<div class="form-group">
				<input type="hidden" name="pageNum" value="${criteria.pageNum}">
			</div>
			<div class="form-group">
				<input type="hidden" name="amount" value="${criteria.amount}">
			</div>
			
			/*  페이지별 달라지는 부분
			* 페이지별 버튼의 구성이 달라진다.
			* <button type="reset">초기화</button>
			* <button id="cancle_Btn" type="button">취소</button>
			* <button id="add_Btn" type="button">확인</button>
			*/
	</form>
```


### 페이지별 다른 구성 요소
페이지별로 하단의 버튼의 구성이달라진다.
각각의 버튼의 기능은 Jquery를 이용하여 구현하였다.

#### 개별 조회 페이지
> + 수정 버튼 : 현재 게시글을 수정하는 페이지로 이동한다.
> + 삭제 버튼 : 현재 조회 중인 게시글을 삭제한다.
> + 확인 버튼 : 게시글 조회를 완료하고 목록 조회 페이지로 이동한다.

```javascript

	//삭제 버튼 클릭시 Form 정의
	$("#delete_Btn").on("click", function(e){
		actionForm.attr("action", "/board/remove/" + ${board.bno});
		actionForm.submit();
	});
	
/***********************************************************************************/	

	//수정 버튼 클릭시 Form 정의
	$("#update_Btn").on("click", function(e){
		actionForm.attr("action", "/board/modifyPage");
		actionForm.attr("method", "post");
		$("input[name='title']").attr("disabled", false);
		$("input[name='writer']").attr("disabled", false);
		$("#content").attr("disabled", false);	
		actionForm.submit();
	});
	
/***********************************************************************************/		
	
		// 확인 버튼 클릭 시 Form 정의
	$("#ok_Btn").on("click", function(e){
		actionForm.attr("action", "/board/list");
		actionForm.attr("method", "get");
		actionForm.submit();
	});
```



#### 수정 페이지
> + 취소 버튼 : 현재 게시글을 수정하는 작업을 취소한다.
> + 확인 버튼 : 현재 수정한 내용을 반영하여 게시글을 수정한다.

```javascript
	//수정 완료 버튼
	$("#ok_Btn").on("click",function(e){
		
		console.log("click");
		actionForm.attr("action", "/board/modify");
		actionForm.attr("method", "post");		
		actionForm.submit();
	});
	
/***********************************************************************************/	

	//수정 취소 버튼
	$("#cancle_Btn").on("click",function(e){
		
		console.log("click");
		actionForm.attr("action", "/board/list");
		actionForm.attr("method", "get");
		actionForm.submit();
	});
```

#### 추가 페이지
> + 초기화 버튼 : 지금까지 입력한 내용을 초기화 시킨다.  Button type:"reset" 사용
> + 취소 버튼 : 새로운 게시글 등록을 취소한다.  
> + 확인 버튼 : 입력한 내용의 게시글을 추가한다.  


```javascript

	//메모 등록 취소 버튼
	$("#cancle_Btn").on("click", function(e){
		actionForm.attr("action", "/board/list");		
		actionForm.attr("method", "get");
		$("input[name='title']").attr("disabled", true);
		$("input[name='writer']").attr("disabled", true);
		$("#content").attr("disabled", true);	
		actionForm.submit();
	});
	
/***********************************************************************************/	

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


```



