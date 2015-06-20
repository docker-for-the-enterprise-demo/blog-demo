# Specifies the starting image from which this docker image will be
# built.  We want this to be as small as possible while still
# providing the required dependencies.
FROM alexeiled/java7-maven-git

# The sources are added directly into the image from the git working
# tree
ADD app /root/app

# Setting the workdir allows us to run mvn package from the proper
# directory
WORKDIR /root/app

RUN mvn package

RUN mkdir -p /var/opt/downsample/download

RUN mkdir -p /var/opt/downsample/static

# CMD is not run as part of the image, but rather is the default
# command that will be executed when a container is executed.
CMD java -jar /root/app/target/simple-app.jar

