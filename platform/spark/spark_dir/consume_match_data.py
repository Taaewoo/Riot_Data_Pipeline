from pyspark.sql import SparkSession

sc = SparkSession.builder.getOrCreate()

sc.sparkContext.setLogLevel('ERROR')

# Read stream
log = sc.readStream.format("kafka") \
.option("kafka.bootstrap.servers", "host.docker.internal:19096,host.docker.internal:29096,host.docker.internal:39096") \
.option("subscribe", "summoner-match") \
.option("startingOffsets", "earliest") \
.load()

log.printSchema()

# Write stream - console
query = log.selectExpr("CAST(value AS STRING)") \
.writeStream \
.format("console") \
.option("truncate", "false") \
.start()

query.awaitTermination()
