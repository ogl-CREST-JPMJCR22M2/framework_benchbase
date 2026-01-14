## BenchBase


### MySQL Initial setup

Add a user to MySQL: ID admin, PASSWORD password
```bash
sudo mysql
> CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';
> GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost';
```


### BenchBase Installation

- Reference: https://github.com/cmu-db/benchbase (The actual installation should be this repository's modified one)

```bash
$ sudo apt install openjdk-17-jdk maven  # must install Java v17 or higher
$ cd ultraverse-benchbase
$ ./make-mysql # compilation. Must run it whenever editting the Java source code

Once the execution is done, the general log file gets stored in the `/var/log/mysql/mylog` file
