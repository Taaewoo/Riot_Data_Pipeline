from pyspark.sql import SparkSession
from pyspark.sql.functions import from_json, col

import time

ss = SparkSession.builder.getOrCreate()

ss.sparkContext.setLogLevel('ERROR')

# Read stream
log = ss.readStream.format("kafka") \
.option("kafka.bootstrap.servers", "host.docker.internal:19096,host.docker.internal:29096,host.docker.internal:39096") \
.option("subscribe", "summoner-match") \
.option("startingOffsets", "earliest") \
.load()

print("**ReadStream Kafka topic schema")
log.printSchema()


log2 = ss.read.format("kafka") \
.option("kafka.bootstrap.servers", "host.docker.internal:19096,host.docker.internal:29096,host.docker.internal:39096") \
.option("subscribe", "summoner-match") \
.option("startingOffsets", "earliest") \
.load()

print("**Read Kafka topic schema")
log2.printSchema()

log2_df = log2.selectExpr("CAST(value AS STRING)") \

value_collect = log2_df.collect()

str_topic_val = str(value_collect[1])
dict_topic_value = value_collect[1].asDict()
#dict_topic_value["value"] 
#type(dict_topic_value["value"]) : unicode
enc = dict_topic_value["value"].encode('utf-8') 
print(enc)
enc_json = json.loads(enc)
enc_json.keys()

#type(enc_json["info"]["participants"]) : list
#type(enc_json["info"]["participants"][0]) : dict 

pretty = json.dumps(enc_json["info"], indent=4)
print(pretty.decode('unicode_escape'))

#enc_json["info"]["participants"][1]["summonerName"] # summonerName 찾기
#enc_json["info"]["participants"][1]["summonerName"].encode('utf-8') == {summonerName} 
#True

