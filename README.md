# Micronaut - Opencv project
Run: ./gradlew run

Docker commands:
- ./gradlew dockerfile
- ./gradlew dockerBuild

This comands will generate all the folders we need, but if you build the docker container from this image it won't work because its using alpine Java.

Remove the alpine tag in the Dockerfile location: "./build/docker/main/Dockerfile"
Now build docker image with: docker build -t "tag" "dockerfile path"
Run the docker container and enjoy.

### Micronaut 3.4.3 Documentation

- [User Guide](https://docs.micronaut.io/3.4.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.4.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.4.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
### Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)


