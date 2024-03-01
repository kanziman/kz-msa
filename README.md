<!-- PROJECT LOGO -->
<br />
<div align="center">

  <a href="https://kanzi.kr" target="_blank">
    <img src="https://github.com/forthezorba/resume/assets/59009409/1256f0a1-9c9e-4ccc-9eaf-1176f0cc113a" alt="Logo" width="50" height="50">
  </a>
  <div>
      <a href="https://kanzi.kr" target="_blank">
        <b>kanzi.kr</b>
      </a>
  </div>

**Investing Platform msa version**

---

</div>

####📗 Table of Contents

-   [📖 About the Project](#about-project)
    -   [API docs](#docs)
    -   [Architecture](#architecture)
    -   [Built With](#built-with)
    -   [Live Demo](#live-demo)
-   [☼ Install](#install)
-   [❓ Contact](#contact)
-   [📝 License](#license)

---

_기존 프로젝트 spring cloud 전환_

<!-- ABOUT THE PROJECT -->

#### Overview <a name="about-project"></a>

-   국내시장의 ADR/CREDIT/VALUATION 정보를 제공합니다.(chart/api)
-   국내기업의 per/pbr/psr/por 정보를 제공합니다.(chart/api)
-   ~~간지봇 국내기업의 가장 최신 분기보고서(2023.3분기)를 바탕(RAG)으로 Chat GPT와 대화할 수 있습니다.~~
-   커뮤니티에 마크다운 형태로 글/댓글을 사용할 수 있습니다.
-   KZ자산운용(가상)에 관해 챗봇으로 안내 받을 수 있습니다.

---

#### API docs <a name="docs"></a>

API 형태로 가공/정제 처리된 정보를 이용가능합니다 [<a href="https://kanzi.kr/docs/index.html" target="_blank"> RestDocs API </a>]

---

#### Key features <a name="features"></a>

> MARKET

1일 간격으로 업데이트되며, 영업일-1 또는 영업일-2 기준으로 지표가 산출됩니다.

<details>
    <summary>지표</summary>
    <ul>
        <li>ADR : 20일간 누적 상승종목수/하락종목수</li>
        <li>CREDIT: 금융투자협회의 신용잔고 정보</li>
        <li>KOSPI/KODAQ: 한국거래소 코스피/코스닥 PER/PBR/DY 정보</li>
    </ul>
</details>
<p>

> 기업

2016.4Q ~ 현재까지 아래와 같이 계산된 정보를 CHART/API 형태로 이용가능 합니다.
1일 간격으로 업데이트되며, 영업일-1 기준으로 기준으로 지표가 산출됩니다.

<details>
    <summary>지표</summary>
    <ul>
        <li>per/eps : 주당가격(price) 을 eps(주당순이익) 으로 나눈 값. 또는 기업 시가총액/한해순이익</li>
        <li>pbr/bps: 주당가격(price)을 bps(주당순자산) 으로 나눈 값. 또는 기업 시가총액/순자산</li>
        <li>por/ops: 주당가격(price)을 ops(주당영업이익) 으로 나눈 값. 또는 기업 시가총액/영업이익</li>
        <li>psr/sps: 주당가격(price)을 sps(주당매출액) 으로 나눈 값. 또는 기업 시가총액/매출액</li>
    </ul>
</details>
<p>

> 커뮤니티

소셜로그인(구글/네이버/카카오)을 지원하며, 글/댓글/좋아요/북마크/필터검색이 가능합니다.

> 챗봇

~~질의시 벡터스토어(faiss)에서 가장 관련된 문서를 골라, AI모델에 원질문(stand alone)과 함께 컨텍스트를 질의해 답변을 구하는 방식의 챗봇입니다.
현재 가장 최신 분기 보고서인 2023.3분기 보고서를 바탕으로 답변 가능합니다.~~

| 카테고리     |              특징              |                                                                                                          GIF |
| :----------- | :----------------------------: | -----------------------------------------------------------------------------------------------------------: |
| **시장**     |     adr,credit,kospi,kodaq     |      <img src="https://github.com/forthezorba/resume/assets/59009409/2d40dd74-80d2-48d8-b8d8-f11d1a59b5d2" > |
| **기업**     |        per,pbr,por,psr         |      <img src="https://github.com/forthezorba/resume/assets/59009409/55ebed39-e324-4368-9b82-c43f58163539" > |
| **커뮤니티** | 글/댓글/좋아요/북마크/필터검색 |      <img src="https://github.com/forthezorba/resume/assets/59009409/088b6cca-c3bd-4baf-a4e1-1fbc689df95d" > |
| **~~챗봇~~** |                                |      <img src="https://github.com/forthezorba/resume/assets/59009409/b57ae49f-1fb6-4b06-bac1-0cc6ba6f6183" > |
| **챗봇**     |                                | <img src="https://github.com/forthezorba/boiler_plate/assets/59009409/283d02cf-e034-466f-8942-361efa85e52b"> |

---

#### Install <a name="install"></a>

```yml
#network
docker network create --gateway 172.16.0.1 --subnet 172.16.0.0/16 kz-network

# Docker
sudo yum install -y docker
sudo systemctl enable --now docker

# Add user to group
sudo usermod -aG docker ec2-user
newgrp docker

# check
docker ps -a

# Compose Plugin
sudo mkdir -p /usr/local/lib/docker/cli-plugins/
sudo curl -SL "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-$(uname -m)" -o /usr/local/lib/docker/cli-plugins/docker-compose
sudo chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

# Start with docker-compose.yml
docker compose up -d
```

---

#### Architecture <a name="architecture"></a>

[인프라 구성]
![image](https://github.com/forthezorba/resume/assets/59009409/fa42acaf-11a4-4029-9b9a-dc93beb9d111)
[배포 프로세스]

![img](https://github.com/forthezorba/resume/assets/59009409/5e6d9dad-ca49-4c3a-87e9-2665fcb6c130)

---

#### 🛠 Built With <a name="built-with"></a>

> BACKEND

| category             |                     content                     |
| :------------------- | :---------------------------------------------: |
| Language & Framework | SPRINGBOOT 3.2.0. / JAVA 17 / SPRING Cloud 2023 |
| Database             |                 Mysql 8 / Faiss                 |
| Model & Library      |       Gpt3.5-turbo / Langchain / flowise        |

> FRONTEND

| category             |    content    |
| :------------------- | :-----------: |
| Language & Framework | Vue3 / quasar |
| Model & Library      |      D3       |

> INFRASTRUCTURE

| category |               content               |
| :------- | :---------------------------------: |
| Cloud    |            AWS / Docker             |
| CI/CD    | Github Action / AWS S3 / CodeDeploy |

## Contact <a name="contact"></a>

kanzirunner@gmail.com

## Licencse <a name="license"></a>

This is released under the MIT license. See [LICENSE](https://choosealicense.com/licenses/mit/) for details.
