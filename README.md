
# Spring Boot 기반 LOL 전적 검색 서비스 (ec2 비용 문제로 점검 중)

## 개발 기간
* 2018.03 ~ 2018.04

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
* Cloud : Amazon EC2

## 기능
* 소환사 검색 ( 기본적인 소환사 정보 표기, 참여한 게임 정보, 선택한 챔피언 별 통계 )
* 전체 챔피언 별 통계 ( 승률 ) 

## DB Collection
* Champion : 게임 캐릭터 (업데이트 시 자동 갱신)
* Item : 게임 아이템 (업데이트 시 자동 갱신)
* Match : 단일 게임에 대한 모든 데이터
* Summoner : 등록된 게임 사용자 정보
* SummonerSpell : 게임 스킬(업데이트 시 자동 갱신)

## 기본 실행 방법
* http://13.125.18.131:8080/lol-analytics-spring-boot/main 에 접속하고 오른쪽 상단에 검색할 소환사 닉네임 입력하기.
* 429 Many request error 발생 시, 1분 뒤 다시 입력.

## 개선할 점
* Context Path 설정
