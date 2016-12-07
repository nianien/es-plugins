1.  make artifact with maven command:
    mvn -Dmaven.test.skip=true clean package

2. unzip -d /pathto/elasticsearch-2.2.0/plugins/like  /pathto/like-analyzer-0.0.1-SNAPSHOT.zip

3. append to config/elasticsearch.yml as follows:

    index.analysis.analyzer.ik.type : "like"
