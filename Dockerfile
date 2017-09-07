FROM aa8y/sbt:1.0

RUN mkdir -p /app/project
WORKDIR /app

# Cache the SBT JARs in a layer.
COPY project/build.properties project/
RUN sbt update

ARG SCALA_VERSION=2.11.8

# Cache the project-specific JARs.
COPY project/*.sbt ./project
COPY build.sbt .
RUN sbt ++$SCALA_VERSION update

# Copy the rest of the files. dockerignore should skip the files we don't want.
COPY . ./
RUN sbt ++$SCALA_VERSION compile
