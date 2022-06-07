import pendulum
import requests

from airflow import DAG
from datetime import datetime, timedelta
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator
from airflow.utils.dates import days_ago

kst = pendulum.timezone("Asia/Seoul")

default_args = {
    'start_date': days_ago(1),
}

dag = DAG(
    'produce_summoner_match_info2',
    start_date=datetime(2022, 6, 6, tzinfo=kst),
    schedule_interval="0 * * * *",
)

def callProduceSummonerMatchInfoApi():
    summonerList = ["닉네임쓰는곳", "Hide on bush", "Nectar", "친구인생구하기", "준 9"]

    for summoner in summonerList:
        print(summoner)
        res = requests.post("http://host.docker.internal:8080/produceSummonerMatchInfo?summonerName=" + summoner)
        print(res.json())
        res.raise_for_status()

t1 = BashOperator(
    task_id='bash',
    bash_command='echo "Hello airflow"',
    dag=dag,
)

t2 = PythonOperator(
    task_id='python',
    python_callable=callProduceSummonerMatchInfoApi,
    dag=dag,
)

t1 >> t2