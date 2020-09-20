docker build -t xppmall:latest .
docker login --username=100011588504 --password=docker..123 ccr.ccs.tencentyun.com
docker tag xppmall:latest ccr.ccs.tencentyun.com/tsf_100011588504/xppmall:latest
docker push ccr.ccs.tencentyun.com/tsf_100011588504/xppmall:latest