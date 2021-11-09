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

