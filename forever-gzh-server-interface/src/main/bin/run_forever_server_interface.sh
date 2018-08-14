JAVA_OPTS="-Xms512m -Xmx1024m"

echo "forever-server-interface on...."

if find -name run_forever_server_interface.pid | grep "run_forever_server_interface.pid";
then
echo "forever-server-interface is running..."
  exit
fi 

CLASSPATH="$CLASSPATH":"./forever-server-interface-1.0-SNAPSHOT.jar"
echo $CLASSPATH
LIBPATH="./lib"
if [ -d "$LIBPATH" ]; then
  for i in "$LIBPATH"/*.jar; do
    CLASSPATH="$CLASSPATH":"$i"
  done
fi

echo "Using CLASSPATH:   $CLASSPATH"

nohup java $JAVA_OPTS \
    -classpath $CLASSPATH \
    com.gaoan.forever.APIServerApplication >  output 2>&1 &

if [ ! -z "run_forever_server_interface.pid" ]; then
  echo $!> run_forever_server_interface.pid
fi

