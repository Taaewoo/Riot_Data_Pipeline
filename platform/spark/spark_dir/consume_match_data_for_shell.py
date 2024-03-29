from pyspark.sql import SparkSession
from pyspark.sql.functions import from_json, col

import time
import json

spark.sparkContext.setLogLevel('ERROR')

json_schema = spark.read.json("/riot/sample_one_data").schema


# Read stream
log = spark.readStream.format("kafka") \
.option("kafka.bootstrap.servers", "host.docker.internal:19096,host.docker.internal:29096,host.docker.internal:39096") \
.option("subscribe", "summoner-match") \
.option("startingOffsets", "earliest") \
.load()

print("**ReadStream Kafka topic schema")
log.printSchema()


participants_col = log.select(from_json(col("value").cast("string"), json_schema) \
    .alias("parsed_value")) \
    .select(col("parsed_value.*")) \
    .select(col("info.participants"))

participants1_col = participants_col.select(participants_col.participants[0])

participants1_col.select(col("participants[0].*")) \
    .writeStream.format("console").start()

participants1_col.select(col("participants[0].summonerName")) \
    .writeStream.format("console").start()



log_val_df = log.select(from_json(col("value").cast("string"), json_schema).alias("parsed_value"))

log_val_parsed_df = log_val_df.select(col("parsed_value.*"))

log_val_parsed_info_df = log_val_parsed_df.select(col("info.*"))

log_val_parsed_info_df = log_val_parsed_df.select(col("info.*")).drop(col("participants"))

log_val_parsed_info_participants_df = log_val_parsed_info_df.select(col("participants"))

log_val_parsed_info_participant_df = log_val_parsed_info_df.select(col("participants")[0])




query = log_val_parsed_df \
.writeStream \
.format("console") \
.option("truncate", "false") \
.start()

query = log_val_parsed_info_df \
.writeStream \
.format("console") \
.option("truncate", "false") \
.start()

query = log_val_parsed_info_participants_df \
.writeStream \
.format("console") \
.option("truncate", "false") \
.start()


log2 = spark.read.format("kafka") \
.option("kafka.bootstrap.servers", "host.docker.internal:19096,host.docker.internal:29096,host.docker.internal:39096") \
.option("subscribe", "summoner-match") \
.option("startingOffsets", "earliest") \
.load()

print("**Read Kafka topic schema")
log2.printSchema()

#json_data = log.selectExpr("CAST(value AS STRING)") \
#.writeStream \
#.queryName("riot_json") \
#.format("memory") \
#.outputMode("append") \
#.start() 

log2_df = log2.selectExpr("CAST(value AS STRING)")

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



# Write stream - console
#query = log.selectExpr("CAST(value AS STRING)") \
#.writeStream \
#.format("console") \
#.option("truncate", "false") \
#.start()

#print(json_data.dtypes)