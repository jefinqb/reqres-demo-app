pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout Repo') {
            steps {
                git branch: 'commitmessages',
                    credentialsId: 'GitLab_Jefin',
                    url: 'git@code.qburst.com:jefin/api-devergence-predictor-tool.git'
            }
        }

        stage('Run Build') {
            steps {
                sh 'mvn clean compile exec:java'
            }
        }

        stage('Archive Output Files') {
            steps {
                archiveArtifacts artifacts: 'output/*', fingerprint: true
            }
        }

        stage('Send Email') {
            steps {
                emailext(
                    subject: 'Divergence Predictor Analyzer Deliverables',
                    to: 'jefin@qburst.com',
                    body: """\
The divergence predictor analyzer successfully analyzed your API implementation.
Please find the outputs attached.

Regards,
Jenkins
""",
                    attachmentsPattern: 'output/divergence_report.json, output/env.json, output/postman_collection.json, output/test-report.html'
                )
            }
        }
    }

    post {
        failure {
            emailext(
                subject: 'Divergence Predictor Analyzer Build FAILED',
                to: 'jefin@qburst.com',
                body: "The job failed. Please check Jenkins logs."
            )
        }
    }
}
