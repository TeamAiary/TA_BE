# 📕 Aiary

## 기여자
<table>
<tr>
<td>

|                     BaeJunH0                       |
| :------------------------------------------------: |
| <img width="240px" src="https://avatars.githubusercontent.com/u/114082026?v=4" /> |
|     [@BaeJunH0](https://github.com/BaeJunH0)       |
|          National - 🇰🇷 Republic Of Korea           |

</td>
<td>

|                  hyun7586                   |
| :------------------------------------------------: |
| <img width="240px" src="https://avatars.githubusercontent.com/u/104716006?v=4" /> |
|     [@hyun7586](https://github.com/hyun7586) |
|          National - 🇰🇷 Republic Of Korea            |

</td>
</tr>
</table>

## 🙄 프로젝트 구상 이유
> **Aiary**는 누구나 **일기 작성을 통해 감정 분석 및 도움을 받을 수 있는** 서비스입니다.
>
> 기존의 정신건강 지원 서비스는 오프라인 위주의 구조로, 명확한 진입 장벽이 존재했습니다.
>
> 이를 해결하기 위해서 익명성과 비대면성의 강점을 가진 기술 기반 접근을 택하였습니다.
>
> 간단한 일기 작성만으로 자신의 상태를 파악하고 도움을 받을 수 있습니다.

## 저장소
> [**_BackEnd_**](https://github.com/TeamAiary/TA_BE)
>
> [**_FrontEnd_**](https://github.com/TeamAiary/TA_Android)

## 프로젝트 주요 기능
**1. 로그인 및 회원가입 기능**
- 자체 로그인 기능 구현
- Interceptor를 이용한 인증 및 인가 기능

**2. 일기 작성 기능**
- 하루에 한 편씩, 일기를 작성할 수 있음
- Upsert를 사용하여, 하루에 하나의 일기만 작성 가능하도록 구현

**3. 리포트 열람 기능**
- 1주, 1달에 한 편씩 주간, 월간 리포트를 열람할 수 있음
- 이는 스케줄링 + open ai api를 이용하여 작성됨
- WebClient + Mono를 통해 Non-Blocking 방식 통신으로 유저 1명당 1개의 요청을 병렬로 처리

**4. 미션 기능**
- 1주에 한 번씩 초기화되는 미션을 수행하는 기능
- 미션 - 유저간은 비정규화를 통해 각각 관리 (유저에게 미션 진척도를 저장, 비트 마스킹 사용)

**5. 상담소 조회 기능**
- 내 주변의 상담소를 가져오는 기능
- [공공 데이터 사용](https://www.data.go.kr/iim/api/selectAPIAcountView.do)

## 설계

**ERD**

![erd.png](image/erd.png)

**Architecture**

![img.png](image/arch.png)

**Git Flow**

![git_flow.png](image/git_flow.png)

## 기술 스택
> Framework
>
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
> DB
>
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

> Deploy & Automation
>
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

> Collaboration Tool
>
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
