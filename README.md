# Web_Board_Project-
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

```


