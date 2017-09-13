FROM aa8y/sbt:1.0

ARG APP_DIR=/app

USER root
RUN mkdir $APP_DIR
RUN chown -R docker:docker $APP_DIR
WORKDIR $APP_DIR

# Cache the SBT JARs in a layer.
USER docker
RUN mkdir project
COPY project/build.properties project/
USER root
RUN chown -R docker:docker project
USER docker
RUN sbt +update

# Cache the project-specific JARs.
COPY project/*.sbt ./project
COPY build.sbt .
USER root
RUN chown -R docker:docker .
USER docker
RUN sbt +update

# Copy the rest of the files. dockerignore should skip the files we don't want.
COPY . ./
USER root
RUN chown -R docker:docker .
USER docker
RUN sbt +compile
RUN sbt +test:compile
