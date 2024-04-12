import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.io.ByteArrayInputStream

plugins {
	java
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
}



group = "com.transactions.consumer"
version = "0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	
	compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<Jar>("jar") {
	// disable generation of *-plain.jar
	enabled = false
	archiveFileName.set(tasks.named<BootJar>("bootJar").get().archiveFileName)
}

// docker::start
/**
 * Must be loged in Docker Desktop, this is not intended
 */
tasks.register<Exec>("pushToDockerHub") {
	description = "Push image to DockerHub repository"
	group = "Docker"
	val jar = tasks.named<Jar>("jar")

	commandLine("docker")
	args("login", "-u", project.properties["docker.username"].toString(), "-p", project.properties["docker.password"].toString())
	commandLine("docker")
	args("push", project.properties["docker.username"].toString()+"/transactions.${jar.get().archiveBaseName.get()}:${version}")
}



tasks.register<Exec>("createImage"){
	description = "Build image"
	group = "Docker"

	dependsOn("copyDockerfile")

	val jar = tasks.named<Jar>("jar")
	commandLine("docker", "image", "build" )
	args("-t", project.properties["docker.username"].toString()+"/transactions.${jar.get().archiveBaseName.get()}:${version}")
	args("--build-arg", "JAR_FILE=${jar.get().archiveFile.get().asFile.name}")
	args("build/libs")
}


/**
 * Copy Dockerfile to build/libs
 */
tasks.register<Copy>("copyDockerfile") {
	from("${project.projectDir}" + File.separator + "docker" + File.separator + "Dockerfile")
	into("${project.getLayout().getBuildDirectory().get().toString()}" + File.separator + "libs" + File.separator )
}
