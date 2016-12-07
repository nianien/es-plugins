1. unzip -d /pathto/elasticsearch-2.2.0/plugins/like  /pathto/like-analyzer-0.0.1-SNAPSHOT.zip

2. append to config/elasticsearch.yml as follows:

    index.analysis.analyzer.ik.type : "like"
