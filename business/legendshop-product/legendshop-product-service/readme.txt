如何打包并推上harbor 见142服务器：

先替换target里的jar包

执行命令：
docker build -t harbor.legendshop.cn/legendshop6.0/legendshop-product-service:LS.7.0.0

docker push harbor.legendshop.cn/legendshop6.0/legendshop-product-service:LS.7.0.0

docker images


/bin/sh -c 'sleep 120;java $JAVA_OPTS -jar legendshop-product-service.jar