
const settingsContent = `<settings>
   <servers>
     <server>
       <id>dev-repo</id>
       <username>maven</username>
       <password>${process.env.MVN_PASS}</password>
	<configuration>
		<hostKey>maven.tehbeard.com ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDBQjeh1mcMNsR25YwVLbDiNZmq/ihxznaovcb/QGD7kB/w2Pxjd3RGYlzifYuDz9G1nCH3EFwQl8Xad8heHeeJHWPUSNIxDuCiblK0NjrYHlqNz4G/s7QFa4zSLrfJqD4CuU62hcJWQta6t+giaPejxs4yNY8v8CrKbOdSWVQcE0O+RNSUzvksvWqdGKmHt0lZ2v9W39xZf4LVzD7t4q5DOnp6PG2DXY+gfPH4jvAyfr++MaZkAfC9t2l3NMwmGH2Tb1MASYo2qPk3ysP34jmzlEQV5gt6GzEPdDjhNXF16jz4KGzIP3UxVpzXXO5RCAK84YFc0LhgOBeHHkRik70F</hostKey>
	</configuration>
     </server>
     <server>
       <id>dev-repo-snap</id>
       <username>maven</username>
       <password>${process.env.MVN_PASS}</password>
	<configuration>
		<hostKey>maven.tehbeard.com ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDBQjeh1mcMNsR25YwVLbDiNZmq/ihxznaovcb/QGD7kB/w2Pxjd3RGYlzifYuDz9G1nCH3EFwQl8Xad8heHeeJHWPUSNIxDuCiblK0NjrYHlqNz4G/s7QFa4zSLrfJqD4CuU62hcJWQta6t+giaPejxs4yNY8v8CrKbOdSWVQcE0O+RNSUzvksvWqdGKmHt0lZ2v9W39xZf4LVzD7t4q5DOnp6PG2DXY+gfPH4jvAyfr++MaZkAfC9t2l3NMwmGH2Tb1MASYo2qPk3ysP34jmzlEQV5gt6GzEPdDjhNXF16jz4KGzIP3UxVpzXXO5RCAK84YFc0LhgOBeHHkRik70F</hostKey>
	</configuration>
     </server>
   </servers>
 </settings>`

require("fs").writeFileSync("./settings.xml", settingsContent)
