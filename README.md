
# Spring Boot 기반 LOL 전적 검색 서비스

## 개발 기간
* 2018.03 ~ 2018.04

## 개발 동기
* 자주 사용하는 서비스를 직접 구현하고 싶었음.

## 개발 목적
* 기존 프로젝트에서 간단하게 구현한 기능을 추가 및 개선하여 독립적으로 구현.

## 개발 환경
* 언어 : Java
* Front : HTML, CSS, Bootstrap
* Back : Spring Boot, JavaScript & JQuery
* DB : MongoDB
* Server : Tomcat
* Build : Maven
* IDE : IntelliJ, Studio 3T, Sublime Text
* Cloud : Amazon EC2 - Free tier
## 기능
* 소환사 검색 ( 기본적인 소환사 정보 표기, 참여한 게임 정보, 선택한 챔피언 별 통계 )
* 전체 챔피언 별 통계 ( 승률 ) 
## DB Collection
* Champion : 게임 캐릭터 (업데이트 시 자동 갱신)
* Item : 게임 아이템 (업데이트 시 자동 갱신)
* Match : 단일 게임에 대한 모든 데이터
* Summoner : 등록된 게임 사용자 정보
* SummonerSpell : 게임 스킬(업데이트 시 자동 갱신)
<hr/>
## 기본 실행 방법
http://13.125.18.131:8080/lol-analytics-spring-boot/search?summonerName="검색하고자 하는 소환사 닉네임 입력 위치"
ex) http://13.125.18.131:8080/lol-analytics-spring-boot/search?summonerName=Tchau
* 개선예정
   - Context Path 설정
   - 보안 그룹 설정
<hr/>
## 전적 검색 기능 사용 방법
* REST API를 사용하기 위해 Riot 계정으로 Token을 발급받아야 함. (Expire될 경우 갱신)
* 제 계정을 사용하세요 ( ID : knoc3885 / PW : vlckd123!@ )
<hr/>

1. https://developer.riotgames.com/ 접속 (Riot-developer 지원 페이지 - REST API 제공)
<img src = "./img/developer 메인.png" width="400" height="300"></img>
<hr/>
2. 로그인 ( korea 서버 선택 필수 )
<img src = "./img/로그인.png" width="200" height="300"></img>
<hr/>
3. 하단에 Captcha 인증 후 regenerate key 눌러서 갱신한 후 복사.
<img src = "./img/갱신.png" width="400" height="300"></img>
<hr/>
4. lol-analytics-spring-boot/src/main/java/com/springboot/analytics/controller/HomeController.java 파일의 
   상단 setHeaders 메소드의 "X-Riot-Token" 부분의 2번째 param에 복사한 키를 붙여넣기.
<img src = "./img/setHeaders.png" ></img>
<hr/>
5. 실행 후 main 확인
<img src = "./img/메인.png" ></img>
