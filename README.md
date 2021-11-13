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




## Spring Security
Spring Security를 사용하여 사용자 인증과 권한 체크 기능을 구현했습니다.

### 로그인 페이지
Spring Security를 사용하여 기본적인 로그인 기능을 구현했다.
id와 password 를 이용하여 사용자를 인증하고 권한을 확인한다.

![로그인](https://user-images.githubusercontent.com/81062639/141301459-23972033-9105-4ce8-a3e3-493497e529d8.PNG)

### loginPage.jsp

```html
 <form method='post' action="/login">
  <div>
    <input type='text' name='username' value='test'>
  </div>
  <div>
    <input type='password' name='password' value='test'>
  </div>

  <div>
    <input type='checkbox' name='remember-me'> 로그인 유지
  </div>

  <div>
    <input type='submit'>
  </div>
    <input type="hidden" name="${_csrf.parameterName}"
    value="${_csrf.token}" />  
  </form>
```

## 자동 로그인 설정 (remember-me)
자동 로그인 체크박스를 클릭후 로그인 시 자동 로그인을 위한 Cookie remember-me 가 생성된다.  
쿠키가 만료되기 전까지 로그인이 유지된다. 
### HTML
```html
  <div>
    <input type='checkbox' name='remember-me'> 로그인 유지
  </div>
```
### security-context.xml 설정
```xml
<security:http>	
	// remember-me 설정
	<security:remember-me data-source-ref="dataSource" token-validity-seconds="600000"/>	
</security:http>
```

## 사용자 인증
사용자가 로그인 정보를 입력하면 로그인 정보가 올바른 정보인지 확인을 한다.    
사용자가 입력한 id에 해당하는 사용자의 정보를 userlist 테이블에서 조회하고 UserDetails 구현체를 생성한다.  
생성한 구현체와 사용자가 입력한 정보를 확인하여 인증 처리를 한다.


### Oracle DataBase Table 
사용자 인증과 권한을 체크를 위해 필요한 데이터를 관리하는 Table  

![시큐리티 테이블](https://user-images.githubusercontent.com/81062639/141306513-9da913eb-9e21-42ef-85ff-3335ba59d7b9.png)

### AuthVO
사용자의 권한을 관리하는 Database의 user_auth 테이블에 해당하는 클래스이다.

```java
@Data
public class AuthVO {	
	private String userid;
	private String auth;
}
```


### UserVO
사용자의 정보을 관리하는 Database의 userlist 테이블에 해당하는 클래스이다.

```java
@Data
public class UserVO {
	
	private String userid;
	private String userpw;
	private String username;
	private boolean enabled;
	private Date regDate;
	private Date updateDate;
	private List<AuthVO> authList;
}
```

### security-context.xml 설정
```xml
<security:http>	
	<!-- 로그인 설정 --> 
	<security:form-login login-page="/loginPage"/>
	
	<!-- 로그인 성공 후 필요한 권한이 없을 때 처리할 로직을 가지고있는 AccessDeniedHandler -->
	<security:access-denied-handler ref="customAccessDeniedHandler"/>
</security:http>

<security:authentication-manager>
	<!-- 사용자 인증을 처리할 UserDetailService -->
	<security:authentication-provider user-service-ref="customUserDetailService">
		
	<!-- Password 암호화를위한 Encoder -->
	<security:password-encoder ref="bcryptPasswordEncoder"/>

	</security:authentication-provider>
</security:authentication-manager>
```

### CustomUser
```java
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	private UserVO member;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
	public CustomUser(UserVO vo) {
		
		// 로그인한 유저의 정보로 User를 생성한다.
		super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		this.member = vo;
		
	}
	
}
```

### CustomUserDetailService
```java
public class CustomUserDetailService implements UserDetailsService {
	
	@Setter(onMethod_ = {@Autowired} )
	UserMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		// 해당하는 이름의 유저 정보를 불러온다.
		// 주의 Spring Security 에서 username은 일반적인 userid를 의미한다.
		UserVO member = mapper.read(username);
		
		return member == null ? null : new CustomUser(member);
	}	
}
```

### mapper.read(String userid)
사용자가 입력한 로그인 정보가 올바른 정보인지 확인하기 위해  
매개변수로 전달된 userid에 해당하는 DataBase 에서 User정보를 가져오는 함수이다.  
MyBatis를 사용한다.


#### UserMapeer
사용자의 정보와 관련된 Database 처를 하는 Mybatis 설정 파일이다.

```xml

<mapper namespace="com.board.mapper.UserMapper">
	
	//유저의 권한 정보를 Mapping 한다.
	<resultMap type="com.board.domain.AuthVO" id="authMap">
		<result property="userid" column="userid"/>
		<result property="auth" column="auth"/>
	</resultMap>
	
	//유저의 정보를 Mapping 한다.
	<resultMap type="com.board.domain.MemberVO" id="memberMap">
		<result property="userid" column="userid"/>
		<result property="userpw" column="userpw"/>
		<result property="username" column="username"/>
		<result property="regDate" column="regdate"/>
		<result property="updateDate" column="updatedate"/>
		<collection property="authList" resultMap="authMap"/>
	</resultMap>
	
	//유저의 정보와 권한을 조회하고 Mapping 한다.
	<select id="read" resultMap="memberMap">
		select use.userid, userpw, username, regdate, updatedate, auth
		from userlist use left outer join user_auth auth on use.userid = auth.userid
		where use.userid=#{userid} 
	</select>	
</mapper>
```



### Login Controller
로그인과 관련된 요청을 처리하는 Controller.

```java
@Log4j
@Controller
@RequestMapping("/")
public class LoginController {
	
	//로그인 성공했지만 권한이 불충분할 때 accessDenied.jsp 페이지를 보여준다.
	@RequestMapping("accessDenied")
	public void accessError(Authentication auth, Model model){
		model.addAttribute("msg", "Access Denied");
	}
	
	//로그인 페이지로 이동한다.
	//로그아웃, 로그인실패 정보를 받을 수 있다.
	@GetMapping("loginPage")
	public void loginInfo(String error, String logout, Model model) {
		if(error != null) {
			model.addAttribute("error", "로그인 정보를 확인해주세요");
		}
		if(logout != null) {
			model.addAttribute("logout", "로그아웃");
		}
	}
}

```


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



