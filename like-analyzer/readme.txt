1. make artifact like-analyzer.zip:
  mvn -Dmaven.test.skip=true clean package
2. unzip like-analyzer.zip to es plugins path
  unzip -d /path/to/elasticsearch/plugins/like  /path/to/like-analyzer.zip
