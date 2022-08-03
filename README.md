# Riot Data Pipeline
### [개발 과정 블로그 게시글](https://taaewoo.tistory.com/category/BigData%20Engineering/Riot%20Data%20Pipeline)을 참고하세요.

## Architecture
<p align="center"><img src="https://user-images.githubusercontent.com/28804154/162478926-b0b975ec-5db1-4f10-a339-d53680555a7b.png" width=80% height=80%></p>


## Git clone
~~~
$ git clone https://github.com/Taaewoo/Riot_Data_Pipeline.git
~~~

## Riot API Key 파일 생성
~~~
$ vim src/main/resources/riotApiKey.properties
~~~
"riotApiKey.properties" 파일에 아래 내용을 본인 Riot API 넣어서 작성.

https://developer.riotgames.com/ 해당 링크에서 Key 발급 가능.
~~~
riot.api.key = RGAPI-xxxxx-xxxxx-xxxx
~~~

## Json 파일 생성
Kafka로 이미 전달된 Match ID를 기록하는 json 파일
~~~
$ vim src/main/resources/json/matchRecord.json
~~~

## Bigdata Platform on docker
[Platform Github](https://github.com/Taaewoo/Bigdata_Platform_on_Docker)

### Kafka + Zookeeper
- Kafka Manager Web : [http:localhost:9000](http:localhost:9000)
~~~
$ cd platform/kafka
$ docker compose up -d
~~~

### Airflow
- Airflow Web : [http:localhost:18080](http:localhost:18080)
~~~
$ cd platform/airflow
$ echo -e "AIRFLOW_UID=$(id -u)\nAIRFLOW_GID=0" > .env
$ docker-compose up airflow-init
$ docker compose up -d
~~~

### Spark + HDFS
- Spark Master Web : [http:localhost:8082](http:localhost:8082)
- Spark Worker Web : [http:localhost:8081](http:localhost:8081)
~~~
$ cd platform/spark
$ mkdir -p data/namenode
$ mkdir -p data/datanode
$ mkdir -p spark_dir
$ docker compose up -d
~~~
