for pid in ` ps -ef | grep com.sand.weixin.Main |grep classpath| grep -v "grep" | awk '{ print $2}'`
do
	kill -9 $pid
done