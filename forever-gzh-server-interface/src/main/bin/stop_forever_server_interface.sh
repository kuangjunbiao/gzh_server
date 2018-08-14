echo "Killing: `cat run_forever_server_interface.pid`"
kill -9 `cat run_forever_server_interface.pid`
rm -rf run_forever_server_interface.pid
