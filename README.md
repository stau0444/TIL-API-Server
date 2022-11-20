# `TIL` (Things I Love) Front-end 

#### 목차

[1. 프로젝트 설명](#1프로젝트-설명 )
<br/>
[2. 사용 라이브러리](#2사용-라이브러리)
<br/>
[3. 프로젝트 구조](#3프로젝트-구조)
<br/>
[4. ERD](#4ERD)
<br/>



## 1.프로젝트 설명 

<br/>

<img width="300" style="border-radius:20px" alt="logoImage" src="https://user-images.githubusercontent.com/51349774/202742588-1871ff0d-c0a1-4de6-b5d4-49366c0b069c.png">

<br/>

좋아하는 것들을 리스트로 저장할 수 있는 앱입니다 . Spring-boot , JPA , Spring-Security 기반으로 만들어졌으며 AWS EC2 인스턴스에 Docker container로 배포되어 있습니다. REST API 방식으로 EC2 인스턴스에 Docker container로 배포되어 있는 Front-end 서버에서 데이터를 응답하고 있습니다.   Jenkins, GitHub Hook , Docker Hub를 활용한 배포자동화가 구축되어 있습니다. 

<br/>

> Site URL

### https://til-api.space

###### * 테스트 계정  ID : testuser@test.com / pwd : asdasd12!

<br/>

## 2. 사용 라이브러리

<br/>

---

## 2.개발 환경

|                 | Back-end Stack                       |
|-----------------|--------------------------------------|  
| Language        | `JAVA 11  `                          |
| CI/CD           | `Jenkins 2.366` , `Docker` 20.10.17  |
| Framwork        | `Spring-boot 2.4.2  `                |
| Library         | `Spring-Security ,Spring-data-JPA`   |
| build Tool      | `gradle 6.6 `                        |
| Web Container   | `Tomcat 9.0.41    `                  |
| Database        | `AWS RDS MariaDB 10.5.12`            |
| Test DB         | `H2 Database`                        |
| Build Server    | ` AWS EC2 Ubuntu Server 22.04 LTS `  |
| Deploy Server    | ` AWS EC2 Ubuntu Server 22.04.5 LTS ` |
| Version Control | `Git , GitHub `                      |
| etc             | `QueryDsl , Lombock`     |

---
<br/>

## 3.프로젝트 구조


<br/>

<img width="517" alt="TilBuildAndDeployFlow" src="https://user-images.githubusercontent.com/51349774/202758185-31986614-dc6c-4ea2-8ab4-8017a5fe875d.png">

<br/>

<img width="517" alt="TilReqRespFlow" src="https://user-images.githubusercontent.com/51349774/202911880-0c1b1768-5f78-4c8a-b9c0-5f689af619fd.png">


---

## 4.ERD

<img width="517" alt="TIL-ERD" src="https://user-images.githubusercontent.com/51349774/202912345-5abd1f27-04e9-4eb6-b8d5-92ac1cc95498.png">
