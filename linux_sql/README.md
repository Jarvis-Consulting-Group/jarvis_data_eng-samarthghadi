# Linux Cluster Monitoring Agent
The Linux Cluster Monitoring Agent is a tool that tracks the hardware specifications and software utilization, memory management of the local machine and saves to a PostgreSQL container. This will help the LCA team to generate reports for future resource planning purposes (e.g. add or remove servers). This tool uses cron to update data to the server each minute.
The technologies used are: 
* bash 
* docker
* git
* postgresSQL

***

# Quickstart
- Start a psql instance using psql_docker.sh
```
        ./scripts/psql_docker.sh start
```
- Create tables using ddl.sql
```
        psql -h localhost -p 5432 -U postgres -d host_agent -f sql/ddl.sql
```
- Insert hardware specs data into the DB using host_info.sh
```
        bash scripts/host_info.sh localhost 5432 host_agent postgres postgres
```
- Insert hardware usage data into the DB using host_usage.sh
```
        bash scripts/host_usage.sh localhost 5432 host_agent postgres postgres
```
- Crontab setup
```
    #edit crontab jobs
        crontab -e

    #add this to crontab
        * * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log

    #list crontab jobs
        crontab -l
```

***

# Implementation
We are implementing an MVP, so instead of executing it on Linux Cluster, this program is implemented on a single cluster node. We have implemented bash scripts to start psql docker and extract hardware information and software specifications. PostgreSQL to create the database and save results to tables. We have installed psql client to execute psql commands and view the database results. We have used Git for version control and GitHub to store our code.

***

## Architecture
![Architecture.png](assets%2FArchitecture.png)

***

## Scripts
Shell script description and usage
- `psql_docker.sh` - This script creates | starts | stops the docker container.
```
    #usage
    ./scripts/psql_docker.sh start
```
- `host_info.sh` - This script gathers Hardware specifications.
```
    #usage
    ./scripts/host_info.sh hostname port db_name username password
```
- `host_usage.sh` - This script runs every minute to gather information with the help of crontab.
```
    #usage
    ./scripts/host_usage.sh hostname port db_name username password
```
- `crontab` - A crontab is a command that the cron executes on a regular basis.
```
    #usage
    * * * * * bash pathname/host_usage.sh hostname port db_name user password > /tmp/host_usage.log
```
- `queries.sql` - A query is a request for data or information from a database table.

***

## Database Modeling
The database contains two tables namely `host_info` and `host_usage`. The Primary key of `host_info` is the Foreign key as well as Primary key of the `host_usage`.  

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![Tables.png](assets%2FTables.png)

***

# Test

- `psql_docker.sh`
    - Tested the script for invalid parameters.
    - Tested if container is already created and it's status.
- `host_info.sh` | `host_usage.sh`
    - Tested the script for invalid parameters.
    - Tested if correct data is inserted into the tables.
- `ddl.sql`
    - Tested creation of tables into the database.
  
***

# Deployment
Using the Github code create a docker container using the quickstart and install the psql client tool and run the scripts and with the help of `crontab` setup the tool to monitor every minute.

***

# Improvements
- Handle Hardware updates.
- Enhance database data validation, and improve error handling.