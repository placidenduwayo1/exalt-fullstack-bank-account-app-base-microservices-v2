pipeline {
    agent any
    tools {
        maven "Maven-3.9.6"
        jdk "Java-21"
    }
    stages {
        stage ("Stage:Build"){
            steps{
                checkout scmGit(branches: [[name: "*/main"]], extensions: [], userRemoteConfigs: [[url: "https://github.com/placidenduwayo1/exalt-fullstack-bank-account-app-base-microservices-v2.git"]])
                dir("./exalt-backend/business-microservices/"){
                    sh "mvn clean install"
                }
            }
            post {
                // ------------------- publishing artefacts ----------------
                success {
                    dir("./exalt-backend/business-microservices/exalt-business-microservice-bankaccount/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                    dir("./exalt-backend/business-microservices/exalt-business-microservice-customer/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                    dir("./exalt-backend/business-microservices/exalt-business-microservice-operation/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                    dir("./exalt-backend/security-microservice/exalt-business-ms-spring-security-aouth2-resource-server/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                    dir("./exalt-backend/utils-microservices/exalt-microservices-configuration-server/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                    dir("./exalt-backend/utils-microservices/exalt-microservices-registration-server/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                    dir("./exalt-backend/utils-microservices/exalt-gateway-service-proxy/"){
                        archiveArtifacts "**/target/*.jar"
                    }
                }
            }
        }
        stage("Stage: Unit Test"){
            steps {
                checkout scmGit(branches: [[name: "*/main"]], extensions: [], userRemoteConfigs: [[url: "https://github.com/placidenduwayo1/exalt-fullstack-bank-account-app-base-microservices-v2.git"]])
                echo "starting run unit test"
                dir("./exalt-backend/business-microservices/"){
                    sh "mvn test"
                }
            }
            post {
               // ------------------- publishing test reports ----------------
                always {
                   dir("./exalt-backend/business-microservices/exalt-business-microservice-bankaccount/"){
                       junit "**/target/surefire-reports/TEST-*.xml"
                   }
                   dir("./exalt-backend/business-microservices/exalt-business-microservice-customer/"){
                       junit "**/target/surefire-reports/TEST-*.xml"
                   }
                   dir("./exalt-backend/business-microservices/exalt-business-microservice-operation/"){
                       junit "**/target/surefire-reports/TEST-*.xml"
                   }
                 }
            }
        }
        stage ("Stage:Build docker images"){
            steps {
                echo "------------------- Starting build docker images of the bank-account application---------------"
                script {
                    sh "docker compose -f ./docker/docker-compose-images-template.yml build"
                    sh "docker system prune -f"
                }
            }
        }
        stage ("Stage:Publish bank-account app stack") {
            steps {
                echo "Starting publish docker images into docker registry"
                script {
                    withDockerRegistry([ credentialsId: "dockerhub-credentials", url: "" ]) {
                        sh "docker compose -f ./docker/docker-compose-images-template.yml push"
                    }
                }
            }
        }
    }
}