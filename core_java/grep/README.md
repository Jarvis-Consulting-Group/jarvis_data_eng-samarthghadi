# Introduction
This Java program searches for a specified string or pattern within text files. It operates by navigating through a user-provided "root directory," exploring files and sub-directories. The objective is to identify and extract lines matching the given pattern from these files. The results are recorded in an output file.

This program is created using the following concepts:
- Utilizes List and ArrayList for data handling.
- Implements an Interface to structure the program.
- Leverages Regex Pattern for pattern matching.
- FileReader/FileWriter for reading from and writing to files.
- BufferedReader/BufferedWriter for efficient file handling.
- Implements Exception Handling for error management.
- Utilizes Logger for effective logging of actions.
- Utilizes Maven for project management.
- Executed using Docker or as a standalone JAR file.
# Quick Start
Compile using Jar file
```
#compile and package your Java code
mvn clean compile package

#Compile using Jar file
java -cp target/grep-1.0.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [ouputFile.txt]

```

# Implemenation
## Pseudocode
`process` method pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
Dealing with large files/directories can lead to memory and performance problems.

Improve performance using these techniques: 
- Use XMX to limit the heap size for memory control.
- Improve character streaming efficiency with BufferedReader.
- BufferedReader prevents memory overload and boosts performance.
- BufferedReader is more efficient than FileReader for reading characters.
- BufferedReader and BufferedWriter use temporary memory regions, called buffers, to speed up tasks like reading and writing data.

# Test
- Utilize logging and print suitable messages for debugging purposes.
- Manual testing using a sample file from "Romeo and Juliet." We used specific patterns to find matches and checked if the results in the output file were as expected.

# Deployment
Dockerized the app and deployed onto DockerHub.
```
#Register Docker hub account
docker_user=your_docker_id

#Login onto DockerHub using your credentials
docker login 

#Create dockerfile
cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jarls
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF

#Package the grep app
mvn clean package

#build a new docker image locally
docker build -t ${docker_user}/grep .

#verify the image
docker image ls | grep "grep"

#run the docker container
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep.out

#push the image to Docker Hub
docker push ${docker_user}/grep
```

# Improvement
- Using Lambda and Stream APIs for the app.
- Instead of manual testing, JUNIT can be utilized for testing.
- Address problems related to managing memory.