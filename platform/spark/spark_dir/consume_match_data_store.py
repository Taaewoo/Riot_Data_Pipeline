from pyspark.sql import SparkSession
from pyspark.sql.functions import from_json, col

import time
import json

#./pyspark --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.2.0

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


participants_col = log.select(from_json(col("value").cast("string"), json_schema).alias("parsed_value"))